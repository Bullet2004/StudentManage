package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	public static Connection getConnection() {
		Connection c = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			
			String url = "jdbc:mysql://localhost:3306/accountmanage?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "bulletsilver";
			
			c = DriverManager.getConnection(url,username,password);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return c;
	}
}
