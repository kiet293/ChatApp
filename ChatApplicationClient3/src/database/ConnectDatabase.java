package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
	public static Connection getConnection() {
		Connection c = null;
		try {
			// Các thông số
			String url = "jdbc:mySQL://localhost:3306/chat";
			String user = "root";
			String password = "";
			// Tạo kết nối
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	public static void closeConnection(Connection c) {
		if(c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
