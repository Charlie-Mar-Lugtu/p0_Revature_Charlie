package com.revature.dao;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.utils.ConnectionUtil;

public class TransactionDaoDB implements TransactionDao {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatment;
	private static ResultSet resultSet;
	
	public TransactionDaoDB() {
		connection = ConnectionUtil.getConnection();
	}
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		List<Transaction> ListOfTransaction = new ArrayList<Transaction>();
		String query = "select * from transaction";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setType((TransactionType) resultSet.getObject("transaction_type"));
				transaction.setSender((Account) resultSet.getObject("from_account"));
				if((TransactionType) resultSet.getObject("transaction_type") == TransactionType.TRANSFER) 
				{
					transaction.setRecipient((Account) resultSet.getObject("to_accountId"));
				}
				transaction.setTimestamp((LocalDateTime) resultSet.getObject("timestamp"));
				transaction.setAmount(resultSet.getDouble("amount"));
				ListOfTransaction.add(transaction);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ListOfTransaction;
	}

}
