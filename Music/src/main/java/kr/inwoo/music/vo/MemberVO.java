package kr.inwoo.music.vo;


import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	private int idx;
	private String userid;
	private String password;
	private String name;
	private String nickname;
	private String hp;
	private String birth;
	private char gender;
	private int use;
	private int lvl;
	private String zipcode;
	private String addr1;
	private String addr2;
	private Date regDate;
	
	private boolean updateFlag;
}
