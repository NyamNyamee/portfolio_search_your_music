package kr.inwoo.music.vo;


import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/*
CREATE SEQUENCE member_role_idx_seq;
DROP TABLE member_roles;
CREATE TABLE member_roles (
  role_idx NUMBER(11)  PRIMARY KEY,
  userid varchar2(45) NOT NULL,
  role varchar2(45) NOT NULL
);
*/
@XmlRootElement
@Data
public class MemberRoleVO {
	private int 	role_idx;
	private String 	userid;
	private String  role;
}
