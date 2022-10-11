package b_preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertEmp {

	public static void main(String[] args) {
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공");
			
			// 2. 연결 객체 얻어 오기
			String url = "jdbc:oracle:thin:@192.168.0.2:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("디비 연결 성공2");
			
			// 입력값
			String bonmyeong = "본명";
			int wolgup = 10000;
			String jikup = "IT";
			
			
			//		10번 부서의 사원의 월급을 10% 인상
			String sql = " INSERT INTO emp ( empno, ename, sal, job ) VALUES ( 555, ?, ?, ?) ";
			//		월급이 10000 이상인 사원들 삭제
			//String sql = " DELETE FROM emp WHERE sal >= 10000 ";
			
			// 4. SQL 전송 객체
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, bonmyeong);
			stmt.setInt(2, wolgup);
			stmt.setString(3, jikup);
			
			// 5. SQL 전송
			/*
			 * 		- ResultSet executeQuery()	: SELECT
			 * 		- int extcuteUpdate()		: INSERT/DELETE/UPDATE
			 */
			int result = stmt.executeUpdate();		// 이미 SQL이 연결되어 있기 때문에 입력 X
			System.out.println(result + "행을 실행");
			
			// 6. 닫기
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("DB 실패: " + e);
		}

	}

}
