package Table;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Database.JDBCUtil;


public class LogIn extends JFrame implements ActionListener{

	Connection con;
	String selectUser;
	String insertUser;
	String updateUser;
		
	JButton btn1 = new JButton("Đăng nhập");
	JButton btn2 = new JButton("Đăng ký");
	
	JLabel Username = new JLabel("Username");
	JLabel Password= new JLabel("Password");
	
	JTextField txtAcc = new JTextField();
	JPasswordField txtPass = new JPasswordField();
	
	
	
	LogIn() throws SQLException{
		try {
            con = JDBCUtil.getConnection();
        	  insertUser = "INSERT INTO accountmanage.accinfor values(?,?)";
        	  selectUser = "SELECT * from accountmanage.accinfor where username = ?";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		this.setSize(420,500);
		
		Username.setBounds(30,50,80,30);
		Password.setBounds(30,100,80,30);
		
		Username.setFont(new Font("MV Boli",Font.PLAIN,18));
		Password.setFont(new Font("MV Boli",Font.PLAIN,18));
		
		txtAcc.setBounds(140, 50, 180, 30);
		txtPass.setBounds(140, 100, 180, 30);
		
		btn1.setBounds(150, 180, 100, 30);
		btn2.setBounds(150, 240, 100, 30);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		this.add(Username);
		this.add(Password);
		this.add(txtAcc);
		this.add(txtPass);
		this.add(btn1);
		this.add(btn2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn1) {
			LogIn();
		}
		if(e.getSource() == btn2) {
			try {
				this.setVisible(false);
				new LogUp();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
		}
	}
	public void LogIn() {
		String user_finding = txtAcc.getText();
		String pass_finding = txtPass.getText();
		String userCheck = "";
		String passCheck = "";
		try {
			PreparedStatement ptm1 = con.prepareStatement(selectUser);
			ptm1.setString(1, user_finding);
			ResultSet rs1 = ptm1.executeQuery();
			while(rs1.next()) {
				 userCheck = rs1.getString("username");
				 passCheck = rs1.getString("pass");
			}
			if(user_finding.equals(userCheck)&&pass_finding.equals(passCheck)&&user_finding.compareTo("")!=0) {
				if(userCheck.equals("buithecuong2004") == true) {
				this.setVisible(false);
				new CreateTable();
				}
				else {
					this.setVisible(false);
					new LearningOutcome(user_finding);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác!","Thông báo!",JOptionPane.WARNING_MESSAGE);
				txtAcc.setText("");
				txtPass.setText("");
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}
}
