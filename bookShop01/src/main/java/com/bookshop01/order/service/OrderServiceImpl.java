package com.bookshop01.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.bookshop01.order.dao.OrderDAO;
import com.bookshop01.order.vo.OrderVO;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDAO orderDAO;

	@Override
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderVO findMyOrder(String order_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
