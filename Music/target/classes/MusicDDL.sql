DROP TABLE music;
DROP SEQUENCE music_idx_seq;

CREATE SEQUENCE music_idx_seq;
CREATE TABLE Music
(
	music_idx number PRIMARY KEY,
	url varchar2(300), -- 이미지 url
	artist varchar2(100) NOT null, -- 가수
	name varchar2(200) NOT null, -- 노래제목
	totalCount NUMBER,
	currentPage NUMBER,
	regDate timestamp DEFAULT SYSDATE
);

COMMIT;

INSERT INTO MUSIC (music_idx, url, ARTIST , name, TOTALCOUNT , CURRENTPAGE) values(music_idx_seq.nextval, 'http:dada', 'bibi', 'binu', 100, 1);
UPDATE MUSIC SET URL ='https://cdn.music-flo.com/image/album/442/965/04/04/404965442_5f3b644f.jpg?1597727824677/dims/resize/75x75/quality/90', artist='015B', name='공기 한정판(Feat.이형은)' WHERE music_idx=1;
SELECT * FROM music WHERE name='binu'; 
SELECT * FROM music WHERE name LIKE CONCAT(CONCAT('%', 'bi'), '%');
SELECT * FROM MUSIC WHERE NAME LIKE '%bi%';

SELECT * FROM music WHERE ARTIST LIKE CONCAT(CONCAT('%', 'nu'), '%');
SELECT * FROM music WHERE NAME LIKE CONCAT(CONCAT('%', 'n'), '%') OR ARTIST LIKE CONCAT(CONCAT('%', 'b'), '%');



DROP TABLE music_cart;
DROP SEQUENCE music_cart_idx_seq;

CREATE SEQUENCE music_cart_idx_seq;
CREATE TABLE music_cart(
	cart_idx number PRIMARY KEY,
	userid varchar2(50),
	music_idx number
);

-- ALTER TABLE MUSIC_CART ADD CONSTRAINT cart_userid_fk FOREIGN KEY (userid) REFERENCES MEMBER (userid);
ALTER TABLE MUSIC_CART ADD CONSTRAINT cart_music_fk FOREIGN KEY (music_idx) REFERENCES music (music_idx);

SELECT * FROM MEMBER;
SELECT * FROM MUSIC;
SELECT * FROM music_cart;

select 
			mc.cart_idx,
			mc.userid,
			m.music_idx,
			m.url,
			m.artist,
			m.name,
			m.regDate
		from 
			member mb, music m, music_cart mc
		where
			mb.userid = mc.userid AND
			m.music_idx = mc.music_idx AND
			mc.userid = 'jopelee2@gmail.com';
