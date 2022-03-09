package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */
public class UserDaoDB implements UserDao {
	
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	
	public UserDaoDB() {
		connection = ConnectionUtil.getConnection();
	}

	public User addUser(User user) {
		// TODO Auto-generated method stub
		String query = "insert into p0_user ( first_name, last_name, username, password, user_type) values (?,?,?,?,?)";
		int status = 0;
		try {
			statement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getPassword());
			//pstmt.setObject(5, UserType.CUSTOMER);
			preparedStatement.setObject(5, user.getUserType().toString());
			preparedStatement.executeUpdate();
			if(status > 0) {
				System.out.println("Registered Sucessfully!!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return user;
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		String query = "select * from p0_user where id=" + userId.intValue();
		User user = new User();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()) 
			{
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType((UserType) resultSet.getObject("user_type"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		String query = "select * from p0_user where username=? and password=?";
		User user = new User();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, pass);
			resultSet = preparedStatement.executeQuery(query);
			if(resultSet.next()) 
			{
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType((UserType) resultSet.getObject("user_type"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		String query = "select * from p0_user";
		List<User> ListOfUsers = new ArrayList<User>();
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				User u = new User();
				u.setId(resultSet.getInt("id"));
				u.setFirstName(resultSet.getString("first_name"));
				u.setLastName(resultSet.getString("last_name"));
				u.setUsername(resultSet.getString("username"));
				u.setUserType((UserType) resultSet.getObject("user_type"));
				ListOfUsers.add(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ListOfUsers;
	}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		String query = "update p0_user set first_name=?, last_name=?, username=?, password=?, user_type=?, where id = ?";
		try {
			statement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, u.getFirstName());
			preparedStatement.setString(2, u.getLastName());
			preparedStatement.setString(3, u.getUsername());
			preparedStatement.setString(4, u.getPassword());
			preparedStatement.setObject(5, UserType.CUSTOMER);
			preparedStatement.setInt(6, u.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		String query = "delete from p0_user where id =" + u.getId();
		boolean check = false;
		try {
			statement=connection.createStatement();
			check = statement.execute(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}

}
