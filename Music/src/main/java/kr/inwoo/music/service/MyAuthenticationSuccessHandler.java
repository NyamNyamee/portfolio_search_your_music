package kr.inwoo.music.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kr.inwoo.music.dao.MemberDAO;
import kr.inwoo.music.vo.MemberVO;
import lombok.Data;

// 로그인 성공 핸들러
/*
1. 로그인 성공시, 어떤 URL로 Redirect 할 지 결정
2. 로그인 실패 에러 세션 지우기
3. 로그인 성공시, 실패 카운터 초기화
 */
@Data
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	@Autowired
	private MemberDAO memberDAO;
	private String successUrl;

	private static int time = 60 * 60 * 12; // 60초 * 60 * 12 = 12시간 
	
	@Override // 로그인 성공시 처리핼 내용
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException 
	{
		// 세션 타임아웃 시간 지정
		request.getSession().setMaxInactiveInterval(time);
		// 회원 정보를 읽어 세션에 저장
		String userid = request.getParameter("userid"); // 아이디 읽기
		List<MemberVO> list = memberDAO.selectByUserid(userid);
		if(list!=null && list.size() > 0)
		{
			MemberVO vo = list.get(0);
			// 회원정보를 세션에 저장하기
			request.getSession().setAttribute("memberVO", vo);
		}
		// 어딘가로 이동
        // request.getRequestDispatcher(successUrl).forward(request, response);
		response.sendRedirect(successUrl);

	}

}
