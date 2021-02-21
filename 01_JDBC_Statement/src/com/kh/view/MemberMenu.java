package com.kh.view;

import java.util.Scanner;

import com.kh.controller.MemberController;

// View : 사용자가 보게될 시각적인 요소 담당 ( 화면 )
// 사용자에게 보여질 출력 + 입력받기를 진행한다. 
// 출력문인 System.out.print와 입력 받는 scanner는 view에서만 사용

public class MemberMenu {
	
	// 전역으로 다 쓸 수 있도록 Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	
	// 전역으로 바로 MemberController 호출할 수 있게끔 객체 생성
	private MemberController mc = new MemberController();
	
	/**
	 * 사용자가 보게될 첫 화면 (메인화면)
	 */
	public void mainMenu() {
		
		// 프로그램이 일회성이지 않기 때문에 무한반복문이용 
		while(true) {
			
			System.out.println("\n== 회원 관리 프로그램 ==");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름으로 키워드 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			// 입력유도
			System.out.print("이용할 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: insertMember(); break;
			case 2:  break;
			case 3:  break;
			case 4:  break;
			case 5:  break;
			case 6:  break;
			case 0: System.out.println("\n이용해주셔서 감사합니다. 프로그램을 종료합니다."); return;
			default: System.out.println("\n번호를 잘못 입력했습니다. 다시 입력해주세요.");
			
			}
			
		}
		
		
	}
	
	/**
	 * 회원 추가용 화면
	 * 추가하고자하는 회원의 정보를 입력받아서 추가 요청
	 */
	public void insertMember() {
		
		System.out.println("\n===== 회원 추가 =====");
		
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별 (M/F) : ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(-빼고) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 공백없이 나열) : ");
		String hobby = sc.nextLine();
		
		// 회원추가요청 ! => Controller의 어떤 메소드 호출!
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, hobby);
	}
	
	
	
	
	
	
	
	// ------------------------------------------------------
	// 서비스 요청 처리 후 사용자가 보게 될 응답화면들

	/**
	 * 서비스 요청 성공시 보게될 화면
	 * @param message 성공메세지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n서비스 요청 성공 : " + message);
	}
	
	/**
	 * 서비스 요청 실패시 보게될 화면
	 * @param message 실패메세지
	 */
	public void displayFail(String message) {
		System.out.println("\n서비스 요청 실패 : " + message);
	}
	
	
	
	
	
}