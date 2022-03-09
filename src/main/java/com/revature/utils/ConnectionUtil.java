package com.revature.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton utility for creating and retrieving database connection
 */
public class ConnectionUtil {
	private static ConnectionUtil cu = null;
	private static Properties properties;
	//Modified
	private static Connection connection;
	private static String URL = "jdbc:mysql://localhost:3306/p0";//=null
	private static String CONNECTION_USERNAME = "root";
	private static String CONNECTION_PASSWORD="Revature888:):(:)";
	/**
	 * This method should read in the "database.properties" file and load
	 * the values into the Properties variable
	 */
	private ConnectionUtil() {
//		FileInputStream fileStream = new FileInputStream("pathtopropertiesfile"); 
		Properties properties = new Properties(); 
		try {
			FileInputStream fileStream = new FileInputStream("/src/main/resources/database.properties"); 
			
			properties.load(fileStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL = properties.getProperty("url");	
		CONNECTION_PASSWORD = properties.getProperty("pswd"); 
		CONNECTION_USERNAME = properties.getProperty("usr"); 
	}
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if(cu == null) {
			cu = new ConnectionUtil();
		}
		return cu;
	}
	
	/**
	 * This method should create and return a Connection object
	 * @return a Connection to the database
	 */
	public static Connection getConnection() {
		// Hint: use the Properties variable to setup your Connection object
		try {
			
			connection = DriverManager.getConnection(URL,CONNECTION_USERNAME,CONNECTION_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
