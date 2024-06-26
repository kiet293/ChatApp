package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import client.Login;
import database.ConnectDatabase;

public class DAO {
	public static User login(String username, String password) {
		String query = "SELECT * FROM user WHERE username=? AND password=?";
		try(Connection conn = ConnectDatabase.getConnection()) {
			try(PreparedStatement psm = conn.prepareStatement(query)) {
				psm.setString(1, username);
				psm.setString(2, password);
				try(ResultSet result = psm.executeQuery()) {
					while (result.next()) {
						User user = new User( 
											result.getString(1),
											result.getString(2)
											);
						return user;
						
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static User register(String username, String password) {
		String query = "INSERT INTO user (username, password) VALUES(?,?)";
		try(Connection conn = ConnectDatabase.getConnection()) {
			try(PreparedStatement psm = conn.prepareStatement(query)) {
				psm.setString(1, username);
				psm.setString(2, password);
				psm.execute();
				return new User(username, password);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(register("user", "user"));
		
	}
}