package kr.inwoo.music.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inwoo.music.dao.MemberDAO;
import kr.inwoo.music.vo.MemberVO;
import kr.inwoo.music.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Service("memberService")
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 회원가입 후 인증 시작
	// 아이디 중복확인
	@Override
	public int idCheck(String userid) 
	{
		return memberDAO.selectUseridCount(userid);
	}
	
	// 저장
	@Override
	public void insert(MemberVO vo) 
	{
		// 비밀번호를 암호화하여 저장
		String password = vo.getPassword();
		password = bCryptPasswordEncoder.encode(password);
		vo.setPassword(password);
		memberDAO.insert(vo);
	}
	
//	// 이메일 인증
//	public void useOk(String userid)
//	{
//		// 데이터 베이스에 쿼리를 use값을 1로 변경해 준다.
//		// userid로 vo를 얻고
//		List<MemberVO> list = memberDAO.selectByUserid(userid);
//		System.out.println("useOk : " + list);
//		// 해당 아이디가 존재하면
//		if (list != null) 
//		{
//			HashMap<String, Integer> map = new HashMap<String, Integer>();
//			map.put("idx", list.get(0).getIdx());
//			map.put("use", 1);
//			// user값을 인증완료인 "1"로 변경한다.
//			memberDAO.updateUse(map);
//		}
//	}
	
	// 인증수정
	@Override
	public void updateUse(HashMap<String, Integer> map) 
	{
		memberDAO.updateUse(map);
	}
	
	// user_role부여
	@Override
	public void userRole(String userid) 
	{
		memberDAO.userRole(userid);
	}

	// member_role부여
	@Override
	public void memberRole(String userid) 
	{
		memberDAO.memberRole(userid);
	}
	// 회원가입 후 인증 끝
	
	
	// 로그인 후 유저가 사용할 메서드 시작
	// 아이디찾기
	@Override
	public String selectUserid(String name, String hp) 
	{
		String userid = null;
		// DAO에 넘기기 위해 VO를 만들고
		MemberVO vo = new MemberVO();
		vo.setName(name);
		vo.setHp(hp);
		// 사용자 아이디를 리스트로 받고
		List<String> list = memberDAO.selectUserid(vo);
		if (list != null && list.size() > 0) // 사용자 아이디가 있으면
		{
			userid = list.get(0); // 대입
		}
		return userid;
	}

	// 비밀번호찾기
	@Override
	public String findPassword(String userid, String hp) 
	{
		log.debug("memberService.findPassword 호출");
		String newPassword = "";
		List<MemberVO> list = memberDAO.selectByUserid(userid);
		if (list != null && list.size() > 0) {
			MemberVO vo = list.get(0);
			if (vo != null && vo.getHp().equals(hp)) {
				// 비번을 만들어서
				newPassword = makePassword(10);
				// DB에 업데이트하고 만들어진 비번을 리턴!!!
				// 비번을 암호화 해서
				String password = bCryptPasswordEncoder.encode(newPassword);
				// 비번 업데이트
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("password", password);
				map.put("idx", vo.getIdx() + "");
				memberDAO.updatePassword(map);
			}
		}
		return newPassword;
	}

	// 랜덤비밀번호 생성메서드
	@Override
	public String makePassword(int length) 
	{
		StringBuffer sb = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < length; i++) 
		{
			if ((i + 1) % 3 == 0) // 영어대문자
			{
				sb.append((char) ('A' + rnd.nextInt(26)));
			} else if ((i + 1) % 3 == 1) // 영어소문자
			{
				sb.append((char) ('a' + rnd.nextInt(26)));
			} else // 숫자
			{
				sb.append((char) ('0' + rnd.nextInt(10)));
			}
		}

		return sb.toString();
	}
	
	// 정보수정
	public MemberVO update(MemberVO vo)
	{
		log.debug("memberService.update 호출 : " + vo);
		if (vo != null) 
		{
			// 1. DB에서 해당 번호의 정보를 읽어온다
			MemberVO dbVO = memberDAO.selectByIdx(vo.getIdx());
			log.debug("memberService.update DB암호 : " + dbVO.getPassword());
			log.debug("memberService.update VO암호 : " + vo.getPassword());
			log.debug("memberService.update 암호 : " + bCryptPasswordEncoder.encode(vo.getPassword()));
			log.debug("memberService.update 암호 : " + bCryptPasswordEncoder.matches(vo.getPassword(), dbVO.getPassword()));
			// 2. 정보가 있다면 암호가 같은지 판단하여 암호가 같을때만 수정한다.
			// bcryptPasswordEncoder는 디코드 함수를 제공하지 않으므로
			// 반드시 matches()메서드를 이용하여 비교하여야 한다.
			if (dbVO != null && bCryptPasswordEncoder.matches(vo.getPassword(), dbVO.getPassword()))
			{
				MemberVO memberVO = null;
				log.debug("memberService.update 수정 : " + vo);
				memberDAO.update(vo);
				memberVO = memberDAO.selectByIdx(vo.getIdx());
				memberVO.setUpdateFlag(true);
				return memberVO;
			}
		}
		vo.setUpdateFlag(false);
		return vo;
	}
	
	/*
	// 회원에 의한 완전삭제(탈퇴)
	@Override
	public void deleteByUser(int idx, String password) 
	{
		// 패스워드가 일치하여 삭제가 성공적으로 이루어졌는지 확인하기 위한 변수
		boolean deleteSuccess = false;
		// 1. DB에서 해당 번호의 정보를 읽어온다
		MemberVO dbVO = memberDAO.selectByIdx(idx);
		log.debug("memberService.update DB암호 : " + dbVO.getPassword());
		log.debug("memberService.update VO암호 : " + password);
		log.debug("memberService.update 암호 : " + bCryptPasswordEncoder.encode(password));
		log.debug("memberService.update 암호 : " + bCryptPasswordEncoder.matches(password, dbVO.getPassword()));
		// 2. 정보가 있다면 암호가 같은지 판단하여 암호가 같을때만 삭제한다
		// bcryptPasswordEncoder는 디코드 함수를 제공하지 않으므로
		// 반드시 matches()메서드를 이용하여 비교하여야 한다.
		if (dbVO != null && bCryptPasswordEncoder.matches(password, dbVO.getPassword()))
		{
			memberDAO.delete(idx);
			deleteSuccess = true;
		}
	}
	*/
	
	// 비밀번호 변경
	@Override
	public boolean updatePassword(int idx, String userid, String cpassword, String npassword) 
	{
		log.debug("memberService.updatePassword 호출 : " + idx + ", " + userid + ", " + cpassword + ", " + npassword);
		boolean isChange = false;
		// 1. idx에 해당하는 vo를 가져온다.
		MemberVO vo = memberDAO.selectByIdx(idx);
		log.debug("memberService.updatePassword 호출 : " + vo);
		// 2. vo가 null이 아니면 비번이 같은지 비교한다.
		if (vo != null) 
		{
			if (bCryptPasswordEncoder.matches(cpassword, vo.getPassword())) // 입력한 비번과 db의 비번이 같다면
			{
				log.debug("memberService.updatePassword 호출 : 암호 변경!!!");
				// 3. 비번이 같으면 새로운 비번으로 업데이트 한다.
				String newPassword = bCryptPasswordEncoder.encode(npassword);
				// 비번 업데이트
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("password", newPassword);
				map.put("idx", idx + "");
				memberDAO.updatePassword(map);
				isChange = true;
			}
		}
		return isChange;
	}
	// 로그인 후 유저가 사용할 메서드 끝
		
	
	// 관리자 페이지에서 사용할 메서드 시작
	// 전체 레코드 개수 얻기
	@Override
	public int selectCount() {
		return memberDAO.selectCount();
	}
	
	// 전체 레코드 얻기
	@Override
	public List<MemberVO> selectAllMember() {
		return memberDAO.selectAllMember();
	}
	
	// role_admin을 가진 id 얻기
	@Override
	public List<String> selectAdminIdx() {
		return memberDAO.selectAdminIdx();
	}

	// idx로 1개 데이터 얻기
	@Override
	public MemberVO selectByIdx(int idx) {
		return memberDAO.selectByIdx(idx);
	}
	
	// userid로 1개 데이터 얻기
	@Override
	public List<MemberVO> selectByUserid(String userid) {
		return memberDAO.selectByUserid(userid);
	}
	
	// userid로 검색
	@Override
	public List<MemberVO> searchByUserid(String userid) {
		return memberDAO.searchByUserid(userid);
	}
	
	// name으로 검색
	@Override
	public List<MemberVO> searchByName(String name) {
		return memberDAO.searchByName(name);
	}
	// hp로 검색
	@Override
	public List<MemberVO> searchByHP(String hp) {
		return memberDAO.searchByHP(hp);
	}
	
	// 1페이지 레코드 얻기
	@Override
	public PagingVO<MemberVO> selectList(int currentPage, int pageSize, int blockSize) 
	{
		int totalCount = memberDAO.selectCount();
		PagingVO<MemberVO> pagingVO = null;
		pagingVO = new PagingVO<>(totalCount, currentPage, pageSize, blockSize);
		if (totalCount > 0) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			List<MemberVO> list = memberDAO.selectList(map);
			pagingVO.setList(list);
		}
		return pagingVO;
	}
	
	// 유저에 의한 자진탈퇴 or 관리자에 의한 완전삭제(추방)
	@Override
	public void delete(int idx)
	{
		memberDAO.delete(idx);
	}
	
	// 완전삭제하면서 member_roles테이블에 남아있는 회원 기록도 모두 삭제
	@Override
	public void deleteRoles(String userid) {
		memberDAO.deleteRoles(userid);
		
	}
	// 관리자 페이지에서 사용할 메서드 끝

	
	// userid에 따른 roles 얻기
	@Override
	public List<String> selectRoles(String userid)
	{		
		return memberDAO.selectRoles(userid);
	}



	

	

	


	

	
	
