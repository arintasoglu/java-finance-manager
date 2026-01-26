package io.github.arintasoglu.finance_manager.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.arintasoglu.finance_manager.exception.DataAccessException;

public class DatabaseConnection {
	public static Connection provideConnection() throws DataAccessException {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/finance";
		try {
			con = DriverManager.getConnection(url, "root", "root");
		}
		catch (Exception e) {
			throw new DataAccessException("Database connection failed.");
		}
		return con;
	}

}