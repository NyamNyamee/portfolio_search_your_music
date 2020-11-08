package kr.inwoo.music.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MusicList {
	String name; // 노래제목
	Album album; // 앨범
	List<ArtistList> artistList = new ArrayList<ArtistList>(); // 가수명 배열
}