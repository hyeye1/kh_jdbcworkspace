package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// Dao ( Data Access Object ) 
// Controller로 부터 호출된 기능을 수행하기 위해
// DB에 직접적으로 접근 한 후 해당 SQL문 실행 및 결과 받기 (JDBC)
public class MemberDao {
	
	/*
	 * * JDBC용 객체
	 * - Connection : DB의 연결정보를 담고있는 객체
	 * - [Prepared] Statement : 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아내는 객체
	 * - ResultSet : 만일 실행한 sql문이 select문일 경우 조회된 결과들이 담겨있는 객체
	 * 
	 * * JDBC 처리 순서
	 * 1) jdbc driver 등록 : 해당 DBMS가 제공하는 클래스 등록
	 * 2) Connection 객체 생성 : 접속하고자 하는 DB정보를 입력해서 DB에 접속하면서 생성
	 * 3) Statement 객체 생성 : Connection객체를 이용해서 생성 
	 * 4) SQL문 전달하면서 실행 : Statement객체를 이용해서 sql문 실행 
	 * 			> SELECT문일 경우 - executeQuery메소드를 이용해서 실행
	 * 			> DML문일 경우 - executeUpdate메소드를 이용해서 실행
	 * 5) 결과 받기 
	 * 			> SELECT문일 경우 - ResultSet객체 (조회된 데이터들이 담겨있다.) => 6-1)
	 * 			> DML문일 경우 - int(처리된 행 수)								=> 6-2)
	 * 
	 * 6-1) Result Set에 담겨있는 모든 데이터를 하나씩 뽑아서 vo객체에 담기
	 * 6-2) 트랜젝션 처리 (성공적이면 commit, 실패면 rollback)
	 * 
	 * 7) 다 쓴 JDBC용 객체 반납 (close) => 생성된 역순으로 닫아주기
	 * 
	 * 8) 결과 반환 (Controller)
	 * 			> SELECT문일 경우 - 6-1) 만들어진 결과
	 * 			> DML일 경우 		 - int(처리된 행수)
	 * 
	 * 
	 * 
	 * ** Statement 특징 : 완성된 sql문을 실행할 수 있는 객체
	 */
	/**
	 * 사용자가 추가 요청시 입력했던 값들을 가지고 insert문 실행하는 메소드
	 * @param m 	사용자가 입력한 아이디~취미까지의 값들이 잔뜩 담겨있는 Member객체
	 * @return 
	 */
	public int insertMember(Member m) {// insert문 => 처리된 행 수 => 트랜젝션 처리
		
		// 필요한 변수들 먼저 세팅
		int result = 0;				// 처리된 결과(처리된 행수)를 담아줄 변수
		Connection conn = null;		// 접속된 DB의 연결정보를 담는 변수
		Statement stmt = null;		// sql문 실행 후 결과를 받기위한 변수 
		
		// 실행할 sql문(완성형태로 만들어둘것!!) => 끝에 세미콜론 있으면 안됨!
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXXX', 'XXX', 'X', XX, 'XXXX', 'XXXX', 'XXXX', 'XXXX', SYSDATE );
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
										+ "'" + m.getUserId() + "', "
										+ "'" + m.getUserPwd() + "', "
										+ "'" + m.getUserName() + "', "
										+ "'" + m.getGender() + "', "
											  + m.getAge() + ", "
									    + "'" + m.getEmail() + "', "
									    + "'" + m.getPhone() + "', "
									    + "'" + m.getAddress() + "', "
									    + "'" + m.getHobby() + "', SYSDATE)";
		
		//System.out.println(sql);
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc6.jar 누락됐다거나, 잘 추가됐지만 오타가 있을경우 = ClassNotFoundException 발생 

			// 2) Connection 객체 생성 (DB와 연결 ==> url, 계정명, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) DB에 완성된 sql문 전달하면서 실행 후 결과(처리된 행수) 받기
			result = stmt.executeUpdate(sql);
			
			// 6_2) 트랜젝션 처리
			if(result > 0) { // 성공했을 경우
				conn.commit();
			}else { // 실패했을 경우
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC용 객체 자원 반납 => 생성된 역순으로 close
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 8) 결과 반환
		return result; // 처리된 행 수 
	}
	
	
	
	public ArrayList<Member> selectList() { // select문 => ResultSet객체(여러행)
		
		// 필요한 변수들 셋팅
		// 조회된 결과 뽑아서 담아둘 ArrayList생성(현재 텅 빈 리스트)
		ArrayList<Member> list = new ArrayList<>(); //조회된 회원들(여러회원) == 여러행
		
		Connection conn = null; // DB 연결정보를 담는 객체
		Statement stmt = null; // sql문 실행 및 결과받기
		ResultSet rset = null; // select문 실행된 조회결과값들이 처음에 실질적으로 담길 객체
		
		// 실행할 sql문 (완성된 형태로)
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
			// 1) jdbc.driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
	
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) sql문(select) 전달해서 실행 후 결과(ResultSet)받기
			rset = stmt.executeQuery(sql);
			
