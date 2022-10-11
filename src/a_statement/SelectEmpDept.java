package a_statement;

import java.sql.*;

public class SelectEmpDept {

	public static void main(String[] args) {
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공");
			
			// 2. 연결 객체 얻어 오기
			String url = "jdbc:oracle:thin:@192.168.0.59:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("디비 연결 성공2");
			
			// 3. SQL 문장 (★★★★★)
			//		-> 20번 부서의 사원들의 정보 - 사번, 사원명, 월급, 부서명, 근무지
			String sql = " SELECT e.empno A1, e.ename A2, e.sal A3, d.dname A4, d.loc A5 FROM emp e INNER JOIN dept d ON e.deptno = d.deptno WHERE e.deptno = 20 ";
			System.out.println("SQL 이상 없음");
			// 4. 전송 객체 얻어 오기
			Statement stmt = con.createStatement();
			
			// 5. SQL 전송
			/*
			 * 		- ResultSet executeQuery()	: SELECT
			 * 		- int extcuteUpdate()		: INSERT/DELETE/UPDATE
			 */
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int empno 		= rs.getInt("A1");
				String ename 	= rs.getString("A2");
				int sal			= rs.getInt("A3");
				String dname	= rs.getString("A4");
				String loc		= rs.getString("A5");	
				System.out.println(empno + "/" + ename + "/" + sal + "/" + dname + "/" + loc);	
			}
			
			// 6. 닫기
			rs.close();
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("실패: " + e);
		}

	}

}
