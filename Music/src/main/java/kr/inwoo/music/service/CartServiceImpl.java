package kr.inwoo.music.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inwoo.music.dao.CartDAO;
import kr.inwoo.music.vo.CartVO;

@Service("cartService")

public class CartServiceImpl implements CartService 
{
	@Autowired
	CartDAO cartDAO;
	
	@Override
	public void insertCart(CartVO vo) {
		cartDAO.insertCart(vo);
	}

	@Override
	public List<CartVO> selectCartList(String userid) {
		return cartDAO.selectCartList(userid);
	}

	@Override
	public int selectCartCount(String userid, int music_idx) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("music_idx", music_idx);
		return cartDAO.selectCartCount(map);
	}

	@Override
	public void deleteCart(int cart_idx) {
		cartDAO.deleteCart(cart_idx);
	}

	@Override
	public void deleteAllCart(String userid) {
		cartDAO.deleteAllCart(userid);
	}

}
