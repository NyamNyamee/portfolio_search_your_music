package kr.inwoo.music.service;

import java.util.List;

import kr.inwoo.music.vo.CartVO;

public interface CartService {
	void insertCart(CartVO vo);
	List<CartVO> selectCartList(String userid);
	int selectCartCount(String userid, int music_idx);
	void deleteCart(int cart_idx);
	void deleteAllCart(String userid);
}
