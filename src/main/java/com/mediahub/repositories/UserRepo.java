package com.mediahub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediahub.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	List<User> findByUsername(String username);
	
}
