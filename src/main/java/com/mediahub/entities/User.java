package com.mediahub.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediahub.classes.UserRole;

@Entity
@Transactional
@Table(name = "Users")
public class User {
	
	public User() {
		super();
	}
	
	public User(String username, String password, UserRole userRole) {
		super();
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.creationDate = new Date();
		this.userMovies = new ArrayList<UserMovie>();
	}
	
	@Id
	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "userRole", nullable = false)
	private UserRole userRole;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "creationDate", nullable = false)
	private Date creationDate;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = UserMovie.class)
	@JsonIgnore
	private List<UserMovie> userMovies;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public List<UserMovie> getUserMovies() {
		return userMovies;
	}

	public void setUserMovies(List<UserMovie> userMovies) {
		this.userMovies = userMovies;
	}

	public Date getCreationDate() {
		return creationDate;
	}
}
