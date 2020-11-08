package kr.inwoo.music.dao;

import java.util.HashMap;
import java.util.List;

import kr.inwoo.music.vo.CartVO;

public interface CartDAO {
	void insertCart(CartVO vo);
	List<CartVO> selectCartList(String userid);
	int selectCartCount(HashMap<String, Object> map);
	void deleteCart(int cart_idx);
	void deleteAllCart(String userid);
}
