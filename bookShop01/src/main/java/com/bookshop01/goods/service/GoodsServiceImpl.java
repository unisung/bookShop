package com.bookshop01.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.goods.dao.GoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;

@Service("goodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	GoodsDAO goodsDAO;

	@Override
	public Map<String, List<GoodsVO>> listGoods() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map goodsDetail(String _goods_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GoodsVO> searchGoods(String searchWord) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
