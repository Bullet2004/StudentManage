package Table;

import Database.JDBCUtil;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LogUp extends JFrame implements ActionListener{
	Connection con;

	JLabel lbl1 = new JLabel("Enter your username:");
	JLabel lbl2 = new JLabel("Enter your password:");
	JLabel lbl3 = new JLabel("Verify your password:");
	
	JTextField txtAcc = new JTextField();
	JPasswordField txtPass1 = new JPasswordField();
	JPasswordField txtPass2 = new JPasswordField();
	
	JButton create_btn = new JButton("Create");
	JButton back_btn = new JButton("Back");
	
	String insertUser;
	String selectUser;
	
	LogUp() throws SQLException{
		try {
            con = JDBCUtil.getConnection();
        	  insertUser = "INSERT INTO accountmanage.accinfor values(?,?)";
        	  selectUser = "SELECT username from accountmanage.accinfor where username = ?";
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		this.setSize(480,500);
		
		lbl1.setBounds(30, 50, 180, 30);
		lbl2.setBounds(30, 100, 180, 30);
		lbl3.setBounds(30, 150, 180, 30);
		
		txtAcc.setBounds(250, 50, 150, 30);
		txtPass1.setBounds(250, 100, 150, 30);
		txtPass2.setBounds(250, 150, 150, 30);

		lbl1.setFont(new Font("MV Boli",Font.PLAIN,16));
		lbl2.setFont(new Font("MV Boli",Font.PLAIN,16));
		lbl3.setFont(new Font("MV Boli",Font.PLAIN,16));
		
		create_btn.setBounds(150,250,100,30);
		back_btn.setBounds(150, 350, 100, 30);
		
		create_btn.addActionListener(this);
		back_btn.addActionListener(this);

		
		this.add(lbl1);
		this.add(lbl2);
		this.add(lbl3);
		this.add(txtAcc);
		this.add(txtPass1);
		this.add(txtPass2);
		this.add(create_btn);
		this.add(back_btn);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == create_btn) {
			Log_up();
		}
		if(e.getSource() == back_btn) {
			Back();
		}
	}
	public void Log_up() {
		String user_checking = txtAcc.getText();
		String pass1_checking = txtPass1.getText();
		String pass2_checking = txtPass2.getText();
		String userTemp = "";
		
		try {
			PreparedStatement ptm1 = con.prepareStatement(selectUser);
			ptm1.setString(1, user_checking);
			ResultSet rs1 = ptm1.executeQuery();
			while(rs1.next()) {
				userTemp = rs1.getString("username");
			}
			if(user_checking.equals("") || pass1_checking.equals("") || pass2_checking.equals("")) {
				JOptionPane.showMessageDialog(null, "Để trống cc", "Thông báo!",JOptionPane.WARNING_MESSAGE);
			}
			if(user_checking.equals(userTemp) &&user_checking.compareTo("")!=0) {
				JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại!!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
				txtAcc.setText("");
				txtPass1.setText("");
				txtPass2.setText("");
			}
			else {
				if(pass1_checking.equals(pass2_checking)) {
					PreparedStatement ptm2 = con.prepareStatement(insertUser);
					ptm2.setString(1, user_checking);
					ptm2.setString(2, pass1_checking);
					ptm2.executeUpdate();
					JOptionPane.showMessageDialog(null, "Tạo tài khoản thành công!",":>",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null,"Mật khẩu xác minh không chính xác!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
					txtPass1.setText("");
					txtPass2.setText("");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	public void Back() {
		this.setVisible(false);
		try {
			new LogIn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
