package com.mediahub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediahub.entities.User;

public interface UserRepo extends JpaRepository<User, String>{
	
	User findByUsername(String username);
	
}
