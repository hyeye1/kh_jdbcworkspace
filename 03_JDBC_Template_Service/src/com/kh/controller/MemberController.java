package com.kh.controller;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;

public class MemberController {

	/**
	 * 사용자의 회원 추가 요청시 처리해주는 메소드
	 * @param userId userPwd userName gender age email phone address hobby
	 * 		   사용자가 회원추가요청시 입력한 값들
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
							 String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		new MemberService().insertMember(m);
		
		
		
		
		
		
	}
	
	public void selectList() {
		
	}

	public void selectByUserId(String userId) {
		
	}

	public void selectByUserName(String keyword) {
		
	}
	
	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
	}

	public void deleteMember(String userId) {
		
	}

}