package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface UserService {
	
	List<User> getAllUsers();
	
	User getUser(int Id);
	
	String createUser(String userAccount, String userPassword, String userName,
			int userPhone, String userEmail, String[] role);
	
	String updateUser(int Id, String userAccount, String userPassword, String userName,
			int userPhone, String userEmail, String[] role);

	String deleteUser(int Id);
	
	String login(String userAccount, String userPassword);
	
	String register(String Account, String Password, String comfirmPassword,
							String Name, int Phone, String Email, String[] role);
	
	String forget(String userEmail, String Password, String comfirmPassword);
	
	String logout(String userAccount);
	
	boolean hasAdminRole(String userAccount);
	
	boolean hasUserRole(String userAccount);
	
}
