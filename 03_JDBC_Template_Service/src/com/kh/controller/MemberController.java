package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	
	/**
	 * 사용자의 회원 추가 요청시 처리해주는 메소드
	 * @param userId userPwd userName gender age email phone address hobby
	 * 		   사용자가 회원추가요청시 입력한 값들
	 */
	public int insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		int result = new MemberService().insertMember(m);
		
		if(result>0) {
			new MemberMenu().displaySuccess("회원 추가 성공");
		}else {
			new MemberMenu().displayFail("회원 추가 실패");
		}
		
		return result;
	}

	public ArrayList<Member> selectList() {
		
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayFail("회원조회에 실패하였습니다.");
		} else {
			new MemberMenu().displayList(list);			
		}
		
		return list;
		
	}

	public void selectByUserId(String inputMemberId) {
		// TODO Auto-generated method stub
		
	}

	public void selectByUserName(String inputMemberName) {
		// TODO Auto-generated method stub
		
	}

	public void deleteMember(String inputMemberId) {
		// TODO Auto-generated method stub
		
	}

	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		// TODO Auto-generated method stub
		
	}

}