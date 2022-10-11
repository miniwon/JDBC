package c_info2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoModelImpl implements InfoModel {

	final static String DRIVER	= "oracle.jdbc.driver.OracleDriver";
	final static String URL		= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER	= "scott";
	final static String PASS	= "tiger";

	public InfoModelImpl () throws ClassNotFoundException {
		// 1. 드라이버 로딩
		Class.forName(DRIVER);
		System.out.println("1. 드라이버 로딩 성공");	
	}

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */
	@Override
	public void insertInfo(InfoVO vo) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
		con = DriverManager.getConnection(URL, USER, PASS);
			
		// 3. SQL 문장
		String sql = "INSERT INTO info_tab(name, jumin, tel, gender, age, home)  "
				+ " VALUES(?, ?, ?, ?, ?, ?) ";
		
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		// ? 세팅 - #
		ps.setString(1, vo.getName());
		ps.setString(2, vo.getId());
		ps.setString(3, vo.getTel());
		ps.setString(4, vo.getGender());
		ps.setInt(5, vo.getAge());
		ps.setString(6, vo.getHome());
		
		
		// 5. 전송
		ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}
	
	/*
	 * Info_tab의 전체 레코드 검색
	 */
	@Override
	public ArrayList<InfoVO> selectAll() throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		con = DriverManager.getConnection(URL, USER, PASS);
			
		// 3. SQL 문장
		String sql = "SELECT * FROM info_tab";
		
		// 4. 전송 객체 얻어 오기
		ps = con.prepareStatement(sql);
		
		// 5. 전송
		rs = ps.executeQuery();
		ArrayList<InfoVO> list = new ArrayList<InfoVO>();
		while(rs.next()) {
			InfoVO vo = new InfoVO();
			vo.setName(rs.getString("NAME"));	// 실제 컬럼명 입력
			vo.setId(rs.getString("JUMIN"));
			vo.setTel(rs.getString("TEL"));
			vo.setGender(rs.getString("GENDER"));
			vo.setAge(rs.getInt("AGE"));
			vo.setHome(rs.getString("HOME"));
			
			list.add(vo);
		}
		
		return list;

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

	}
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 검색
	 */
	public InfoVO selectByTel (String tel) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		InfoVO vo = new InfoVO();
		
		try {
		con = DriverManager.getConnection(URL, USER, PASS);
			
		// 3. SQL 문장
		String sql = "SELECT * FROM info_tab WHERE TEL = ? ";
		
		// 4. 전송 객체 얻어 오기
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		
		// 5. 전송
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			vo.setName(rs.getString("NAME"));
			vo.setId(rs.getString("JUMIN"));
			vo.setTel(rs.getString("TEL"));
			vo.setGender(rs.getString("GENDER"));
			vo.setAge(rs.getInt("AGE"));
			vo.setHome(rs.getString("HOME"));
			
		} return vo;
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 삭제
	 * 메서드명	: deleteByTel
	 * 인자		: 전화번호
	 * 리턴값		: 삭제한 행 수
	 * 역할		: 전화번호를 넘겨받아 해당 전화번호의 레코드를 삭제
	 */
	public int deleteByTel (String tel) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		InfoVO vo = new InfoVO();
		int count;
//		int before = 0;
//		int after = 0;
		
		try {
		con = DriverManager.getConnection(URL, USER, PASS);
			
		// 3. SQL 문장
//		String sqlBefore = " SELECT COUNT(*) FROM info_tab ";
		String sql = " DELETE FROM info_tab WHERE TEL = ? ";
//		String sqlAfter = " SELECT COUNT(*) FROM info_tab ";
		
		// 4. 전송 객체 얻어 오기
//		ps = con.prepareStatement(sqlBefore);
//		ResultSet rs = ps.executeQuery();
//		if(rs.next()) {
//		before = rs.getInt(1);
//		}
//		
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		count = ps.executeUpdate();
		
//		ps = con.prepareStatement(sqlAfter);
//		rs = ps.executeQuery();
//		if(rs.next()) {
//		after = rs.getInt(1);
//		}
		// 5. 전송
		
		return count;
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}
	
	public void updateByTel(InfoVO vo) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
		con = DriverManager.getConnection(URL, USER, PASS);
			
		// 3. SQL 문장
		String sql = " UPDATE INFO_TAB "
				+ " SET NAME = ?, JUMIN = ?, TEL = ?, GENDER = ?, AGE = ?, HOME = ? "
				+ " WHERE TEL = ? ";
		
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		// ? 세팅 - #
		ps.setString(1, vo.getName());
		ps.setString(2, vo.getId());
		ps.setString(3, vo.getTel());
		ps.setString(4, vo.getGender());
		ps.setInt(5, vo.getAge());
		ps.setString(6, vo.getHome());
		ps.setString(7, vo.getTel());
		
		// 5. 전송
		ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}
}