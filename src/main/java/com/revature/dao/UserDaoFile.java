package com.revature.dao;


import java.util.List;
import java.util.Set;
//modified down
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.EOFException;
//modified up
import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {
	
	
	/*modified down(change the variable)*/
	public static String fileLocation = "src\\output\\users.txt";
	public static File FileOfUsers = new File(fileLocation);
	private static int id = 0;
	public static List<User> ListOfUsers = new ArrayList<User>() ;
	

	
	/*modified up(change the variable)*/
	public User addUser(User user) 
	{
		
		/*modified down(change the variable)*/
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(FileOfUsers));
			objectOut.writeObject(user);
			System.out.println("User Registration Successful!!!");
			objectOut.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return user;
		/*modified up(change the variable)*/
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		/*modified down (change variable)*/
		User userRequested = null;
		for(User user : ListOfUsers) {
			if(user.getId().equals(userId)) {
				userRequested = user;
			}
		}

		/*modified up (change variable)*/
		return userRequested;
		
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		/*modified down (change variable)*/
		User userRequested = null;
		for (User user : ListOfUsers) {
			if (user.getUsername().equals(username) && user.getPassword().equals(pass)) {
				userRequested = user;
			}
		}

		/*modified up (change variable)*/
		return userRequested;
	}

	public List<User> getAllUsers() {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(FileOfUsers));
			User user = new User();
			//do  {
			while(user!=null) {
				//User user = (User) ois.readObject();
				//ListOfUsers.add(user);
				user = (User) objectIn.readObject();

				//ListOfUsers.add((User) objectIn.readObject());
				ListOfUsers.add(user);
			}
			//}while (objectIn.readObject() != null);//causing problems.
			//while
			
			objectIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		
		return ListOfUsers;
	}

	public User updateUser(User u) {
		
		return null;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		boolean userRemoved = false;
		for (User user : ListOfUsers) {
			if(user.getId()==u.getId()) {
				userRemoved = ListOfUsers.remove(u);
			}
		}
		return userRemoved;
	}

}