			// 6-1) 현재 조회된 결과가 담긴 ResultSet으로부터 한행씩 움직여가며 조회결과 뽑아서 Vo객체에 주섬주섬 담기
			while(rset.next()) { // 커서를 한 행 움직여주고 뿐만 아니라 해당 행이 존재할경우 true/ 없을때 false
				
				// 현재 rset의 커서가 가리키고있는 해당 행의 데이터를 하나씩 뽑아서 member객체에 주섬주섬 담기
				Member m = new Member();
				// rset으로부터 어떤 컬럼에 해당하는 값을 뽑을건지 제시해주면됨! (컬럼명 == 대소문자 가리지 않음)
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE")); 
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 한행에 대한 모든 데이터값들 하나의 Member객체에 옮겨담는 작업 끝!
				
				// 리스트에 해당 Member객체 차곡차곡 담아두기
				list.add(m);
				
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다쓴 JDBC용 객체 반납(생성된 역순으로)
				rset.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 8) 반납
		return list;
	}
	
	
	public Member selectByUserId(String userId) { // select문 => ResultSet객체(한행)
		
		// 필요한 변수들 셋팅
		// 조회된 한 회원에 대한 정보를 담을 변수
		Member m = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // 조회할 결과가 담길 
		
		// 실행할 sql문 (완성된 형태로)
		// SELECT * FROM MEMBER WHERE USERID = 'XXXX'
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
		try {
			// 1)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			//3)
			stmt = conn.createStatement();
			
			//4, 5)
			rset = stmt.executeQuery(sql);
			
			//6-1)
			if(rset.next()) {// 커서를 한행 움직여보고 조회결과가 있다면 true 없다면 false반환
			
				// 조회된 한 행에 대한 내용 담기
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("GENDER"),
							   rset.getInt("AGE"),
							   rset.getString("EMAIL"),
							   rset.getString("PHONE"),
							   rset.getString("ADDRESS"),
							   rset.getString("HOBBY"),
							   rset.getDate("Enrolldate") //-->대소문자 구분해주지않아도됨
						      );
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}
	
	
	public ArrayList<Member> selectByUserName(String keyword) { // select문 => ResultSet 객체(여러행)
		
		//필요한 변수 세팅
		
		// 여러행 조회될 수 있다 한다면 ArrayList
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// 실행할sql문(완성된 형태로)
		// SELECT * FROM MEMBER WHERE USERNAME LIKE '%XX%'
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + keyword + "%'";
		// SQL thin text error => 에러발생했을시 이 sql문에 오타가 있는 것
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				
				// 한 행에 데이터들을 => 한 Member객체에 담기 => list에 추가
				list.add(new Member(rset.getInt("USERNO"),
									rset.getString("USERID"),
									rset.getString("USERPWD"),
									rset.getString("USERNAME"),
									rset.getString("gender"),
									rset.getInt("age"),
									rset.getString("Email"),
									rset.getString("PHONE"),
									rset.getString("ADDRESS"),
									rset.getString("hobby"),
									rset.getDate("enrolldate")
								   )
						);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
		
		
	}
	
	public int updateMember(Member m) { // update 문 => 처리된 행수(int) => 트렌젝션처리
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문
		/*
		 UPDATE MEMBER
		 SET USERPWD = 'XXX', 
		 	 EMAIL = 'XXX', 
		 	 PHONE = 'XXXX', 
		 	 ADDRESS = 'XXXX'
		 WHERE USERID = 'XXXX';
		 */
		String sql = "UPDATE MEMBER "
				   + "SET USERPWD = '" + m.getUserPwd() + "', "
				   + 	   "EMAIL = '" + m.getEmail()   + "', "
				   +	   "PHONE = '" + m.getPhone()   + "', "
				   +     "ADDRESS = '" + m.getAddress() + "' "
				   +"WHERE USERID = '" + m.getUserId()  + "'";
		
		try {
			// 1)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3)
			stmt = conn.createStatement();
			// 4, 5)
			result = stmt.executeUpdate(sql);
			// 6_2)
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7)
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 8) 
		return result;
		
		
	}
	
	
	public int deleteMember(Member m) {
		
		
		// 변수세팅
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문
		// DELETE FROM MEMBER WHERE USERID = 'XXXX';
		String sql = "DELETE FROM MEMBER WHERE USERID = '" + m.getUserId() + "'";
		
		try {
			// 1)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3)
			stmt = conn.createStatement();
			// 4, 5)
			result = stmt.executeUpdate(sql);
			// 6_2)
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7)
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 8) 
		return result;
		
	}
}
