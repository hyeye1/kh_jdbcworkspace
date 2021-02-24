package com.kh.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import java.util.ArrayList;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	
	public int insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		if(result>0) {
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
	
	
	
	
	
	
	
	
	
}
