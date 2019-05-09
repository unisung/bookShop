package com.bookshop01.admin.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.admin.goods.dao.AdminGoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;


@Service("adminGoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	AdminGoodsDAO adminGoodsDAO;

	@Override
	public int addNewGoods(Map newGoodsMap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map goodsDetail(int goods_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List goodsImageFile(int goods_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyGoodsInfo(Map goodsMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrderVO> listOrderGoods(Map condMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyOrderGoods(Map orderMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGoodsImage(int image_id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewGoodsImage(List imageFileList) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
