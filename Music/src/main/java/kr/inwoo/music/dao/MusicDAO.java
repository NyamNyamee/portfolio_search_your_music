package kr.inwoo.music.dao;


import java.util.HashMap;
import java.util.List;

import kr.inwoo.music.vo.MusicVO;

public interface MusicDAO {
	int selectCount();	
	List<MusicVO> selectByAll(String name);
	List<MusicVO> selectByName(String name);
	List<MusicVO> selectByArtist(String artist);
	int selectMusicCount(HashMap<String, String> map);
	void insert(MusicVO vo);
	void update(MusicVO vo);
	void deleteCart(int music_idx);
	void delete(int music_idx);
}
