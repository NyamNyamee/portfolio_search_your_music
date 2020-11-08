CREATE SEQUENCE member_idx_seq;

CREATE TABLE MEMBER(
	idx NUMBER PRIMARY KEY, -- 회원번호
	userid varchar2(50) NOT NULL, -- 아이디
	password varchar2(80) NOT NULL, -- 비밀번호
	name varchar2(30) NOT NULL, -- 이름
	nickname varchar2(20) NOT NULL, -- 닉네임
	hp varchar2(50) NOT NULL, -- 전화번호
	birth varchar2(20), -- 생년월일
	gender char(1) DEFAULT 'M', -- 성별
	use NUMBER(1) DEFAULT 0, -- 사용가능단계
	lvl NUMBER DEFAULT 0, -- 회원등급
	zipcode varchar2(10), -- 우편번호
	addr1 varchar2(100), -- 도로명주소
	addr2 varchar2(100), -- 상세주소
	regDate timestamp default SYSDATE -- 가입일
);

DROP TABLE MEMBER;
-- sample data 입력
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2)
VALUES
	(member_idx_seq.nextval,'admin@daum.net','1234','주인장','대빵','1','2020-01-01','1','1','1');
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2)
VALUES
	(member_idx_seq.nextval,'root@naver.com','1234','주인장','대빵','1','2020-01-01','1','1','1');
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2)
VALUES
	(member_idx_seq.nextval,'admin2@naver.com','1234','주인장','대빵','1','2020-01-01','1','1','1');
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2,use)
VALUES
	(member_idx_seq.nextval,'admin@naver.com','1234','주인장','대빵','1','2020-01-01','1','1','1','1');
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2)
VALUES
	(member_idx_seq.nextval,'root@daum.net','1234','주인장','대빵','1','2020-01-01','1','1','1');
INSERT INTO MEMBER
	(idx, userid, password, name, nickname,hp,birth,zipcode,addr1,addr2)
VALUES
	(member_idx_seq.nextval,'admin2@daum.net','1234','주인장','대빵','1','2020-01-01','1','1','1');
COMMIT;
SELECT * FROM MEMBER ORDER BY userid;
SELECT * FROM MEMBER;

SELECT * FROM tabs;


SELECT * FROM MEMBER;




CREATE SEQUENCE member_role_idx_seq;

DROP TABLE member_roles;
CREATE TABLE member_roles (
  role_idx NUMBER(11)  PRIMARY KEY,
  userid varchar2(45) NOT NULL,
  role varchar2(45) NOT NULL
);
INSERT INTO MEMBER_ROLES (role_idx,userid,role) VALUES 
(member_role_idx_seq.nextval,'jopelee2@gmail.com','ROLE_ADMIN');
INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'jopelee2@gmail.com','ROLE_DBA');
INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'jopelee2@gmail.com','ROLE_USER');
INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'jopelee2@gmail.com','ROLE_MEMBER');

INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'jopelee2@naver.com','ROLE_USER');
INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'jopelee2@naver.com','ROLE_MEMBER');

INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'bigdata202005@gmail.com','ROLE_USER');
INSERT INTO MEMBER_ROLES (role_idx,userid,role)  VALUES 
(member_role_idx_seq.nextval,'bigdata202005@gmail.com','ROLE_MEMBER');
COMMIT;

SELECT * FROM member_roles;