package com.bookshop01.member.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.bookshop01.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl  implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberVO login(Map loginMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertNewMember(MemberVO memberVO) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String selectOverlappedID(String id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
	
}
