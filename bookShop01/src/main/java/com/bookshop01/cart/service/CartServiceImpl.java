package com.bookshop01.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.cart.dao.CartDAO;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;

@Service("cartService")
@Transactional(propagation=Propagation.REQUIRED)
public class CartServiceImpl  implements CartService{
	@Autowired
	CartDAO cartDAO;

	@Override
	public Map<String, List> myCartList(CartVO cartVO) throws Exception {
		System.out.println(cartVO.getCart_id());
		Map<String,List> cartMap = new HashMap<String, List>();
		List<CartVO> myCartList =cartDAO.selectCartList(cartVO);
	
		if(myCartList.size()==0) 
			 return null;//카트에 저장된 상품이 없으면 리턴
		List<GoodsVO> myGoodsList = cartDAO.selectGoodsList(myCartList);
		cartMap.put("myCartList",myCartList);
		cartMap.put("myGoodsList", myGoodsList);
		return cartMap;
	}
	

	@Override
	public boolean findCartGoods(CartVO cartVO) throws Exception {
		return cartDAO.selectCountInCart(cartVO);
	}

	@Override
	public void addGoodsInCart(CartVO cartVO) throws Exception {
	  cartDAO.insertGoodsInCart(cartVO);
	}

	@Override
	public boolean modifyCartQty(CartVO cartVO) throws Exception {
		cartDAO.updateCartGoodsQty(cartVO);
		return true;
	}


	@Override
	public void removeCartGoods(int cart_id, int goods_id, String member_id) {
		cartDAO.deleteCartGoods(cart_id,goods_id,member_id);
	}

	
	
	
}
