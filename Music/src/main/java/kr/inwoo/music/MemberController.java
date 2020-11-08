package kr.inwoo.music;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.inwoo.music.service.CartService;
import kr.inwoo.music.service.EMailService;
import kr.inwoo.music.service.MemberService;
import kr.inwoo.music.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController 
{
	@Autowired
	MemberService memberService;
	
	@Autowired
	EMailService emailService;
	
	// 회원 탈퇴, 삭제 시 해당 아이디의 music_cart테이블의 내용도 삭제하기 위해
	@Autowired
	CartService cartService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 메인화면
	@RequestMapping(value = {"/index", "/"})
	public String index(HttpServletRequest request, Model model)
	{
		String userid = getPrincipal();
		model.addAttribute("user", userid);
		
		List<String> roles = memberService.selectRoles(userid);
		model.addAttribute("roles", roles);
		
		return "index";
	}
	
	// 로그인화면
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model)
	{
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		
		return "login";
	}
	
	// 로그인실패화면
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model)
	{
		String userid = getPrincipal();
		model.addAttribute("user", userid);

		return "accessDenied";
	}
	
	// 회원가입화면
	@RequestMapping(value = "/register")
	public String register(Model model)
	{
		return "register";
	}
	
	// 아이디중복확인
	@RequestMapping(value = "/idCheck", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String idCheck(@RequestParam("userid")String userid, Model model)
	{
		return memberService.idCheck(userid) + ""; // idCheck메서드의 리턴타입이 int라서 ""를 더해 String타입으로 변환
	}
	
	// 회원가입완료화면 GET
	@RequestMapping(value = "/registerOk", method = RequestMethod.GET)
	public String registerOkGet(Model model) 
	{
		return "redirect:/";
	}
	
	// 회원가입완료화면 POST
	@RequestMapping(value = "/registerOk", method = RequestMethod.POST)
	public String registerOkPost(@ModelAttribute MemberVO memberVO, Model model) 
	{
		memberService.insert(memberVO); // 우선 db에 저장
		String username = memberVO.getName();
		// 인증메일 발송
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Hi " + username + "!<br>");
			sb.append("Click this link to use your ID<br>");
			sb.append("<a href='http://localhost:8080/music/useOk");
			sb.append("?userid=" + URLEncoder.encode(memberVO.getUserid(),"UTF-8") + "'>Enroll complete</a>");
			emailService.sendMail(memberVO.getUserid(), "SYM - Welcome " + username + "!", sb.toString()); // (받는이메일, 제목, 내용)
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "registerOk";
	}
	
	// 이메일인증완료화면
	@RequestMapping(value = "/useOk")
	public String useOk(@RequestParam("userid")String userid, Model model) 
	{
		List<MemberVO> list = memberService.selectByUserid(userid);
		if(list!=null && list.size()>0) 
		{
			MemberVO vo = list.get(0);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("idx",vo.getIdx());
			map.put("use", 1 );
			memberService.updateUse(map); // 인증완료
			memberService.userRole(userid); // user_role 부여
			memberService.memberRole(userid); // member_role 부여
			model.addAttribute("memberVO", vo);
		}
		
		return "useOk";
	}
	
	// 아이디찾기화면
	@RequestMapping(value = "/findUserid")
	public String findUserid(HttpServletRequest request, Model model)
	{
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		
		return "findUserid";
	}
	
	// 아이디찾기완료화면 GET
	@RequestMapping(value = "/findUseridOk", method = RequestMethod.GET)
	public String findUseridOkGet(HttpServletRequest request,  Model model)
	{
		return "redirect:/";
	}

	// 아이디찾기완료화면 POST
	@RequestMapping(value = "/findUseridOk", method = RequestMethod.POST)
	public String findUseridOkPost(HttpServletRequest request,  Model model)
	{
		// 데이터 받기
		String name = request.getParameter("name");
		String hp = request.getParameter("hp");
		
		// 서비스 클래스를 호출하여 
		String userid = memberService.selectUserid(name, hp);
		model.addAttribute("userid", userid);
		model.addAttribute("name", name);
		
		return "findUseridOk";
	}
	
	// 비밀번호찾기화면
	@RequestMapping(value = "/findPassword")
	public String findPassword(HttpServletRequest request, Model model)
	{
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		
		return "findPassword";
	}
	
	// 비밀번호찾기완료화면 GET
	@RequestMapping(value = "/findPasswordOk", method = RequestMethod.GET)
	public String findPasswordOkGet()
	{
		log.debug("MemberController.findPasswordOk 호출 : " );
		
		return "redirect:/";
	}
	
	// 비밀번호찾기완료화면 POST
	@RequestMapping(value = "/findPasswordOk", method = RequestMethod.POST)
	public String findPasswordOkPost(HttpServletRequest request,  Model model) 
	{
		log.debug("MemberController.findPasswordOk 호출 : " );
		// 데이터 받기
		String userid = request.getParameter("userid");
		String hp = request.getParameter("hp");
		
		// 서비스 클래스를 호출하여 비밀번호를 만들어 업데이트하고
		String newPassword = memberService.findPassword(userid, hp); 

		if(newPassword!=null && newPassword.trim().length()>0 )
		{
			// 사용자에게 이메일을 보내준다.
			StringBuffer sb = new StringBuffer();
			sb.append(userid + "'s new password is created.<br>");
			sb.append("Temporary PW is \"" + newPassword + "\".");
			sb.append("<br>");
			sb.append("Please change your password immediately after login!");
			emailService.sendMail(userid, "SYM - Your new PW is created", sb.toString());
			model.addAttribute("userid", userid);
		}
		
		return "findPasswordOk";
	}
	
	// 회원정보수정화면
	@RequestMapping(value = "/memberUpdate")
	public String memberUpdate()
	{
		return "memberUpdate";
	}

	// 회원정보수정완료화면 GET
	@RequestMapping(value = "/memberUpdateOk", method = RequestMethod.GET)
	public String memberUpdateGet() 
	{
		log.debug("MemberController.memberUpdate 호출 : ");
		
		return "redirect:/";
	}

	// 회원정보수정완료화면 POST
	@RequestMapping(value = "/memberUpdateOk", method = RequestMethod.POST)
	public String memberUpdatePost(@ModelAttribute MemberVO memberVO, HttpServletRequest request, Model model)
	{
		log.debug("MemberController.memberUpdate 호출 : " + memberVO);
		if (memberVO != null) 
		{	// 업데이트하고
			memberVO = memberService.update(memberVO);
			// 세션에 다시 저장
			request.getSession().setAttribute("memberVO", memberVO);
			model.addAttribute("memberVO", memberVO);
		}
		
		return "memberUpdateOk";
	}

	// 회원 탈퇴하면서 해당 회원의 music_cart에 들어있던 목록도 전부 삭제
	@ResponseBody // 리턴값에 해당하는 페이지를 리턴하지 않고, 있는 그대로 문자열 "false"를 리턴시키기 위해 적용 
	@RequestMapping(value = "/deleteMemberSelfOk", method = RequestMethod.POST)
	public String deleteMemberSeflOk(HttpServletRequest request, HttpSession session, Model model)
	{
		String userid = request.getParameter("userid");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String password = request.getParameter("password");
		log.debug("----------------------------" + userid);
		log.debug("----------------------------" + idx);
		log.debug("----------------------------" + password);
		
		// 1. DB에서 해당 번호의 정보를 읽어온다
		MemberVO dbVO = memberService.selectByIdx(idx);
		log.debug("memberService.update DB암호 : " + dbVO.getPassword());
		log.debug("memberService.update 입력암호 : " + password);
		log.debug("memberService.update 입력암호 인코딩 : " + bCryptPasswordEncoder.encode(password));
		log.debug("memberService.update DB,입력암호 일치여부 : " + bCryptPasswordEncoder.matches(password, dbVO.getPassword()));
		// 2. 정보가 있다면 암호가 같은지 판단하여 암호가 같을때만 삭제한다
		// bcryptPasswordEncoder는 디코드 함수를 제공하지 않으므로
		// 반드시 matches()메서드를 이용하여 비교하여야 한다.
		if (dbVO != null && bCryptPasswordEncoder.matches(password, dbVO.getPassword()))
		{
			log.debug("-----------성공-------------");
			cartService.deleteAllCart(userid); // music_cart테이블에서 삭제
			memberService.delete(idx); // member 테이블에서 삭제
			memberService.deleteRoles(userid); // member_roles 테이블에서 삭제
			session.removeAttribute("memberVO"); // 세션에서 강제 로그아웃 시킴
			return "true";
		}
		log.debug("-----------실패-------------");
		
		return "false";
	}
	
	// 비밀번호변경화면
	@RequestMapping(value = "/changePassword")
	public String changePassword()
	{
		return "changePassword";
	}
	
	// 비밀번호변경완료화면 GET
	@RequestMapping(value = "/changePasswordOk", method = RequestMethod.GET)
	public String changePasswordGet() 
	{
		log.debug("MemberController.changePasswordOk 호출 : " );
		
		return "redirect:/";
	}
	
	// 비밀번호변경완료화면 POST
	@RequestMapping(value = "/changePasswordOk", method = RequestMethod.POST)
	public String changePasswordPost(HttpServletRequest request, HttpSession session,  Model model)
	{
		log.debug("MemberController.changePasswordOk 호출 : " + session );
		// 데이터 받기
		String cpassword = request.getParameter("cpassword");
		String npassword = request.getParameter("password");
		
		// 서비스를 호출하여 현재 비번이 같으면 새로운 비번으로 업데이트 한다.
		int idx = ((MemberVO)session.getAttribute("memberVO")).getIdx();
		String userid = ((MemberVO)session.getAttribute("memberVO")).getUserid();
		log.debug("MemberController.changePasswordOk 호출 : " + idx);
		log.debug("MemberController.changePasswordOk 호출 : " + userid);
		boolean isChange = memberService.updatePassword(idx, userid, cpassword, npassword);
		
		if(isChange)
		{
			// 강제 로그아웃을 시키고 로그인페이지로 보내버림
			session.removeAttribute("memberVO");
			return "redirect:/login";
		}
		
		return "changePasswordOk"; // 현재 비번이 다르다면 login페이지로 가지 않고 changePasswordOk페이지로 보내버림
	}
	
	// 관리자 페이지
	@RequestMapping(value = "/adminPage")
	public String adminPage(HttpServletRequest request, Model model)
	{
		return "adminPage";
	}
	
	// 회원관리 페이지
	@RequestMapping(value = "/memberManage")
	public String memberManage(HttpServletRequest request, Model model)
	{
		int count = memberService.selectCount();
		model.addAttribute("count", count);
		
		List<MemberVO> allMemberList = memberService.selectAllMember();
		model.addAttribute("allMemberList", allMemberList);
		
		List<String> adminIdxList = memberService.selectAdminIdx();
		model.addAttribute("adminIdxList", adminIdxList);
		
		return "memberManage";
	}
	
	// 관리자 회원검색 검색결과
	@RequestMapping(value = "/searchHumanOk", method = RequestMethod.GET)
	public String searchHumanOkGet(HttpServletRequest request, Model model)
	{
		return "redirect:/musicManage";
	}

	@RequestMapping(value = "/searchHumanOk", method = RequestMethod.POST)
	public String searchHumanOkPost(HttpServletRequest request, Model model)
	{
		String search = request.getParameter("search");
		String category = request.getParameter("category");
		String firstSearch = request.getParameter("firstSearch");
		int count = memberService.selectCount();
		List<String> adminIdxList = memberService.selectAdminIdx();
		
		log.debug("-----------------------------------------" + search);
		log.debug("-----------------------------------------" + category);
		log.debug("-----------------------------------------" + firstSearch);
		model.addAttribute("search", search);
		model.addAttribute("category", category);
		model.addAttribute("firstSearch", firstSearch);
		model.addAttribute("count", count);
		model.addAttribute("adminIdxList", adminIdxList);

		if(category.equals("userid")) 
		{
			List<MemberVO> list1 = new ArrayList<MemberVO>();
			list1 = memberService.searchByUserid(search);
			model.addAttribute("list1", list1);
		} 
		else if(category.equals("name")) 
		{
			List<MemberVO> list2 = new ArrayList<MemberVO>();
			list2 = memberService.searchByName(search);
			model.addAttribute("list2", list2);
		} 
		else if(category.equals("hp"))
		{
			List<MemberVO> list3 = new ArrayList<MemberVO>();
			list3 = memberService.searchByHP(search);
			model.addAttribute("list3", list3);
		}

		return "memberManage";
	}
	
	// 회원 삭제하면서 해당 회원의 music_cart에 들어있던 목록, member_roles에 들어있던 roles도 전부 삭제
	@RequestMapping(value = "/deleteMemberOk", method = RequestMethod.POST)
	public String deleteMemberOk(HttpServletRequest request, Model model)
	{
		String userid = request.getParameter("userid");
		int idx = Integer.parseInt(request.getParameter("idx"));
		log.debug("----------------------------" + userid);
		log.debug("----------------------------" + idx);
		cartService.deleteAllCart(userid);
		memberService.delete(idx);
		memberService.deleteRoles(userid);
		return "redirect:/login";
	}
	
	// 관리자 페이지에서 다중 체크된 회원들 삭제하기
	@RequestMapping(value = "deleteMemberCheckOkAjax", method = RequestMethod.POST)
	public String deleteMemberCheckOkAjax(HttpServletRequest request, Model model)
	{
		String[] member_userid_array = request.getParameterValues("member_userid_array");
		String[] member_idx_array = request.getParameterValues("member_idx_array");
		log.debug("----------------------------" + member_userid_array);
		log.debug("----------------------------" + member_idx_array);
		for(int i = 0; i < member_userid_array.length; i++)
		{
			cartService.deleteAllCart(member_userid_array[i]);
			memberService.delete(Integer.parseInt(member_idx_array[i]));			
		}
		// String music_idx = request.getParameter("music_idx");
		return "redirect:/memberManage";
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) 
		{
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		return "redirect:/";
	}
	
	// 시큐리티에서 유저 아이디 얻기
	private String getPrincipal()
	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) 
		{
			userName = ((UserDetails) principal).getUsername();
		} 
		else
		{
			userName = principal.toString();
		}
		
		return userName;
	}
}
