package com.bookshop01.goods.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;

import net.sf.json.JSONObject;

@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	@Autowired
	GoodsService goodsService;

	@Override
	@RequestMapping(value="/goodsDetail.do", method=RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id")String goods_id, 
			                                         HttpServletRequest request, 
			                                         HttpServletResponse response)throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession();
		Map goodsMap = goodsService.goodsDetail(goods_id);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsMap", goodsMap);
		GoodsVO goodsVO = (GoodsVO)goodsMap.get("goodsVO");
		addGoodsInQuick(goods_id,goodsVO,session);
		return mav;
		
	}

	@Override
	public String keywordSearch(String keyword, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value="/goodsList.do",method=RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord, 
			                                          HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   String viewName = (String)request.getAttribute("viewName");
	   List<GoodsVO> goodsList = goodsService.searchGoods(searchWord);
	   ModelAndView mav = new ModelAndView(viewName);
	   mav.addObject("goodsList", goodsList);
		return mav;
	}
	

	private void addGoodsInQuick(String goods_id, GoodsVO goodsVO, HttpSession session) {
	 boolean already_existed=false;
	 List<GoodsVO> quickGoodsList;//최근본 상품 저장 list
	 quickGoodsList=( List<GoodsVO>)session.getAttribute("quickGoodsList");
	 
	 if(quickGoodsList!=null) {//추가
		 if(quickGoodsList.size() < 4) {
			 for(int i=0;i<quickGoodsList.size();i++) {
				 GoodsVO _goodsBean =(GoodsVO)quickGoodsList.get(i);
				 if(goods_id.equals(_goodsBean.getGoods_id())) {
					  already_existed=true;
					  break;
				 }
			 }
			 if(already_existed==false) {
				 quickGoodsList.add(goodsVO);
			 }
		 }
	 }else {//최초
		 quickGoodsList = new ArrayList<GoodsVO>();
		 quickGoodsList.add(goodsVO);
	 }
	 session.setAttribute("quickGoodsList", quickGoodsList);
	 session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
}
