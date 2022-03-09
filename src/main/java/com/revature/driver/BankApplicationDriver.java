package com.revature.driver;

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
		Collection<User> l = new ArrayList<User>();
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
 
				

			
				break;
			case 2:
				System.out.println("Enter Username : ");
				un = userInput.next();
				System.out.println("Enter Password : ");
				pw = userInput.next();
				User userLogin = ServiceForUsers.login(un, pw);
				System.out.println("User Login : " + userLogin);
				if (userLogin != null) {
					System.out.println("Logged in Successfully!!!");
					SessionCache.setCurrentUser(userLogin);

					int option = 0;
					int accountType = 0;
					double startingBalance = 0;

					do {
						System.out.println("1.Add New Account ");
						System.out.println("2.Deposit");
						System.out.println("3.Withdraw ");
						System.out.println("4.Wire Transfer ");
						System.out.println("5.Approve/Reject Account ");
						System.out.println("6.Logout ");
						System.out.print("Enter your option [1-6]:");
						option = userInput.nextInt();
						switch (option) {
						case 1:
							System.out.print("select the Account Type [1.Checking/2.Saving]: ");
							accountType = userInput.nextInt();
							System.out.print("Enter Starting balance:");
							startingBalance = userInput.nextDouble();
							Account account = new Account();
							account.setBalance(startingBalance);
							System.out.println("Logged user ID :" + SessionCache.getCurrentUser().get().getId());
							account.setOwnerId(userLogin.getId());
						//	account.setType(accountType == 1 ? AccountType.CHECKING.toString()
						//			: AccountType.SAVINGS.toString());
							List<Account> accountList = new ArrayList<Account>();
							accountList.add(account);
							userLogin.setAccounts(accountList);
							serviceAccounts.createNewAccount(userLogin);
							break;
						case 2:
							System.out.println("Here are the available accounts");
							serviceAccounts.getAccounts(userLogin).forEach(System.out::println);
							System.out.print("Enter Account ID to Deposit :");
							int accountId = 0 ;
							accountId = userInput.nextInt();
							System.out.print("Enter the amount to deposit :");
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
							System.out.print("Do you want to Logout? (1.Yes/2.No) :");
							int logout = 0;
							logout = userInput.nextInt();
							if (logout == 1) {
								SessionCache.setCurrentUser(null);
							}
							break;
						default:
							System.out.println("Enter a number between 1 to 6");
							break;
						}

					}while (option != 6);
				}
				

				break;
			case 3:
				//System.out.println(userData.getAllUsers());
				//userData.getAllUsers().forEach(System.out::println);
				System.out.println(patron.toString());
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
				System.out.println("Enter First Name:");
				fn = userInput.next();
				System.out.println("Enter Last Name:");
				ln = userInput.next();
				System.out.print("Enter Password to Update:");
				pw = userInput.next();
				User updatedUser = new User();
				updatedUser.setId(id);
				updatedUser.setFirstName(fn);
				updatedUser.setLastName(ln);
				updatedUser.setPassword(pw);
				userData.updateUser(updatedUser);
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


