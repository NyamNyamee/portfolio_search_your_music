package kr.inwoo.music.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inwoo.music.dao.MusicDAO;
import kr.inwoo.music.vo.MusicVO;
import lombok.extern.slf4j.Slf4j;

@Service("musicService")
@Transactional
@Slf4j
public class MusicServiceImpl implements MusicService
{
	@Autowired
	MusicDAO musicDAO;
	
	@Override
	public int selectCount() {
		return musicDAO.selectCount();
	}
	
	@Override
	public List<MusicVO> selectByAll(String name) {
		return musicDAO.selectByAll(name);
	}
	
	@Override
	public List<MusicVO> selectByName(String name) {
		return musicDAO.selectByName(name);
	}

	@Override
	public List<MusicVO> selectByArtist(String artist) {
		return musicDAO.selectByArtist(artist);
	}
	
	@Override
	public int selectMusicCount(String artist, String name) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("artist", artist);
		map.put("name", name);
		return musicDAO.selectMusicCount(map);
	}
	
	@Override
	public void insert(MusicVO vo) {
		musicDAO.insert(vo);
	}

	@Override
	public void update(MusicVO vo) {
		musicDAO.update(vo);
	}

	@Override
	public void deleteCart(int music_idx) {
		musicDAO.deleteCart(music_idx);
		
	}
	
	@Override
	public void delete(int music_idx) {
		musicDAO.delete(music_idx);
	}

	


}
