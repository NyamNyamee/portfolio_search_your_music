package kr.inwoo.music.service;

import java.util.HashMap;
import java.util.List;

import kr.inwoo.music.vo.MemberVO;
import kr.inwoo.music.vo.PagingVO;

public interface MemberService 
{
	// 회원가입 후 인증 시작
	// 아이디중복확인
	public int idCheck(String userid);
	
	// 저장
	public void insert(MemberVO vo);
	
//	// 이메일 인증
//	public void useOk(String userid); // db에 쿼리를 날려서 use값을 1로 변경해 준다
	
	// 인증수정
	public void updateUse(HashMap<String, Integer> map);
	
	// user_role 부여
	public void userRole(String userid);
	
	// member_role부여
	public void memberRole(String userid);
	// 회원가입 후 인증 끝
	
	
	// 로그인 후 유저가 사용하는 메서드 시작
	// 아이디 찾기
	public String selectUserid(String name, String hp);
	
//	// id 찾기
//	public List<String> selectUserid(MemberVO vo);
	
	// 비밀번호 찾기
	public String findPassword(String userid, String hp);
	
	// 길이만큼 무작위 비밀번호를 만들어주는 메서드
	public String makePassword(int length);
	
	// 정보수정
	public MemberVO update(MemberVO vo);
	
	/*
	// 회원탈퇴
	public void deleteByUser(int idx, String password);
	*/
	
	// 비밀번호 변경
	public boolean updatePassword(int idx, String userid, String cpassword, String npassword);
	// 로그인 후 유저가 사용하는 메서드 끝
	
	
	// 관리자 페이지에서 사용하는 메서드 시작
	// 전체 레코드 개수 얻기
	public int selectCount();
		
	// 전체 레코드 얻기
	public List<MemberVO> selectAllMember();
	
	// role_admin을 가진 id 얻기
	public List<String> selectAdminIdx();
		
	// 한개 레코드 얻기 idx로
	public MemberVO selectByIdx(int idx);

	// 한개 레코드 얻기 userid로
	public List<MemberVO> selectByUserid(String userid);
	
	// userid로 검색
	public List<MemberVO> searchByUserid(String userid);
		
	// name으로 검색
	public List<MemberVO> searchByName(String name);
		
	// hp로 검색
	public List<MemberVO> searchByHP(String hp);
		
	// 1페이지 레코드 얻기
	public PagingVO<MemberVO> selectList(int currentPage, int pageSize, int blockSize);
	
	// 관리자에 의한 완전삭제(추방)
	public void delete(int idx);
	
	// 완전삭제하면서 member_roles테이블에 남아있는 회원 기록도 모두 삭제
	public void deleteRoles(String userid);
	// 관리자 페이지에서 사용하는 메서드 끝
	
	
	// userid에 따른 roles 얻기
	public List<String> selectRoles(String userid);
}
