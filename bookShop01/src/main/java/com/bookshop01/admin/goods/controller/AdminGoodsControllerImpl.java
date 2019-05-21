package com.bookshop01.admin.goods.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.member.vo.MemberVO;

@Controller("adminGoodsController")
@RequestMapping(value="/admin/goods")
public class AdminGoodsControllerImpl extends BaseController  implements AdminGoodsController{
	private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";
   
	 @Autowired
	 AdminGoodsService adminGoodsService;
	
	@Override
	@RequestMapping(value="/adminGoodsMain.do",
	                         method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView adminGoodsMain(Map<String, String> dateMap,
			                                                HttpServletRequest request,
			                                                HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");

		if(memberVO!=null && memberVO.getMember_id()!=null) {
			mav.setViewName(viewName);
	
		session.setAttribute("side_menu", "admin_mode");//마이페이지 사이드 메뉴 설정

		String fixedSearchPeriod=dateMap.get("fixedSearchPeriod");
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		String beginDate=null, endDate=null;
		
		String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate=tempDate[0];
		endDate=tempDate[1];
		System.out.print("beginDate:"+beginDate);
		System.out.print("endDate:"+endDate);
		
		dateMap.put("beginDate",beginDate);
		dateMap.put("endDate",endDate);
		
		Map<String,Object> condMap = new HashMap<String,Object>();
		if(section==null) {
			section="1";
		}
		condMap.put("section",section);
		if(pageNum==null) {
			pageNum="1";
		}
		condMap.put("pageNum",pageNum);
		condMap.put("beginDate",beginDate);
		condMap.put("endDate",endDate);
		List<GoodsVO> newGoodsList=adminGoodsService.listNewGoods(condMap);
		System.out.println("newGoodsList:"+newGoodsList.size());
		mav.addObject("newGoodsList",newGoodsList);
		
		String beginDate1[]=beginDate.split("-");
		String endDate2[]=endDate.split("-");
		mav.addObject("beginYear",beginDate1[0]);
		mav.addObject("beginMonth",beginDate1[1]);
		mav.addObject("beginDay",beginDate1[2]);
		
		mav.addObject("endYear",endDate2[0]);
		mav.addObject("endMonth",endDate2[1]);
		mav.addObject("endDay",endDate2[2]);
		
		mav.addObject("section",section);
		mav.addObject("pageNum",pageNum);
		
		}else {//로그인시 회원정보가 존재하지 않으면....
			mav.setViewName("/member/loginForm");
		}
		return mav;
	}

	@Override
	@RequestMapping(value="/addNewGoods.do",method=RequestMethod.POST)
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		  multipartRequest.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  String imageFileName=null;
		  
		  Map newGoodsMap = new HashMap();
		  Enumeration enu = multipartRequest.getParameterNames();
		  while(enu.hasMoreElements()) {
			  String name=(String)enu.nextElement();
			  String value=multipartRequest.getParameter(name);
			  newGoodsMap.put(name, value);
		  }
		  
		  HttpSession session = multipartRequest.getSession();
		  MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		  System.out.println("memberInfo:"+memberVO.getMember_id());
		  String reg_id =memberVO.getMember_id();
		  
		  List<ImageFileVO> imageFileList = upload(multipartRequest);
		  String message=null;
		  ResponseEntity resEntity=null;
		  HttpHeaders responseHeaders=new HttpHeaders();
		  responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		  
		  if(imageFileList!=null && imageFileList.size()!=0) {
			  for(ImageFileVO imageFileVO:imageFileList) {
				  imageFileVO.setReg_id(reg_id);
			  }
			  newGoodsMap.put("imageFileList",imageFileList);
		  }else {
			  message ="<script>";
		      message+="alert('상품을 등록할수 없습니다. 다시 시도해 주세요');";
		      message+="location.href='"+multipartRequest.getContextPath()+"/admin/goods/addNewGoodsForm.do';";
		      message+="</script>";
		      
		      resEntity=new ResponseEntity(message,responseHeaders,HttpStatus.OK);
		      return resEntity;
		  }
		

		  try {
			    //db에 상품 등록 처리
			      int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			      if(imageFileList!=null && imageFileList.size()!=0) {
			    	  for(ImageFileVO imageFileVO:imageFileList) {
			    		  imageFileName=imageFileVO.getFileName();
			    		  File srcFile=new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
			    		  File desDir=new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
			    		  FileUtils.moveFileToDirectory(srcFile, desDir, true);
			    	  }
			      }
			      message ="<script>";
			      message+="alert('새상품을 추가하였습니다');";
			      message+="location.href='"+multipartRequest.getContextPath()+"/admin/goods/addNewGoodsForm.do';";
			      message+="</script>";
		  }catch(Exception e) {
			  if(imageFileList!=null && imageFileList.size()!=0) {
		    	  for(ImageFileVO imageFileVO:imageFileList) {
		    		  imageFileName=imageFileVO.getFileName();
		    		  File srcFile=new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
		    		  srcFile.delete();
		    	  }
		      }
		      message ="<script>";
		      message+="alert('오류가 발생하였습니다. 다시 시도해 주세요');";
		      message+="location.href='"+multipartRequest.getContextPath()+"/admin/goods/addNewGoodsForm.do';";
		      message+="</script>";
		      e.printStackTrace();
		  }
		  resEntity=new ResponseEntity(message,responseHeaders,HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/modifyGoodsInfo.do",method=RequestMethod.POST)
	public ResponseEntity modifyGoodsInfo(@RequestParam("goods_id")String goods_id, 
			                                                 @RequestParam("attribute") String attribute,
			                                                 @RequestParam("value")String value,  
			                                                 HttpServletRequest request,
			                                                 HttpServletResponse response) throws Exception {
		Map<String,String> goodsMap = new HashMap<String,String>();
		goodsMap.put("goods_id",goods_id);
		goodsMap.put(attribute, value);
		adminGoodsService.modifyGoodsInfo(goodsMap);
		
		
		
		String message=null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders=new HttpHeaders();
		message = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders,HttpStatus.OK);
		return resEntity;
	}

	@Override
	public void removeGoodsImage(int goods_id, int image_id, String imageFileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequestMapping(value="/addNewGoodsImage.do",method=RequestMethod.POST)
	public void addNewGoodsImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		Map goodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			goodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		int image_id=0;
		try {
			imageFileList =upload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id"));
					image_id = Integer.parseInt((String)goodsMap.get("image_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setImage_id(image_id);
					imageFileVO.setReg_id(reg_id);
				}
				
			    adminGoodsService.modifyGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
		
		
	}

	@Override
	@RequestMapping(value="/modifyGoodsImageInfo.do" ,method={RequestMethod.POST})
	public void modifyGoodsImageInfo(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		Map goodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			goodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		int image_id=0;
		try {
			imageFileList =upload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id"));
					image_id = Integer.parseInt((String)goodsMap.get("image_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setImage_id(image_id);
					imageFileVO.setReg_id(reg_id);
				}
				
			    adminGoodsService.modifyGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/modifyGoodsForm.do",method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView modifyGoodsForm(@RequestParam("goods_id") int goods_id,
			                                                  HttpServletRequest request, 
			                                                  HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		Map goodsMap = adminGoodsService.goodsDetail(goods_id);
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}
	

}
