package Table;

import Database.JDBUtil1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class Information extends JFrame implements ActionListener{
	JMenuBar menuBar;
	JMenu edit;
	JMenuItem back;
	
	JLabel lbl = new JLabel("YOUR PERSONAL INFORMATION");
	JLabel id_lbl = new JLabel("ID");
	JLabel name_lbl = new JLabel("Name");
	JLabel class_lbl = new JLabel("Class");
	JLabel dob_lbl = new JLabel("DateOfBirth");
	JLabel address_lbl = new JLabel("Address");
	
	JTextField id_txt = new JTextField();
	JTextField name_txt = new JTextField();
	JTextField class_txt = new JTextField();
	JTextField dob_txt = new JTextField();
	JTextField address_txt = new JTextField();
	
	String idTemp = "";
	String nameTemp = "";
	String classTemp = "";
	String dobTemp = "";
	String addressTemp = "";
	
	private String studentID;
	Connection con;
	String selectStudent;
	
	Information(String id) throws SQLException{
		this.studentID = id;
		
		try {
			con = JDBUtil1.getConnection();
			selectStudent = "SELECT * FROM newstudent.sinhvien where MASV = ?";
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		PreparedStatement ptm = con.prepareStatement(selectStudent);
		ptm.setString(1, this.studentID);
		ResultSet rs = ptm.executeQuery();
		while(rs.next()) {
			idTemp = rs.getString("MASV");
			nameTemp = rs.getString("HOTEN");
			classTemp = rs.getString("LOP");
			dobTemp = rs.getString("NGAYSINH");
			addressTemp = rs.getString("DIACHI");
		}
		
		menuBar = new JMenuBar();
		edit = new JMenu("Edit");
		back = new JMenuItem("Back");
		edit.setMnemonic(KeyEvent.VK_E);
		back.setMnemonic(KeyEvent.VK_B);
		back.addActionListener(this);
		
		lbl.setBounds(180, 30, 250, 50);
		id_lbl.setBounds(50, 65, 100, 100);
		name_lbl.setBounds(50, 150, 100, 30);
		class_lbl.setBounds(50, 200, 80, 30);
		dob_lbl.setBounds(50, 250, 100, 30);
		address_lbl.setBounds(50, 300, 100, 30);

		id_txt.setBounds(170, 100, 150, 30);
		name_txt.setBounds(170, 150, 150, 30);
		class_txt.setBounds(170, 200, 150, 30);
		dob_txt.setBounds(170, 250, 150, 30);
		address_txt.setBounds(170, 300, 150, 30);
		
		id_txt.setText(idTemp);
		name_txt.setText(nameTemp);
		class_txt.setText(classTemp);
		dob_txt.setText(dobTemp);
		address_txt.setText(addressTemp);
		
		id_txt.setEditable(false);
		name_txt.setEditable(false);
		class_txt.setEditable(false);
		dob_txt.setEditable(false);
		address_txt.setEditable(false);

		
		this.add(lbl);
		this.add(id_lbl);
		this.add(name_lbl);
		this.add(class_lbl);
		this.add(dob_lbl);
		this.add(address_lbl);
		this.add(id_txt);
		this.add(name_txt);
		this.add(class_txt);
		this.add(dob_txt);
		this.add(address_txt);
		menuBar.add(edit);
		edit.add(back);
		this.setJMenuBar(menuBar);
		this.setSize(500,450);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.setVisible(false);
			try {
				new LearningOutcome(idTemp);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
}
