package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	/*
	 * 기존의 방식 : diver관련 정보와 접속할 db관련한 정보를 자바소스코드내에 명시적으로 작성 => 정적코딩방식
	 *   > 문제점 : 개발 완료 후 dbms가 바뀐다거나 접속할 db에 대한 정보가 변경될 경우 수정해야됨(단, 자바코드를 수정해야될거임)
	 *   		   => 뿐만아니라 수정된 자바코드를 반영시키고자 한다면 프로그램을 재구동 시켜야된다.
	 *   
	 *   > 해결방식 : driver 정보 및 db정보를 별도로 관리하는 . properties 파일로 만들어서 거기서 기술하기!
	 *   			자바에서는 실시간으로 파일에 기록되어있는 정보를 가져와서 사용하는 식으로 수정 => 동적 코딩방식
	 */
	
	// 1. DB와 접속된 Connection 객체 생성해서 반환시켜주는 메소드
	public static Connection getConnection() {
		 
		Connection conn = null;
		
		Properties prop = new Properties();
		
		try { 
			prop.load(new FileInputStream("resources/driver.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"),
											   prop.getProperty("username"),
											   prop.getProperty("password"));
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 2. 전달받은 JDBC용 객체를 반납시켜주는 메소드
	// 2_1) Connection 객체 전달 받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//2_2) Statement 객체 전달받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 2_3) ResultSet 객체 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3. 전달받은 Connection 객체를 가지고 트랜젝션 처리해주는 메소드
	// 3_1) 전달받은 Connection 객체를 가지고 commit 시켜주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_2) 전달받은 Connection 객체를 가지고 rollback시켜주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}















