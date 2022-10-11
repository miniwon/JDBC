package c_info2_번외1_전화번호대신이름으로삭제;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class InfoView {

	// 1. 멤버변수 선언
	JFrame f;
//	JLabel lbName, lbID, lbTel, lbGender, lbAge, lbHome;
	JTextField tfName, tfID, tfTel, tfGender, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;
	
	// 비즈니스 로직 - 모델단
	InfoModel model;
	
	// 2. 멤버변수 객체 생성
	InfoView() {
		f = new JFrame("DBTest");
//		lbName = new JLabel("Name");
//		lbID = new JLabel("ID");
		tfName = new JTextField(20);
		tfID = new JTextField();
		tfTel = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfHome = new JTextField();
		ta = new JTextArea(50,50);
		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bExit = new JButton("Exit");
		
		// 모델 객체 생성
		try {
			model = new InfoModelImpl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	
		
	}
	
	// 3. 화면 구성하고 출력
	/*
	 * 전체 프레임 BorderLayout 지정
	 * 		- WEST		: JPanel 붙이기(GridLayout(6,2))
	 * 		- CENTER	: TextArea
	 * 		- SOUTH		: JPanel 붙이기(GridLayout(1,6))
	 */
	public void addLayout() {

		f.setLayout(new BorderLayout());
		
		JPanel pWest = new JPanel();
		pWest.setLayout(new GridLayout(6,2));
		pWest.add(new JLabel("Name", JLabel.CENTER));
		pWest.add(tfName);
		pWest.add(new JLabel("ID", JLabel.CENTER));
		pWest.add(tfID);
		pWest.add(new JLabel("Tel", JLabel.CENTER));
		pWest.add(tfTel);
		pWest.add(new JLabel("Gender", JLabel.CENTER));
		pWest.add(tfGender);
		pWest.add(new JLabel("Age", JLabel.CENTER));
		pWest.add(tfAge);
		pWest.add(new JLabel("Home", JLabel.CENTER));
		pWest.add(tfHome);
//		for (Component jc : pWest.getComponents()) {
//		    if ( jc instanceof JLabel ) {
//		        JLabel label = (JLabel) jc;
//		        label.setHorizontalAlignment(JLabel.CENTER);
//		    }
//		}
		
		f.add(pWest, BorderLayout.WEST);
		pWest.setPreferredSize(new Dimension(300,300));
		
		f.add(ta, BorderLayout.CENTER);
		
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new GridLayout(1,6));
		pSouth.add(bAdd);
		pSouth.add(bShow);
		pSouth.add(bSearch);
		pSouth.add(bDelete);
		pSouth.add(bCancel);
		pSouth.add(bExit);
		f.add(pSouth, BorderLayout.SOUTH);
		
		f.setBounds(100, 100, 800, 350);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	void eventProc () {
		// Add 버튼이 눌렸을 때
		bAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				insertData();
			}
		}); // .ActionListener()
		
		// Show 버튼이 눌렸을 때
		bShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		}); // .ActionListener()
		
		// Search 버튼이 눌렸을 때
		bSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		}); // .ActionListener()
		
		// 전화번호 텍스트 필드에서 엔터 쳤을 때
		tfTel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		}); // .ActionListener()
		
		// Delete 버튼이 눌렸을 때
		bDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteByTel();
			}
		}); // .ActionListener()
		
	} // eventProc()
	
	void insertData() {
		// (1) 사용자 입력값 얻어 오기
		String name		= tfName.getText();
		String id		= tfID.getText();
		String tel		= tfTel.getText();
		String gender	= tfGender.getText();
		int age			= Integer.parseInt(tfAge.getText());
		String home		= tfHome.getText();
		
		// (2) 1번의 값들을 InfoVO에 지정
		InfoVO vo = new InfoVO();
			// InfoVO vo = new InfoVO(name, id, tel, gender, age, home);
		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);
		
		
		// (3) 모델의 insertInfo() 호출
		try {
			model.insertInfo(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// (4) 화면의 입력값들을 지우기
		clearText();
	}
	
	void clearText() {
		tfName.setText(null);
		tfID.setText(null);
		tfTel.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfHome.setText(null);
		
	}
	
	void selectAll() {
		try {
			ArrayList<InfoVO> data = model.selectAll();
			ta.setText("---------- 검색 결과----------\n\n");
			for (InfoVO vo : data ) {
				ta.append(vo.toString());
			}
		} catch (SQLException e) {
			ta.setText("검색 실패: " + e.getMessage());
		}
	}

	void selectByTel() {
		try {
		// (1) 입력한 전화번호 값을 얻어 오기
		String tel = tfTel.getText();
			
		// (2) 모델단에 selectByTel() 호출
		InfoVO vo = model.selectByTel(tel);
		
		// (3) 받은 결과를 각각의 텍스트필드에 지정(출력)
		tfName.setText(vo.getName());
		tfID.setText(vo.getId());
		tfTel.setText(vo.getTel());
		tfGender.setText(vo.getGender());
		tfAge.setText(Integer.toString(vo.getAge()));
		tfHome.setText(vo.getHome());
		
		} catch (Exception e) {
			ta.setText("전화번호 검색 실패: " + e.getMessage());
		}
	} // selectByTel()
	
	void deleteByTel() {
		// (1) 입력한 전화번호 값을 얻어 오기
		String name = tfName.getText();
		
		// (2) 모델단에 deleteByName() 호출
		try {
			int result = model.deleteByName(name);
		
		// (3) 화면을 지우고
			clearText();
			ta.setText("삭제한 행 수: " + result);
		} catch (Exception e) {
			ta.setText("이름 삭제 실패: " + e.getMessage());
		}

	}
	
	public static void main(String[] args) {
		
		InfoView info = new InfoView();
		info.addLayout();
		info.eventProc();


	}
	

}
