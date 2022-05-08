package com.mediahub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserMovie {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = Movie.class)
	private Movie movie;
	
	@OneToOne(targetEntity = User.class)
	private User user;
	
	@Column(name = "isWatched", nullable = false)
	private boolean isWatched; //if the user has marked the movie as watched
}
