package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	/**
	 * 사용자의 회원 추가 요청시 처리해주는 메소드
	 * @param userId, userPwd, userName, gender, age, email, phone, address, hobby
	 * 		    사용자가 회원추가요청시 입력한 값들
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		int result = new MemberService().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("회원 추가 성공!");
		}else {
			new MemberMenu().displayFail("회원 추가 실패!");
		}
		
	}

	public void selectList() {
		
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) { // 조회x
			new MemberMenu().displayNoData("전체 조회 결과 없습니다.");
		}else { // 조회o
			new MemberMenu().displayList(list);
		}
		
	}

	public void selectByUserId(String userId) {
		Member m = new MemberService().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData("\n" + userId + "에 해당하는 검색결과가 없습니다.");
		}else {
			new MemberMenu().displayOne(m);
		}
	}

	public void selectByUserName(String keyword) {
		ArrayList<Member> list = new MemberService().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("\n" + keyword + "에 해당하는 검색 결과가 없습니다.");
		}else {
			new MemberMenu().displayList(list);
		}
	} 

	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address);
		
		int result = new MemberService().updateMember(m);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원 정보 변경 성공!");
		}else {
			new MemberMenu().displayFail("회원 정보 변경 실패!");
		}
	}

	public void deleteMember(String userId) {
		
		int result = new MemberService().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("회원 탈퇴 성공!!");
		}else {
			new MemberMenu().displayFail("회원 탈퇴 실패!!");
		}
		
	}
	
	
	public void loginMember(String userId, String userPwd) {
		
		Member m = new MemberService().loginMember(userId, userPwd);
		
		if(m == null) { // 조회 x
			new MemberMenu().displayNoData("로그인 실패하셨습니다.");
		}else { // 조회됨
			new MemberMenu().displayOne(m);
		}
		
	}

	
}




