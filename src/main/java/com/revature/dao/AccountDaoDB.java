package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	public AccountDaoDB() {//modified
		connection = ConnectionUtil.getConnection();
	}
	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
		String query = "insert into account (owner_id, balance, account_type)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, a.getOwnerId());
			preparedStatement.setDouble(2, a.getBalance());
			preparedStatement.setObject(3, a.getType().toString());
			int status = preparedStatement.executeUpdate();
			if (status > 0) {
				System.out.println("Account is already created, please wait for approval...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}

	public Account getAccount(Integer actId) {
		// TODO Auto-generated method stub
		String query = "select * from account where id=" + actId.intValue();
		Account a = new Account();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				a.setId(resultSet.getInt("id"));
				a.setOwnerId(resultSet.getInt("owner_id"));
				a.setBalance(resultSet.getDouble("balance"));
				//a.setType(resultSet.getString("account_type"));
				a.setApproved(resultSet.getBoolean("approved"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
		
	}

	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		String query = "select * from account";
		List<Account> ListOfAccounts = new ArrayList<Account>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Account a = new Account();
				a.setId(resultSet.getInt("id"));
				a.setOwnerId(resultSet.getInt("owner_id"));
				a.setBalance(resultSet.getDouble("balance"));
				//a.setType(resultSet.getString("account_type"));
				a.setApproved(resultSet.getBoolean("approved"));
				ListOfAccounts.add(a);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ListOfAccounts;
		
	}

	public List<Account> getAccountsByUser(User u) {
		// TODO Auto-generated method stub
		String query = "select * from account where owner_id=" + u.getId();
		List<Account> ListOfAccounts = new ArrayList<Account>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Account a = new Account();
				a.setId(resultSet.getInt("id"));
				a.setOwnerId(resultSet.getInt("owner_id"));
				a.setBalance(resultSet.getDouble("balance"));
				//a.setType(resultSet.getString("account_type"));
				a.setApproved(resultSet.getBoolean("approved"));
				ListOfAccounts.add(a);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ListOfAccounts;
	}

	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeAccount(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

}
