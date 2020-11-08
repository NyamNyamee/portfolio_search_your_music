package kr.inwoo.music;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import kr.inwoo.music.service.CartService;
import kr.inwoo.music.service.MusicService;
import kr.inwoo.music.vo.CartVO;
import kr.inwoo.music.vo.Music;
import kr.inwoo.music.vo.MusicVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MusicController 
{
	@Autowired
	MusicService musicService;
	
	@Autowired
	CartService cartService;
	
	// 비회원 노래검색 화면
	@RequestMapping(value = "/searchNormal")
	public String searchNormal(HttpServletRequest request, Model model)
	{
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		return "searchNormal";
	}
	
	// 비회원 노래검색 검색결과
	@RequestMapping(value = "/searchNormalOk", method = RequestMethod.GET)
	public String searchNormalOkGet(HttpServletRequest request, Model model)
	{
		return "redirect:/searchNormal";
	}
	@RequestMapping(value = "/searchNormalOk", method = RequestMethod.POST)
	public String searchNormalOkPost(HttpServletRequest request, Model model)
	{
		String search = request.getParameter("search");
		String category = request.getParameter("category");
		String firstSearch = request.getParameter("firstSearch");
		log.debug("-----------------------------------------" + search);
		log.debug("-----------------------------------------" + category);
		log.debug("-----------------------------------------" + firstSearch);
		model.addAttribute("search", search);
		model.addAttribute("category", category);
		model.addAttribute("firstSearch", firstSearch);
		
		if(category.equals("all"))
		{
			List<MusicVO> list1 = new ArrayList<MusicVO>();
			list1 = musicService.selectByAll(search);
			model.addAttribute("list1", list1);
		}
		else if(category.equals("name")) 
		{
			List<MusicVO> list2 = new ArrayList<MusicVO>();
			list2 = musicService.selectByName(search);
			model.addAttribute("list2", list2);
		}
		else if(category.equals("artist")) 
		{
			List<MusicVO> list3 = new ArrayList<MusicVO>();
			list3 = musicService.selectByArtist(search);
			model.addAttribute("list3", list3);
		}

		return "searchNormal";
	}
	
	
	// 회원 노래검색 화면
	@RequestMapping(value = "/searchMember")
	public String searchMember(HttpServletRequest request, Model model)
	{
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		return "searchMember";
	}
	
	// 회원 노래검색 검색결과
	@RequestMapping(value = "/searchMemberOk", method = RequestMethod.GET)
	public String searchMemberOkGet(HttpServletRequest request, Model model) 
	{
		return "redirect:/searchMember";
	}
	
	@RequestMapping(value = "/searchMemberOk", method = RequestMethod.POST)
	public String searchMemberOkPost(HttpServletRequest request, Model model)
	{
		String search = request.getParameter("search");
		String category = request.getParameter("category");
		String firstSearch = request.getParameter("firstSearch");
		log.debug("-----------------------------------------" + search);
		log.debug("-----------------------------------------" + category);
		log.debug("-----------------------------------------" + firstSearch);
		model.addAttribute("search", search);
		model.addAttribute("category", category);
		model.addAttribute("firstSearch", firstSearch);
		
		if(category.equals("all"))
		{
			List<MusicVO> list1 = new ArrayList<MusicVO>();
			list1 = musicService.selectByAll(search);
			model.addAttribute("list1", list1);
		}
		else if(category.equals("name")) 
		{
			List<MusicVO> list2 = new ArrayList<MusicVO>();
			list2 = musicService.selectByName(search);
			model.addAttribute("list2", list2);
		}
		else if(category.equals("artist")) 
		{
			List<MusicVO> list3 = new ArrayList<MusicVO>();
			list3 = musicService.selectByArtist(search);
			model.addAttribute("list3", list3);
		}

		return "searchMember";
	}
	
	
	// 관리자 노래검색 검색결과
	@RequestMapping(value = "/searchAdminOk", method = RequestMethod.GET)
	public String searchAdminOkGet(HttpServletRequest request, Model model) 
	{
		return "redirect:/musicManage";
	}

	@RequestMapping(value = "/searchAdminOk", method = RequestMethod.POST)
	public String searchAdminOkPost(HttpServletRequest request, Model model) 
	{
		String search = request.getParameter("search");
		String category = request.getParameter("category");
		String firstSearch = request.getParameter("firstSearch");
		int count = musicService.selectCount();
		log.debug("-----------------------------------------" + search);
		log.debug("-----------------------------------------" + category);
		log.debug("-----------------------------------------" + firstSearch);
		model.addAttribute("search", search);
		model.addAttribute("category", category);
		model.addAttribute("firstSearch", firstSearch);
		model.addAttribute("count", count);
		
		if(category.equals("all"))
		{
			List<MusicVO> list1 = new ArrayList<MusicVO>();
			list1 = musicService.selectByAll(search);
			model.addAttribute("list1", list1);
		}
		else if(category.equals("name")) 
		{
			List<MusicVO> list2 = new ArrayList<MusicVO>();
			list2 = musicService.selectByName(search);
			model.addAttribute("list2", list2);
		}
		else if(category.equals("artist")) 
		{
			List<MusicVO> list3 = new ArrayList<MusicVO>();
			list3 = musicService.selectByArtist(search);
			model.addAttribute("list3", list3);
		}

		return "musicManage";
	}

	// 음악목록 추가 form태그
	@RequestMapping(value = "/insertCartOk", method = RequestMethod.POST)
	public String insertCartOk(@ModelAttribute CartVO vo, Model model, HttpServletRequest request)
	{
		String userid = getPrincipal();
		vo.setUserid(userid);
		int count = cartService.selectCartCount(userid, vo.getMusic_idx());
		if(count == 0) // 중복된 곡이 없을땐 추가하고 있을때는 아무짓도 안한다
			cartService.insertCart(vo);
		return "redirect:/searchMemberOk";
	}
	
	// 음악목록에 추가 ajax
	@RequestMapping(value = "/insertCartOkAjax", method = RequestMethod.POST)
	public String insertCartOkAjax(@ModelAttribute CartVO vo, Model model, HttpServletRequest request)
	{
		String userid = getPrincipal();
		vo.setUserid(userid);
		int count = cartService.selectCartCount(userid, vo.getMusic_idx());
		if(count == 0) // 중복된 곡이 없을땐 추가하고 있을때는 아무짓도 안한다
			cartService.insertCart(vo);
		
		return "searchMember";
	}
	
	// 다중 체크된 음악들 음악목록에 추가 ajax
	@RequestMapping(value = "/insertCartCheckOkAjax", method = RequestMethod.POST)
	public String insertCartCheckOkAjax(@ModelAttribute CartVO vo, Model model, HttpServletRequest request)
	{
		String userid = getPrincipal();
		String[] music_idx_array = request.getParameterValues("music_idx_array");
	
		vo.setUserid(userid);
		for(int i = 0; i < music_idx_array.length; i++)
		{
			int count = cartService.selectCartCount(userid, Integer.parseInt(music_idx_array[i]));
			if(count == 0) // 중복된 곡이 없을땐 추가하고 있을때는 아무짓도 안한다
			{
				vo.setMusic_idx(Integer.parseInt(music_idx_array[i]));
				cartService.insertCart(vo);
			}
		}
		
		return "searchMember";
	}
	
	// 회원 내 음악목록
	@RequestMapping(value = "/cartList")
	public String cartList(HttpServletRequest request, Model model) 
	{
		String userid = getPrincipal();
		List<CartVO> cartList = cartService.selectCartList(userid);
		model.addAttribute("cartList", cartList);
		
		return "cartList";
	}
	
	// 내 음악목록에서 1개 삭제하기
	@RequestMapping(value = "removeCartOk", method = RequestMethod.POST)
	public String removeCartOk(HttpServletRequest request, Model model)
	{
		String cart_idx = request.getParameter("cart_idx");
		cartService.deleteCart(Integer.parseInt(cart_idx));
		return "redirect:/cartList";
	}
	
	// 내 음악목록에서 다중 체크된 음악들 삭제하기
	@RequestMapping(value = "removeCartCheckOkAjax", method = RequestMethod.POST)
	public String removeCartCheckOkAjax(HttpServletRequest request, Model model)
	{
		String[] cart_idx_array = request.getParameterValues("cart_idx_array");
		for(int i = 0; i < cart_idx_array.length; i++)
		{
			cartService.deleteCart(Integer.parseInt(cart_idx_array[i]));
		}
//		String cart_idx = request.getParameter("cart_idx");
//		cartService.deleteCart(Integer.parseInt(cart_idx));
		return "redirect:/cartList";
	}
	
	// 내 음악목록에서 모두 삭제하기
	@RequestMapping(value = "removeAllCartOk", method = RequestMethod.POST)
	public String removeAllCartOk(HttpServletRequest request, Model model)
	{
		String userid = getPrincipal();
		cartService.deleteAllCart(userid);
		return "redirect:/cartList";
	}
		
	// 음악관리 페이지
	@RequestMapping(value = "/musicManage")
	public String musicManage(HttpServletRequest request, Model model)
	{
		int count = musicService.selectCount();
		model.addAttribute("count", count);
		return "musicManage";
	}
	
	// 관리자 페이지에서 1개 삭제하기
	@RequestMapping(value = "/deleteOk", method = RequestMethod.POST)
	public String deleteOk(HttpServletRequest request, Model model)
	{
		String idx = request.getParameter("music_idx");
		log.debug("----------------------------" + idx);
		int music_idx = Integer.parseInt(request.getParameter("music_idx"));
		musicService.deleteCart(music_idx);
		musicService.delete(music_idx);
		return "redirect:searchAdminOk";
	}
	
	// 관리자 페이지에서 다중 체크된 음악들 삭제하기
	@RequestMapping(value = "deleteMusicCheckOkAjax", method = RequestMethod.POST)
	public String deleteMusicCheckOkAjax(HttpServletRequest request, Model model)
	{
		String[] music_idx_array = request.getParameterValues("music_idx_array");
		for(int i = 0; i < music_idx_array.length; i++)
		{
			musicService.deleteCart(Integer.parseInt(music_idx_array[i]));
			musicService.delete(Integer.parseInt(music_idx_array[i]));			
		}
		// String music_idx = request.getParameter("music_idx");
		return "redirect:/musicManage";
	}

	// 관리자 페이지에서 최신곡 추가
	@RequestMapping(value = "insertSongsOk", method = RequestMethod.POST)
	public String insertAllOk(HttpServletRequest request, Model model)
	{
		URL url;
		
		try {			
			String addAmount = request.getParameter("selectAmount"); // 추가할 곡수를 요청받아서
			request.getSession().setAttribute("addAmount", addAmount); // 다시 클라에서 쓸 수 있도록 세션에 저장
			
			Gson gson = new Gson();
			url = new URL("https://www.music-flo.com/api/meta/v1/track/KPOP/new?page=1&size=" + addAmount); // 웹에서 json으로 받아올 곡의 개수를 지정
			InputStreamReader isr = new InputStreamReader(url.openStream()); // url을 읽어들일 객체 생성
			Music vo = gson.fromJson(isr, Music.class); // url을 읽은 객체를 Music클래스에 저장
			
			for(int i = 0; i < Integer.parseInt(addAmount); i++) // 요청받은 곡수만큼 반복
			{
				String image = vo.getData().getList().get(i).getAlbum().getImgList().get(0).getUrl(); // 이미지 url소스
				String artist = vo.getData().getList().get(i).getArtistList().get(0).getName(); // 가수명
				String name = vo.getData().getList().get(i).getName(); // 곡명
				
				int musicCount = musicService.selectMusicCount(artist, name); // 웹에서 받은 가수와 제목정보를 DB에 저장된 정보와 비교해서 이미 db에 존재한다면 musicCount가 1 이상일것임
				
				if (musicCount == 0) // 그러므로 0일 때만 DB에 추가
				{
					MusicVO vo2 = new MusicVO(); // 받아온 정보를 VO에 저장한 후 VO형태로 DB에 저장
					vo2.setUrl(image);
					vo2.setArtist(artist);
					vo2.setName(name);
					
					musicService.insert(vo2);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/musicManage";
	}
	
	// 시큐리티 사용자 정보 얻기
	private String getPrincipal() 
	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
