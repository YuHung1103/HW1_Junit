package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User getUser(int Id);
	
	public User createUser(User newUser);
	
	public User updateUser(int Id, User updatedUser);
	
	public String deleteUser(int Id);
	
	public String login(String userAccount, String userPassword);
	
	public boolean authenticateUser(String userAccount, String userPassword);
	
	public String register(String Account, String Password, String comfirmPassword,
							String Name, int Phone, String Email, int role);
	
	public String forget(String userEmail, String Password, String comfirmPassword);
	
	public String logout(String userAccount);
	
	public boolean hasAdminRole(String userAccount);
	
	public boolean hasUserRole(String userAccount);
	
}
