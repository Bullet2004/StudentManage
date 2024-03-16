package Table;

import Database.JDBCUtil;
import java.awt.Font;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChagePassword extends JFrame implements ActionListener{
	Connection con;
	String selectUser;
	String updateUser;
	
	JLabel lbl1 = new JLabel("Enter your username");
	JLabel lbl2 = new JLabel("Enter your old pasword");
	JLabel lbl3 = new JLabel("Enter your new password");
	JLabel lbl4 = new JLabel("Verify your new password");
	
	JTextField txt1 = new JTextField();
	JPasswordField txt2 = new JPasswordField();
	JPasswordField txt3 = new JPasswordField();
	JPasswordField txt4 = new JPasswordField();
	
	JButton confirm_btn = new JButton("Confirm");
	
	ChagePassword() {
		try {
			con = JDBCUtil.getConnection();
			selectUser = "SELECT * FROM accountmanage.accinfor where username = ?";
			updateUser = "UPDATE accountmanage.accinfor set pass = ? where username = ?";
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		this.setSize(480,500);
		
		lbl1.setBounds(30, 50, 200, 30);
		lbl2.setBounds(30, 100, 200, 30);
		lbl3.setBounds(30, 150, 200, 30);
		lbl4.setBounds(30, 200, 200, 30);
		
		lbl1.setFont(new Font("MV Boli",Font.PLAIN,16));
		lbl2.setFont(new Font("MV Boli",Font.PLAIN,16));
		lbl3.setFont(new Font("MV Boli",Font.PLAIN,16));
		lbl4.setFont(new Font("MV Boli",Font.PLAIN,16));
		
		txt1.setBounds(250, 50, 150, 30);
		txt2.setBounds(250, 100, 150, 30);
		txt3.setBounds(250, 150, 150, 30);
		txt4.setBounds(250, 200, 150, 30);
		
		confirm_btn.setBounds(170, 270, 100, 30);
		confirm_btn.addActionListener(this);
		
		this.add(lbl1);
		this.add(lbl2);
		this.add(lbl3);
		this.add(lbl4);
		this.add(txt1);
		this.add(txt2);
		this.add(txt3);
		this.add(txt4);
		this.add(confirm_btn);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confirm_btn) {
			ChangePass();
		}
	}
	public void ChangePass() {
		String user_finding = txt1.getText();
		String pass_finding = txt2.getText();
		String newPass = txt3.getText();
		String verPass = txt4.getText();
		String user_check = "";
		String pass_check = "";
		try {
			PreparedStatement ptm1 = con.prepareStatement(selectUser);
			PreparedStatement ptm2 = con.prepareStatement(updateUser);
			ptm1.setString(1, user_finding);
			ResultSet rs = ptm1.executeQuery();
			while(rs.next()) {
				user_check = rs.getString("username");
				pass_check = rs.getString("pass");
			}
			if(user_finding.equals(user_check) == true) {
				if(pass_finding.equals(pass_check) == true) {
					if(newPass.equals(verPass) == true) {
						ptm2.setString(1, newPass);
						ptm2.setString(2, user_check);
						ptm2.executeUpdate();
						JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!","Thông báo!!",JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Mật khẩu xác minh không chính xác!", "Thông báo!!",JOptionPane.WARNING_MESSAGE);
						txt3.setText("");
						txt4.setText("");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Mật khẩu cũ không chính xác!","Thông báo",JOptionPane.WARNING_MESSAGE);
					txt2.setText("");
					txt3.setText("");
					txt4.setText("");
					}
			}
			else {
				JOptionPane.showMessageDialog(null, "Tài khoản không chính xác!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
