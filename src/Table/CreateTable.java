package Table;

import Database.JDBCUtil;
import Database.JDBUtil1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateTable extends JFrame implements ActionListener{
	
	JMenuBar menuBar;
	JMenu manage;
	JMenu accountMenu;
	JMenuItem accountItem1;
	JMenuItem accountItem2;
	JMenuItem studyItem;
	
	JPanel panel = new JPanel();
	
	JButton btn1 = new JButton("Reset");
	JButton btn2 = new JButton("Add");
	JButton btn3 = new JButton("View");
	JButton btn4 = new JButton("DropAll");
	JButton btn5 = new JButton("Find");
	
	JLabel ID_lbl = new JLabel("ID");
	JLabel Name_lbl = new JLabel("Name");
	JLabel Class_lbl = new JLabel("Class");
	JLabel DateOfBirth_lbl = new JLabel("DateOfBirth");
	JLabel Address_lbl = new JLabel("Address");
	
	JTextField ID_txt = new JTextField("");
	JTextField Name_txt = new JTextField("");
	JTextField Class_txt = new JTextField("");
	JTextField DateOfBirth_txt = new JTextField("");
	JTextField Address_txt = new JTextField("");
	
	JTextField findID = new JTextField("");
	
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	JScrollPane scrollPane = new JScrollPane(table);
	
	 String insertSql;
     String selectSql;
     String findSql;
     String deleteSql;
     
     String insertGPA;
     String insertUser;
	Connection con;
	Connection con1;
    CreateTable() throws SQLException{
    	try {
            con = JDBUtil1.getConnection();
            con1 = JDBCUtil.getConnection();
            insertSql = "INSERT INTO newstudent.sinhvien VALUES (?, ?, ?, ?, ?)";
            selectSql = "SELECT * FROM newstudent.sinhvien";
            findSql = "SELECT * FROM newstudent.sinhvien where MASV = ?";
            deleteSql = "DELETE FROM newstudent.sinhvien where MASV = ?";
            insertGPA = "INSERT INTO newstudent.gpa values (?,?,?,0,0,0,0,0)";
            insertUser = "INSERT INTO accountmanage.accinfor values(?,?)";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

		con.setAutoCommit(false);
    	
    	this.setTitle("Student Managememt");
    	this.setSize(900,500);
    	
    	menuBar = new JMenuBar();
    	manage = new JMenu("Manage");
    	
		accountMenu = new JMenu("Account");
		accountItem1 = new JMenuItem("ChangePassword");
		accountItem2 = new JMenuItem("LogOut");

    	studyItem = new JMenuItem("Result");
    	studyItem.addActionListener(this);
    	accountItem1.addActionListener(this);
    	accountItem2.addActionListener(this);
    	
    	accountMenu.setMnemonic(KeyEvent.VK_A);
		accountItem1.setMnemonic(KeyEvent.VK_C);
		accountItem2.setMnemonic(KeyEvent.VK_L);
    	manage.setMnemonic(KeyEvent.VK_M);
    	studyItem.setMnemonic(KeyEvent.VK_R);
    	
    	panel.setBackground(new Color(25,246,254));
    	panel.setBounds(0, 0, 320, 470);
    	
    	btn1.setBounds(30, 300, 80, 30);
    	btn2.setBounds(160, 300, 80, 30);
    	btn3.setBounds(30, 350, 80, 30);
    	btn4.setBounds(160, 350, 80, 30);
    	btn5.setBounds(30, 400, 80, 30);
    	
    	
    	btn1.addActionListener(this);
    	btn2.addActionListener(this);
    	btn3.addActionListener(this);
    	btn4.addActionListener(this);
    	btn5.addActionListener(this);
    	
    	ID_lbl.setBounds(30,50,80,30);
    	Name_lbl.setBounds(30, 100, 80, 30);
    	Class_lbl.setBounds(30, 150, 80, 30);
    	DateOfBirth_lbl.setBounds(30, 200, 80, 30);
    	Address_lbl.setBounds(30, 250, 80, 30);
    	findID.setBounds(120, 400, 120, 30);
    	
    	ID_lbl.setFont(new Font("MV Boli",Font.PLAIN,14));
    	Name_lbl.setFont(new Font("MV Boli",Font.PLAIN,14));
    	Class_lbl.setFont(new Font("MV Boli",Font.PLAIN,14));
    	DateOfBirth_lbl.setFont(new Font("MV Boli",Font.PLAIN,14));
    	Address_lbl.setFont(new Font("MV Boli",Font.PLAIN,14));
    	
    	ID_txt.setBounds(130, 50, 150, 30);
    	Name_txt.setBounds(130, 100, 150, 30);
    	Class_txt.setBounds(130, 150, 150, 30);
    	DateOfBirth_txt.setBounds(130, 200, 150, 30);
    	Address_txt.setBounds(130, 250, 150, 30);
    	
    	
    	model.addColumn("ID");
    	model.addColumn("Name");
    	model.addColumn("Class");
    	model.addColumn("DateOfBirth");
    	model.addColumn("Address");
    	
    	
    	scrollPane.setBounds(320, 0,560,500);
    	
    	panel.add(btn1);
    	panel.add(btn2);
    	panel.add(btn3);
    	panel.add(btn4);
    	panel.add(btn5);
    	panel.add(findID);
    	panel.add(ID_lbl);
    	panel.add(ID_txt);
    	panel.add(Name_lbl);
    	panel.add(Name_txt);
    	panel.add(Class_lbl);
    	panel.add(Class_txt);
    	panel.add(DateOfBirth_lbl);
    	panel.add(DateOfBirth_txt);
    	panel.add(Address_lbl);
    	panel.add(Address_txt);
    	panel.setLayout(null);
    	menuBar.add(manage);
		menuBar.add(accountMenu);
		accountMenu.add(accountItem1);
		accountMenu.add(accountItem2);
    	manage.add(studyItem);
    	this.setJMenuBar(menuBar);
    	this.add(panel);
    	this.add(scrollPane);
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLayout(null);
    	this.setLocationRelativeTo(null);
    	this.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn1) {
			clearField();
		}
		if(e.getSource() == btn2) {
			addData();
		}
		if(e.getSource() == btn3) {
			viewData();
		}
		if(e.getSource() == btn4) {
			deleteData();
		}
		if(e.getSource() == btn5) {
			findStudent();
		}
		if(e.getSource() == studyItem) {
			new Student();
			this.setVisible(false);
		}
		if(e.getSource() == accountItem1) {
			new ChagePassword();
		}
		if(e.getSource() == accountItem2) {
			this.setVisible(false);
			try {
				new LogIn();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
	
	public void clearField() {
		ID_txt.setText("");
		Name_txt.setText("");
		Class_txt.setText("");
		DateOfBirth_txt.setText("");
		Address_txt.setText("");
	}
	
	 public static boolean isDate(String dateString, String dateFormat) {
	        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	        sdf.setLenient(false); // Tắt chế độ linh hoạt

	        try {
	            sdf.parse(dateString);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	    }
	
	public void addData() {
		String id = ID_txt.getText();
		String name = Name_txt.getText();
		String clas = Class_txt.getText();
		String DOB = DateOfBirth_txt.getText();
		String address = Address_txt.getText();
		
		String dateFormat = "yyyy-MM-dd";
		
		if(isDate(DOB,dateFormat)) {
		try {
			PreparedStatement ptm = con.prepareStatement(insertSql);
			PreparedStatement ptm1 = con.prepareStatement(insertGPA);
			PreparedStatement ptm2 = con1.prepareStatement(insertUser);
			
			ptm2.setString(1, id);
			ptm2.setString(2, id);
			
			ptm1.setString(1,id);
			ptm1.setString(2, name);
			ptm1.setString(3, clas);
			
			ptm.setString(1, id);
			ptm.setString(2, name);
			ptm.setString(3, clas);
			ptm.setString(4, DOB);
			ptm.setString(5, address);
			
			ptm.executeUpdate();
			ptm1.executeUpdate();
			ptm2.executeUpdate();

            con.commit();
            
            model.addRow(new Object[]{id, name, clas, DOB, address});
            
            clearField();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
		}
	}
		else {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập không hợp lệ!","Thông báo!!",JOptionPane.WARNING_MESSAGE);
			clearField();
		}
	}
	
	public void viewData() {
		deleteData();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(selectSql);
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString("MASV"),rs.getString("HOTEN"),rs.getString("LOP"),rs.getString("NGAYSINH"),rs.getString("DIACHI")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteData() {
		int rowCount = model.getRowCount();
		for(int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
	
	public void findStudent(){
		deleteData();
		String id_finding = findID.getText();
		boolean check = true;
		for(char x: id_finding.toCharArray()) {
			if(!Character.isDigit(x)) {
				check = false;
			}
		}
			try {
				PreparedStatement ptm = con.prepareStatement(findSql);
				ptm.setString(1, id_finding);
				ResultSet rs = ptm.executeQuery();
				while(rs.next()) {
				model.addRow(new Object[] {rs.getString("MASV"),rs.getString("HOTEN"),rs.getString("LOP"),rs.getString("NGAYSINH"),rs.getString("DIACHI")});
				}
				clearField();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		if(check == false) {
			String[] options = {"Ừ!tao là chó!","Con chó mô bằng tao!","Kệ tao!"};
			JOptionPane.showOptionDialog(null, "Nhập ngu như chó!!", "Thông báo cho con chó", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, null, options, 0);
		}
		if(model.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả","Thông báo!",JOptionPane.WARNING_MESSAGE);
		}
	}
}
