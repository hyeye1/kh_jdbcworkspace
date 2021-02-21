package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	
	
	
	
	
	
	
	
	
	
	
}
