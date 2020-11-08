package kr.inwoo.music.dao;

import java.util.HashMap;
import java.util.List;

import kr.inwoo.music.vo.MemberVO;

public interface MemberDAO 
{
	// 회원가입 후 인증 시작
	// id중복확인:id를 받아서 db에 저장된 그 id의 개수를 리턴
	public int selectUseridCount(String userid);

	// 저장
	public void insert(MemberVO vo);
	
	// 인증수정
	public void updateUse(HashMap<String, Integer> map);
	
	// user_role부여
	public void userRole(String userid);
	
	// member_role부여
	public void memberRole(String userid);
	// 회원가입 후 인증 끝
	
	
	// 로그인 후 유저가 사용하는 메서드 시작
	// id찾기
	public List<String> selectUserid(MemberVO vo);
	
	// 정보수정
	public void update(MemberVO vo);
	
	// 비번수정
	public void updatePassword(HashMap<String, String> map);	
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
	public List<MemberVO> selectList(HashMap<String, Integer> map);

	// 완전삭제
	public void delete(int idx);
	
	// 완전삭제하면서 member_roles테이블에 남아있는 회원 기록도 모두 삭제
	public void deleteRoles(String userid);
	// 관리자 페이지에서 사용하는 메서드 끝
	
	
	// userid에 따른 roles 얻기
	public List<String> selectRoles(String userid);

}
