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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findCartGoods(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addGoodsInCart(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean modifyCartQty(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeCartGoods(int cart_id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