//	@Override
//	public List<String> selectUserid(MemberVO vo) 
//	{
//		return memberDAO.selectUserid(vo);
//	}



//	public static MemberServiceImpl instance = new MemberServiceImpl();
//	private MemberServiceImpl() {}
//	public static MemberServiceImpl getInstance() { return instance; }
//	
//	// 아이디중복확인
//	public int idCheck(String userid)
//	{
//		int count = 0;
//		
//		// db에 쿼리를 날려서 데이터개수를 세준다
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//
//			count = dao.selectUseridCount(sqlSession, userid);
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		} finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//		
//		return count;
//	}
//	
//	// 1개 데이터 보기 idx로
//	public MemberVO selectByIdx(int idx)
//	{
//		MemberVO vo = null;
//		
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			vo = dao.selectByIdx(sqlSession, idx);
//			
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		}	finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//		
//		return vo;
//	}
//	
//	// 1페이지 데이터 보기
//	public PagingVO<MemberVO> selectList(int currentPage, int pageSize, int blockSize)
//	{
//		PagingVO<MemberVO> pagingVO = null;
//		
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			int totalCount = dao.selectCount(sqlSession);
//			pagingVO = new PagingVO<>(totalCount, currentPage, pageSize, blockSize);
//			if(totalCount > 0)
//			{
//				HashMap<String, Integer> map = new HashMap<>();
//				map.put("startNo", pagingVO.getStartNo());
//				map.put("endNo", pagingVO.getEndNo());
//				List<MemberVO> list =  dao.selectList(sqlSession, map);
//				pagingVO.setList(list);
//			}
//			
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		}	finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//		
//		return pagingVO;
//	}
//	
//	// 저장
//	public void insert(MemberVO vo)
//	{
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			// 비밀번호 암호화해서 저장
//			String password = vo.getPassword();
//			// 유저아이디를 키값으로 설정하여 암호화후 다시 저장
//			password = CryptoUtil.encryptAES256(password, vo.getUserid());
//			vo.setPassword(password);
//			
//			// 데이터의 유효성검사 후 저장
//			dao.insert(sqlSession, vo);
//			
//			// 사용자에게 가입 축하 이메일을 보내기
//			String subject = vo.getName() + "님의 인부상사 회원 가입을 축하드립니다";
//			StringBuffer sb = new StringBuffer();
//			sb.append(vo.getName() + "님!!<br>");
//			sb.append("회원가입을 완료하려면 아래 링크를 클릭하세요~<br>");
//			sb.append("<a href='http://localhost:8080/MemberProject/useOk.jsp");
//			sb.append("?userid=" + URLEncoder.encode(vo.getUserid(),"UTF-8") + "'>가입완료</a>");
//			
//			MailUtil.sendMail(vo.getUserid(), subject, sb.toString());
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		}	finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//	}
//	
//	// 이메일 인증
//	public void useOk(String userid) // db에 쿼리를 날려서 use값을 1로 변경해 준다
//	{
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			// userid로 vo롤 얻어서, 그 vo에서 idx값을 추출해와서 해당 회원의 use값을 1로 업데이트 해야 한다
//			MemberVO vo = dao.selectByUserid(sqlSession, userid);
//			// 해당 id가 존재한다면
//			if(vo != null)
//			{
//				HashMap<String, Integer> map = new HashMap<>();
//				map.put("idx", vo.getIdx());
//				map.put("use", 1);
//				// use값을 업데이트 해줌
//				dao.updateUse(sqlSession, map);
//			}
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		}	finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//	}
//	
//	// 로그인 처리
//	public MemberVO loginOk(String userid, String password) 
//	{
//		log.debug("----------------------------------------------------------");
//		log.debug(userid + ", " + password);
//		log.debug("----------------------------------------------------------");
//		MemberVO vo = null;
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			vo = dao.selectByUserid(sqlSession, userid);
//			if(vo != null)
//			{
//				log.debug("---------------------------------------------------------");
//				log.debug(CryptoUtil.decryptAES256(password, userid) + ", " + vo.getPassword());
//				log.debug("---------------------------------------------------------");
//				if(vo.getUse() != 1 || !CryptoUtil.decryptAES256(password, userid).equals(vo.getPassword())) // 암호화 해서 비교
//				{
//					vo = null; // 비밀번호가 같지 않으면 vo를 비움
//				}
//				
//			}
//				
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		}	finally {
//			if(sqlSession != null)
//				sqlSession.close();
//		}
//		
//		return vo;
//	}
//
//	// 아이디 찾기
//	public String findUserid(String name, String hp) 
//	{
//		String userid = null;
//
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//
//			// DAO에 넘기기 위해 VO를 만들고
//			MemberVO vo = new MemberVO();
//			vo.setName(name);
//			vo.setHp(hp);
//			// 사용자 아이디를 리스트로 받고
//			List<String> list = dao.selectUserid(sqlSession, vo);
//			if (list != null && list.size() > 0) // 사용자 아이디가 있으면
//			{
//				userid = list.get(0); // 대입
//			}
//
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		} finally {
//			if (sqlSession != null)
//					sqlSession.close();
//		}
//
//		return userid;
//	}
//	
//	// 비밀번호 찾기
//	public String findPassword(String userid, String hp) 
//	{
//		String newPassword = null;
//		
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			MemberVO vo = dao.selectByUserid(sqlSession, userid);
//			if(vo != null && vo.getHp().equals(hp)) // 입력한 id가 존재하면서 입력한 휴대폰번호와 일치한다면
//			{
//				// 비밀번호 생성 후 암호화하여 DB에 업데이트하고, 새 비밀번호를 리턴한다
//				newPassword = makePassword(10);
//				String password = CryptoUtil.encryptAES256(newPassword, vo.getUserid());
//				HashMap<String, String> map = new HashMap<>();
//				map.put("password", password);
//				map.put("idx", vo.getIdx() + "");
//				dao.updatePassword(sqlSession, map);
//			}
//			
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		} finally {
//			if (sqlSession != null)
//				sqlSession.close();
//		}
//		
//		return newPassword;
//	}
//	
//	// 길이만큼 무작위 비밀번호를 만들어주는 메서드
//	public String makePassword(int length)
//	{
//		StringBuffer sb = new StringBuffer();
//		Random rnd = new Random();
//		for(int i = 0; i < length; i++)
//		{
//			if((i + 1) % 3 == 0) // 영어대문자
//			{
//				sb.append((char)('A' + rnd.nextInt(26)));
//			}
//			else if((i + 1) % 3 == 1) // 영어소문자
//			{
//				sb.append((char)('a' + rnd.nextInt(26)));
//			}
//			else // 숫자
//			{
//				sb.append((char)('0' + rnd.nextInt(10)));
//			}
//		}
//		
//		return sb.toString();
//	}
//	
//	
//	// 비밀번호 변경
//	public boolean updatePassword(int idx, String userid, String cpassword, String npassword) {
//		boolean isChange = false;
//
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//
//			// idx에 해당하는 vo를 가져온다
//			MemberVO vo = dao.selectByIdx(sqlSession, idx);
//			if (cpassword.equals(CryptoUtil.decryptAES256(vo.getPassword(), userid))) // vo가 존재한다면 입력한 비밀번호가 db의 비밀번호를 복호화한 비밀번호와 일치하는지 비교								
//			{
//				String newPassword = CryptoUtil.encryptAES256(npassword, userid); // 새 비밀번호를 다시 암호화하여 저장
//				// 비밀번호 업데이트
//				HashMap<String, String> map = new HashMap<>();
//				map.put("password", newPassword);
//				map.put("idx", idx + "");
//				dao.updatePassword(sqlSession, map);
//				isChange = true;
//			}
//
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		} finally {
//			if (sqlSession != null)
//				sqlSession.close();
//		}
//
//		return isChange;
//	}
//		
//	// 정보수정
//	public MemberVO update(MemberVO vo) {
//		MemberVO memberVO = null;
//
//		SqlSession sqlSession = null;
//		MemberDAO dao = null;
//		try {
//			sqlSession = MyBatisUtil.getSqlSession();
//			dao = MemberDAO.getInstance();
//			// ----------------------------
//			
//			MemberVO dbVO = dao.selectByIdx(sqlSession, vo.getIdx());
//			if(dbVO != null && vo.getPassword().equals(CryptoUtil.decryptAES256(dbVO.getPassword(), dbVO.getUserid())))
//			{
//				dao.update(sqlSession, vo);
//				memberVO = dao.selectByIdx(sqlSession, vo.getIdx());
//			}
//
//			// ----------------------------
//			sqlSession.commit();
//		} catch (Exception e) {
//			sqlSession.rollback();
//			e.printStackTrace();
//		} finally {
//			if (sqlSession != null)
//				sqlSession.close();
//		}
//
//		return memberVO;
//	}
}
