package com.revature.driver;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
//import com.revature.Info;
//import com.revature.Info;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.AccountDaoFile;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;

/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {
	//Modified down
	public static void register(User user) {
		
	}
	//Modified up
	public static void main(String[] args) {
		// your code here...
		String fn = null;
		String ln = null;
		String un= null;
		String pw = null;
		System.out.println("Welcome to the Credit Union");
		//Collection<User> l = new ArrayList<User>();
		Scanner userInput = new Scanner(System.in);
		//Scanner userInput1 = new Scanner(System.in);
		//Scanner userInput2 = new Scanner(System.in);
		User patron = new User();
		//UserDao userData = new UserDaoFile();
		UserDao userData = new UserDaoDB();
		//AccountDao accountData = new AccountDaoFile();
		AccountDao accountData = new AccountDaoDB();
		UserService ServiceForUsers = new UserService(userData, null);
		AccountService serviceAccounts = new AccountService(accountData);
		int choice = 0;
		int id = 0;
		do {
			//System.out.println("Do you want to register or login?");
			System.out.println();
			System.out.println("Please Enter Your Choice [1-6]");
			System.out.println("1) Apply");
			System.out.println("2) Login");
			System.out.println("3) Display ");
			System.out.println("4) Delete Customer");
			System.out.println("5) Update Customer");
			System.out.println("6) Exit");
			choice = userInput.nextInt();
			switch(choice) {
			case 1:
				id = UserDaoFile.ListOfUsers.size();
				System.out.println("Enter first name: ");
				fn = userInput.next();
				System.out.println("Enter last name: ");
				ln = userInput.next();
				System.out.println("Enter username: ");
				un = userInput.next();
				System.out.println("Enter password: ");
				pw = userInput.next();
				
				patron = new User(id++, un, pw, fn, ln, UserType.CUSTOMER); 
 
//				ServiceForUsers.register(user);
				
				ServiceForUsers.register(patron);

			
				break;
				/****************************Logging in*************************************/
			case 2:
				System.out.println("Enter Username : ");
				un = userInput.next();
				System.out.println("Enter Password : ");
				pw = userInput.next();
				User userLogin = ServiceForUsers.login(un, pw);
				System.out.println("User Login : " + userLogin);
				if (userLogin != null) {
					System.out.println("User Successfully Logged in!");
					SessionCache.setCurrentUser(userLogin);

					int option = 0;
					int TypeOfAccount = 0;
					double initialBalance = 0;
					while (option != 6) {
					//do {
						System.out.println("1.Add New Account ");
						System.out.println("2.Deposit money");
						System.out.println("3.Withdraw money");
						System.out.println("4.Wire Transfer ");
						System.out.println("5.Approve/Reject Account ");
						System.out.println("6.Logout ");
						System.out.print("Enter your option [1-6]:");
						option = userInput.nextInt();
						switch (option) {
						case 1:
							System.out.println("Choose the Account Type [1 or 2]: ");
							System.out.println("1-Checking");
							System.out.println("2-Saving");
							TypeOfAccount = userInput.nextInt();
							System.out.print("Enter Starting balance:");
							initialBalance = userInput.nextDouble();
							Account account = new Account();
							account.setBalance(initialBalance);
							System.out.println("Logged user ID :" + SessionCache.getCurrentUser().get().getId());
							account.setOwnerId(userLogin.getId());
							account.setType(TypeOfAccount == 1 ? AccountType.CHECKING.toString() : AccountType.SAVINGS.toString());
							List<Account> ListOfAccounts = new ArrayList<Account>();
							ListOfAccounts.add(account);
							userLogin.setAccounts(ListOfAccounts);
							serviceAccounts.createNewAccount(userLogin);
							break;
						case 2:
							System.out.println("Here are the available accounts");
							serviceAccounts.getAccounts(userLogin).forEach(System.out::println);
							System.out.println("Enter Account ID to Deposit :");
							int accountId = 0 ;
							accountId = userInput.nextInt();
							System.out.println("Enter the amount to deposit :");
							double amount = 0;
							amount = userInput.nextDouble();
							account = accountData.getAccount(accountId);
							serviceAccounts.deposit(account, amount);
							break;
						case 3:

							break;
						case 4:

							break;
						case 5:
							break;
						case 6:
							
							System.out.println("Okay, your account and money is automatically saved.");
//							System.out.println("Do you want to Logout?");
//							System.out.println("1) Yes");
//							System.out.println("2) No");
//							int logout = 0;
//							logout = userInput.nextInt();
//					
//							if (logout == 1) {
//								SessionCache.setCurrentUser(null);
//								
//							}
							
							
							break;
						default:
							System.out.println("Enter a number between 1 to 6");
							break;
						}
						
					}//while (option <= 6);
				}
				

				break;
			case 3:
				System.out.println("********************Display Users******************************");
			
				userData.getAllUsers().forEach(System.out::println);
				
				System.out.println("***************************************************************");
				break;
			case 4:
				System.out.println("Enter Id to remove customer:");
				id = userInput.nextInt();
				User u = userData.getUser(id);
				userData.removeUser(u);
				break;
			case 5:
				System.out.println("Enter Id of the customer");
				id = userInput.nextInt();
				System.out.println("Update First Name:");
				fn = userInput.next();
				System.out.println("Update Last Name:");
				ln = userInput.next();
				System.out.print("Update your password: ");
				pw = userInput.next();
				User patronUpdate = new User();
				patronUpdate.setId(id);
				patronUpdate.setFirstName(fn);
				patronUpdate.setLastName(ln);
				patronUpdate.setPassword(pw);
				userData.updateUser(patronUpdate);
				break;
			case 6:
				System.out.println("Thank you, come again. Bye.");
				break;
			default:
				System.out.println("Choose 1 to 6 please.");
				break;
			}
			
		}while(choice != 6);
		
		userInput.close();
		
		
		
	}
		
		
}


