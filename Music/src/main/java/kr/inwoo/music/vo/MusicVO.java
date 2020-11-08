package kr.inwoo.music.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicVO 
{
	int music_idx;
	String url;
	String artist;
	String name;
	Date regDate;
}
