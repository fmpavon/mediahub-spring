package com.mediahub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediahub.entities.User;
import com.mediahub.repositories.UserRepo;

@Service
public class UserService implements UserServiceI {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.getById(username);
	}

	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public void removeUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public void removeUserById(String username) {
		userRepo.deleteById(username);
	}
	
}
