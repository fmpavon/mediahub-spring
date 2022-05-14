package com.mediahub.services;

import java.util.List;

import com.mediahub.entities.User;

public interface UserServiceI {
	
	public List<User> getUsers();
	
	public User getUserByUsername(String username);
	
	public User addUser(User user);
	
	public User updateUser(User user);
	
	public void removeUser(User user);
	
	public void removeUserById(String username);
	
}
