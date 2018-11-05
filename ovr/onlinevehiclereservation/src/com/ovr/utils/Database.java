package com.ovr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class Database {

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/db_ovr";
	static String user = "root";
	static String pass = "";
	
	public static Connection connect(){
		
		Connection con = null;
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println("[DATABASE CONNECTION FAILED]: "+e);
		}
		
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.err.println("[DATABASE CONNECTION FAILED]: "+e);
		}
		
		return con;
		
	}
	
}
