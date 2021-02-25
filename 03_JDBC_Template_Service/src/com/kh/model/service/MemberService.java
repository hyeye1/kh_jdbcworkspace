package com.kh.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import java.util.ArrayList;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	

	public int insertMember(Member m) {
		
		// Connection 객체 생성 
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		// 트랜젝션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public ArrayList<Member> selectList() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Member> list = new MemberDao().selectList(conn);
				
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		
		
		Connection conn = JDBCTemplate.getConnection();
		
		Member m = new MemberDao().selectByUserId(conn, userId);

		JDBCTemplate.close(conn);
		
		return m;
		
	}
	
	
	
	
	
	
	public int deleteMember(String userId) {

		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId);
		
		return result;
	}

	
	
	
	
	public ArrayList<Member> selectByUserName(String keyword) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	
	
	
	
	
}
