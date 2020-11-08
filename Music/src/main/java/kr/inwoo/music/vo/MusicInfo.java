package kr.inwoo.music.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MusicInfo
{
	List<MusicList> list = new ArrayList<MusicList>();
}