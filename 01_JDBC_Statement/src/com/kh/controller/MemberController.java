package com.kh.controller;
// Controller : View를 통해서 요청한 기능을 처리하는 담당
// view의 메소드를 호출한다, 해당 메소드로 전달된 데이터를 가공처리 한 후 Dao 메소드 호출시 전달
// Dao로부터 반환받은 결과에 따라 사용자가 보게 될 view(응답화면)을 결정(view 메소드호출)

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	
	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId, userPwd, userName, gender, age, email, phone, address, hobby
	 * => 사용자가 요청한 값
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, 
							 int age, String email, String phone, String address, String hobby) {
	
		// 전달된 데이터들을 member객체 주섬주섬 담기
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) { // 성공했을 경우
			new MemberMenu().displaySuccess("회원 추가 성공");
			
		}else { // 실패했을 경우
			new MemberMenu().displayFail("회원 추가 실패 ㅠㅠ");
			
		}
		
	}	
	
	
	
	
	
	
	
	
	
	
}
