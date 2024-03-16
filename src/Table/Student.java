package Table;

import Database.JDBCUtil;
import Database.JDBUtil1;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class Student extends JFrame implements ActionListener{
	Connection con;
	String selectGPA;
	String insertGPA;
	String selectAllGPA;
	
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	JScrollPane scrollPane = new JScrollPane(table);
	
	JPanel panel = new JPanel();
	
	JLabel find_lbl = new JLabel("Enter Student ID:");
	
	JLabel id_lbl = new JLabel("Student ID");
	JLabel name_lbl = new JLabel("Name");
	JLabel class_lbl = new JLabel("Class");
	
	JTextField find_txt = new JTextField();
	static JTextField id_txt = new JTextField();
	static JTextField name_txt = new JTextField();
	static JTextField class_txt = new JTextField();
	
	JButton find_btn = new JButton("Find");
	JButton update_btn = new JButton("Update");
	JButton view_btn = new JButton("View");
	JButton drop_btn = new JButton("DropAll");
	
	JMenuBar menuBar;
	JMenu manageMenu;
	JMenu accountMenu;
	JMenuItem informationItem;
	JMenuItem accountItem1;
	JMenuItem accountItem2;
	Student(){
		try {
            con = JDBCUtil.getConnection();
             selectGPA = "SELECT * FROM newstudent.gpa where MASV = ?";
             insertGPA = "INSERT INTO newstudent.gpa(OOP,PTTKGT,GT2,PBL2,TTHCM) values (?,?,?,?,?)";
             selectAllGPA = "SELECT * FROM newstudent.gpa";
        } catch (Exception e) {
            e.printStackTrace();
        }
		this.setSize(900,500);
		
		menuBar = new JMenuBar();
		
		manageMenu = new JMenu("Manage");
		accountMenu = new JMenu("Account");
		informationItem = new JMenuItem("Information");
		accountItem1 = new JMenuItem("ChangePassword");
		accountItem2 = new JMenuItem("LogOut");
		
		accountMenu.setMnemonic(KeyEvent.VK_A);
		accountItem1.setMnemonic(KeyEvent.VK_C);
		accountItem1.setMnemonic(KeyEvent.VK_L);
		manageMenu.setMnemonic(KeyEvent.VK_M);
		informationItem.setMnemonic(KeyEvent.VK_I);
		
		
		informationItem.addActionListener(this);
		accountItem1.addActionListener(this);
		accountItem2.addActionListener(this);
		
		panel.setBackground(new Color(25,246,254));
    	panel.setBounds(0, 0, 320, 470);
    	
    	find_lbl.setBounds(30,50,100,30);
    	
    	id_lbl.setBounds(30, 100, 80, 30);
    	name_lbl.setBounds(30, 150, 80, 30);
    	class_lbl.setBounds(30, 200, 80, 30);
    	
    	id_txt.setBounds(100, 100, 150, 30);
    	name_txt.setBounds(100, 150, 150, 30);
    	class_txt.setBounds(100, 200, 100, 30);
    	
    	find_txt.setBounds(140, 50, 80, 30);
    	find_btn.setBounds(230, 50, 80, 30);
    	update_btn.setBounds(30, 300, 100, 30);
    	view_btn.setBounds(180, 300, 100, 30);
    	drop_btn.setBounds(30, 350, 100, 30);
    	
    	model.addColumn("MASV");
    	model.addColumn("OOP");
    	model.addColumn("PTTKGT");
    	model.addColumn("GT2");
    	model.addColumn("PBL2");
    	model.addColumn("TTHCM");

    	scrollPane.setBounds(320, 0,560,500);


    	find_btn.addActionListener(this);
    	update_btn.addActionListener(this);
    	view_btn.addActionListener(this);
    	drop_btn.addActionListener(this);
    	
		
		panel.setLayout(null);
		panel.add(find_lbl);
		panel.add(id_lbl);
		panel.add(name_lbl);
		panel.add(class_lbl);
		panel.add(id_txt);
		panel.add(name_txt);
		panel.add(class_txt);
		panel.add(find_txt);
		panel.add(find_btn);
		panel.add(update_btn);
		panel.add(view_btn);
		panel.add(drop_btn);
		menuBar.add(manageMenu);
		menuBar.add(accountMenu);
		manageMenu.add(informationItem);
		accountMenu.add(accountItem1);
		accountMenu.add(accountItem2);
		this.setJMenuBar(menuBar);
    	this.add(panel);
    	this.add(scrollPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == find_btn) {
			findStudent();
		}
		if(e.getSource() == update_btn) {
			updateGPA();
		}
		if(e.getSource() == informationItem) {
			try {
				new CreateTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			this.setVisible(false);
		}
		if(e.getSource() == view_btn) {
			viewGPA();
		}
		if(e.getSource() == drop_btn) {
			dropAll();
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
	public void findStudent() {
		String MASV_txt = find_txt.getText();
		String MASV = "";
		String HOTEN = "";
		String LOP = "";
		try {
			PreparedStatement ptm = con.prepareStatement(selectGPA);
			ptm.setString(1, MASV_txt);
			ResultSet rs = ptm.executeQuery();
			while(rs.next()) {
				MASV = rs.getString("MASV");
				HOTEN = rs.getString("HOTEN");
				LOP = rs.getString("LOP");
				
			}
			id_txt.setText(MASV);
			name_txt.setText(HOTEN);
			class_txt.setText(LOP);
			
			id_txt.setEditable(false);
			name_txt.setEditable(false);
			class_txt.setEditable(false);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(MASV_txt.equals(MASV) == false) {
			JOptionPane.showMessageDialog(null, "ID không tồn tại!!","Thông báo",JOptionPane.WARNING_MESSAGE);
			find_txt.setText("");
		}
	}
	public void updateGPA() {
		try {
			new Update(id_txt.getText(), name_txt.getText(), class_txt.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	public void viewGPA() {
		dropAll();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(selectAllGPA);
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString("MASV"),rs.getString("OOP"),rs.getString("PTTKGT"),rs.getString("GT2"),rs.getString("PBL2"),rs.getString("TTHCM")});
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void dropAll() {
		int rowCount = model.getRowCount();
		for(int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
}
