package Table;
import Database.JDBUtil1;
import Table.Student;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Update extends JFrame implements ActionListener{
	Connection con;
	String updateGPA;
	String selectGPA;
	
	JLabel oop_lbl = new JLabel("OOP");
	JLabel pttkgt_lbl = new JLabel("PTTKGT");
	JLabel gt2_lbl = new JLabel("GT2");
	JLabel pbl2_lbl = new JLabel("PBL2");
	JLabel tthcm_lbl = new JLabel("TTHCM");
	
	JLabel id_lbl = new JLabel();
	JLabel name_lbl = new JLabel();
	
	JTextField oop_txt = new JTextField();
	JTextField pttkgt_txt = new JTextField();
	JTextField gt2_txt = new JTextField();
	JTextField pbl2_txt = new JTextField();
	JTextField tthcm_txt = new JTextField();
	
	JButton confirm_btn = new JButton("Confirm");
	
	private String idStudent;
    private String nameStudent;
    private String classStudent;
	Update(String id, String name, String className) throws SQLException{
		
		this.idStudent = id;
		this.nameStudent = name;
		this.classStudent = className;
		
		try {
			con = JDBUtil1.getConnection();
			updateGPA = "UPDATE newstudent.gpa set OOP = ?, PTTKGT = ?, GT2 = ?, PBL2 = ?, TTHCM = ? where MASV = ?";
			selectGPA = "SELECT * from newstudent.gpa where MASV = ?";
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		String oopTemp = "";
		String pttkgtTemp = "";
		String gt2Temp = "";
		String pbl2Temp = "";
		String tthcmTemp = "";
		
		PreparedStatement ptm1 = con.prepareStatement(selectGPA);
		ptm1.setString(1, idStudent);
		ResultSet rs = ptm1.executeQuery();
		while(rs.next()) {
			oopTemp = rs.getString("OOP");
			pttkgtTemp = rs.getString("PTTKGT");
			gt2Temp = rs.getString("GT2");
			pbl2Temp = rs.getString("PBL2");
			tthcmTemp = rs.getString("TTHCM");
		}
		
		con.setAutoCommit(false);

		
		this.setSize(550, 250);
		
		id_lbl.setBounds(30, 10, 80, 30);
		name_lbl.setBounds(102, 10, 150, 30);
		
		id_lbl.setText(id+" - ");
		name_lbl.setText(name);
		
		oop_lbl.setBounds(58, 60, 50, 30);
    	pttkgt_lbl.setBounds(150, 60, 50, 30);
    	gt2_lbl.setBounds(258, 60, 50, 30);
    	pbl2_lbl.setBounds(357, 60, 50, 30);
    	tthcm_lbl.setBounds(450, 60, 50, 30);
    	
    	oop_txt.setBounds(50, 100, 50, 30);
    	pttkgt_txt.setBounds(150, 100, 50, 30);
    	gt2_txt.setBounds(250, 100, 50, 30);
    	pbl2_txt.setBounds(350, 100, 50, 30);
    	tthcm_txt.setBounds(450, 100, 50, 30);
    	
    	oop_txt.setText(oopTemp);
    	pttkgt_txt.setText(pttkgtTemp);
    	gt2_txt.setText(gt2Temp);
    	pbl2_txt.setText(pbl2Temp);
    	tthcm_txt.setText(tthcmTemp);
    	
    	confirm_btn.setBounds(225, 160, 100, 30);
    	confirm_btn.addActionListener(this);
		
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
    	this.add(id_lbl);
    	this.add(name_lbl);
    	this.add(confirm_btn);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confirm_btn) {
			Confirm();
		}
	}
	public void Confirm() {
		String oopG = oop_txt.getText();
		String pttkgtG = pttkgt_txt.getText();
		String gt2G = gt2_txt.getText();
		String pbl2G = pbl2_txt.getText();
		String tthcmG = tthcm_txt.getText();
		
		System.out.println(pbl2G);
		if(isNumeric(oopG) && isNumeric(pttkgtG) && isNumeric(gt2G) && isNumeric(pbl2G) && isNumeric(tthcmG)) {
			double oopD = Double.parseDouble(oopG);
			double pttkgtD = Double.parseDouble(pttkgtG);
			double gt2D = Double.parseDouble(gt2G);
			double pbl2D = Double.parseDouble(pbl2G);
			double tthcmD = Double.parseDouble(tthcmG);
			if((oopD>=0.0&&oopD<=4.0)&&(pttkgtD>=0.0&&pttkgtD<=4.0)&&(gt2D>=0.0&&gt2D<=4.0)&&(pbl2D>=0.0&&pbl2D<=4.0)&&(tthcmD>=0.0&&tthcmD<=4.0)) {
			try {
			PreparedStatement ptm = con.prepareStatement(updateGPA);
			ptm.setDouble(1, Double.parseDouble(oopG));
			ptm.setDouble(2, Double.parseDouble(pttkgtG));
			ptm.setDouble(3, Double.parseDouble(gt2G));
			ptm.setDouble(4, Double.parseDouble(pbl2G));
			ptm.setDouble(5, Double.parseDouble(tthcmG));
			ptm.setString(6, idStudent);
			
			ptm.executeUpdate();
			con.commit();

			JOptionPane.showMessageDialog(null, "Cập nhật điểm thành công!","Thông báo!!",JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(e.getMessage());
			}
		}
		}
			else {
				JOptionPane.showMessageDialog(null, "Dữ liệu nhập không hợp lệ!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
				oop_txt.setText("");
				pttkgt_txt.setText("");
				gt2_txt.setText("");
				pbl2_txt.setText("");
				tthcm_txt.setText("");
			}
	}
		else {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập không hợp lệ!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
			oop_txt.setText("");
			pttkgt_txt.setText("");
			gt2_txt.setText("");
			pbl2_txt.setText("");
			tthcm_txt.setText("");
		}
		
}
	 public static boolean isNumeric(String str) {
	        if (str == null || str.length() == 0) {
	            return false;
	        }
	        try {
	            double d = Double.parseDouble(str);
	        } catch (NumberFormatException e) {
	            return false;
	        }
	        return true;
	    }
}
