package kr.inwoo.music.vo;

import java.util.Date;

import lombok.Data;

/*
 * CREATE TABLE music_cart(
	cart_idx number PRIMARY KEY,
	userid varchar2(50),
	music_idx number
);
 */
@Data
public class CartVO {
	int cart_idx;
	String userid;
	int music_idx;
	String url;
	String artist;
	String name;
	Date regDate;
}
