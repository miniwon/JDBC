package b_preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateEmp {

	public static void main(String[] args) {
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("1. 드라이버 로딩 성공");
			
			// 2. 연결 객체 얻어 오기
			String url = "jdbc:oracle:thin:@192.168.0.2:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("2. 디비 연결 성공");
			
			// 입력값
			int sabun = 7698;
			String saname = "아무개";
			int salary = 15000;
			
			// 7698 사원의 이름과 월급을 변경하세요
			String sql = " UPDATE emp SET ename = '" + saname + "', sal = " + salary + " WHERE empno = " + sabun ;
			
			// 4. SQL 전송 객체
			Statement stmt = con.createStatement();
			
			// 5. SQL 전송
			int result = stmt.executeUpdate(sql);
			System.out.println(result + "행을 실행");
			
			// 6. 닫기
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("DB 실패: " + e);
		}
		

	}

}
