package c_info2_번외1_전화번호대신이름으로삭제;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InfoModel {

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */
	void insertInfo(InfoVO vo) throws SQLException;

	/*
	 * Info_tab의 전체 레코드 검색
	 */
	ArrayList<InfoVO> selectAll() throws SQLException;
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 검색
	 */
	InfoVO selectByTel(String tel) throws SQLException;
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 삭제
	 */
	int deleteByName (String name) throws SQLException;
}