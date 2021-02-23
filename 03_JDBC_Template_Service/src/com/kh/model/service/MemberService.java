package com.kh.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	
	public void insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		new MemberDao().insertMember(conn, m);
	}
}
