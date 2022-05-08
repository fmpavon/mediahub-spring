package com.mediahub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "creationDate", nullable = false)
	private Date creationDate;

	@OneToMany(targetEntity = UserMovie.class)
	@JoinTable(name = "")
	private List<UserMovie> userMovies;
	
	@OneToMany(targetEntity = UserShow.class)
	private List<UserShow> userShow;
}
