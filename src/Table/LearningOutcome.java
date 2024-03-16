package Table;
import Database.JDBUtil1;

import java.awt.Font;
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

public class LearningOutcome extends JFrame implements ActionListener{
	Connection con;
	String selectGPA;
	
	JMenuBar menuBar;
	JMenu manage;
	JMenu account;
	JMenuItem accountItem1;
	JMenuItem accountItem2;
	JMenuItem inforItem;
	
	JLabel name_lbl = new JLabel();
	JLabel lbl = new JLabel("YOUR LEARNING RESULT");
	
	JLabel oop_lbl = new JLabel("OOP");
	JLabel pttkgt_lbl = new JLabel("PTTKGT");
	JLabel gt2_lbl = new JLabel("GT2");
	JLabel pbl2_lbl = new JLabel("PBL2");
	JLabel tthcm_lbl = new JLabel("TTHCM");
	
	JTextField oop_txt = new JTextField();
	JTextField pttkgt_txt = new JTextField();
	JTextField gt2_txt = new JTextField();
	JTextField pbl2_txt = new JTextField();
	JTextField tthcm_txt = new JTextField();
	
	
	private String idStudent;
	LearningOutcome(String id) throws SQLException{
		this.idStudent = id;
		try {
			con = JDBUtil1.getConnection();
			selectGPA = "SELECT * from newstudent.gpa where MASV = ?";
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		String oopTemp = "";
		String pttkgtTemp = "";
		String gt2Temp = "";
		String pbl2Temp = "";
		String tthcmTemp = "";
		String nameTemp = "";
		String classTemp = "";
		
		PreparedStatement ptm = con.prepareStatement(selectGPA);
		ptm.setString(1, idStudent);
		ResultSet rs = ptm.executeQuery();
		while(rs.next()) {
			oopTemp = rs.getString("OOP");
			pttkgtTemp = rs.getString("PTTKGT");
			gt2Temp = rs.getString("GT2");
			pbl2Temp = rs.getString("PBL2");
			tthcmTemp = rs.getString("TTHCM");
			nameTemp = rs.getString("HOTEN");
		}
		menuBar = new JMenuBar();
		manage = new JMenu("Manage");
		account = new JMenu("Account");
		accountItem1 = new JMenuItem("ChangePassword");
		accountItem2 = new JMenuItem("LogOut");
		inforItem = new JMenuItem("Information");
		
		manage.setMnemonic(KeyEvent.VK_M);
		account.setMnemonic(KeyEvent.VK_A);
		accountItem1.setMnemonic(KeyEvent.VK_C);
		accountItem2.setMnemonic(KeyEvent.VK_L);
		inforItem.setMnemonic(KeyEvent.VK_I);
		
		accountItem1.addActionListener(this);
		accountItem2.addActionListener(this);
		inforItem.addActionListener(this);
		
		oop_lbl.setBounds(58, 120, 50, 30);
    	pttkgt_lbl.setBounds(150, 120, 50, 30);
    	gt2_lbl.setBounds(258, 120, 50, 30);
    	pbl2_lbl.setBounds(357, 120, 50, 30);
    	tthcm_lbl.setBounds(450, 120, 50, 30);
    	
    	name_lbl.setBounds(30, 30, 200, 30);
    	lbl.setBounds(200, 70, 200, 50);
    	oop_txt.setBounds(50, 160, 50, 30);
    	pttkgt_txt.setBounds(150, 160, 50, 30);
    	gt2_txt.setBounds(250, 160, 50, 30);
    	pbl2_txt.setBounds(350, 160, 50, 30);
    	tthcm_txt.setBounds(450, 160, 50, 30);
    	
    	oop_txt.setEditable(false);
    	pttkgt_txt.setEditable(false);
    	gt2_txt.setEditable(false);
    	pbl2_txt.setEditable(false);
    	tthcm_txt.setEditable(false);

    	
    	name_lbl.setText("Welcome "+nameTemp);
    	oop_txt.setText(oopTemp);
    	pttkgt_txt.setText(pttkgtTemp);
    	gt2_txt.setText(gt2Temp);
    	pbl2_txt.setText(pbl2Temp);
    	tthcm_txt.setText(tthcmTemp);
    	
		
    	this.add(lbl);
    	this.add(name_lbl);
    	this.add(oop_lbl);
    	this.add(pttkgt_lbl);
    	this.add(gt2_lbl);
    	this.add(pbl2_lbl);
    	this.add(tthcm_lbl);
    	this.add(oop_txt);
    	this.add(pttkgt_txt);
    	this.add(gt2_txt);
    	this.add(pbl2_txt);
    	this.add(tthcm_txt);
    	menuBar.add(manage);
    	menuBar.add(account);
    	manage.add(inforItem);
    	account.add(accountItem1);
    	account.add(accountItem2);
    	this.setJMenuBar(menuBar);
		this.setSize(550,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == inforItem) {
			this.setVisible(false);
			try {
				new Information(this.idStudent);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
		if(e.getSource() == accountItem1) {
			new ChagePassword();
		}
		if(e.getSource() == accountItem2) {
			this.setVisible(false);
			try {
				new LogIn();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
		}
	}
}
