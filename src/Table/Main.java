package Table;
import Database.JDBCUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args){
		try {
			new LogIn();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
	}
}	
