package run;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Run {

	public static void main(String[] args) {

		/*
		 
		 * Properties
		 - Map 계열의 컬렉션 (key+value 세트로 담는)
		 - Key값도 String, Value값도 String 인 특징
		 - Properties에 담겨있는 것들을 파일로 출력을 한다거나
		     파일에 있는 데이터들을 Properties 입력받아올 수 있는
		     즉, 파일과 입출력을 할 수 있는 메소드들 제공!
		 
		 */
		
		Properties prop1 = new Properties();
		
		
		try {
			prop1.load(new FileInputStream("test.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop1.getProperty("driver"));
		System.out.println(prop1.getProperty("url"));
		System.out.println(prop1.getProperty("username"));
		System.out.println(prop1.getProperty("password"));
		
		System.out.println("============================================");
		
		Properties prop2 = new Properties();
		
		try {
			prop2.loadFromXML(new FileInputStream("test.xml"));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop2.getProperty("selectList"));
		System.out.println(prop2.getProperty("updateMember"));
		System.out.println(prop2.getProperty("deleteMember"));
		   
	}

}
















