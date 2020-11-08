package kr.inwoo.music.service;


import java.util.List;

import kr.inwoo.music.vo.MusicVO;

public interface MusicService {
	int selectCount();
	List<MusicVO> selectByAll(String name);
	List<MusicVO> selectByName(String name);
	List<MusicVO> selectByArtist(String artist);
	int selectMusicCount(String artist, String name);
	void insert(MusicVO vo);
	void update(MusicVO vo);
	void deleteCart(int music_idx);
	void delete(int music_idx);
}
