package com.bookshop01.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.member.dao.MemberDAO;
import com.bookshop01.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO login(Map loginMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMember(MemberVO memberVO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String overlapped(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
