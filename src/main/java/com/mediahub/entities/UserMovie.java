package com.mediahub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserMovies")
public class UserMovie {
	
	public UserMovie() {
		super();
	}
	
	public UserMovie(User user, Movie movie) {
		super();
		this.id = 0;
		this.user = user;
		this.movie = movie;
		this.isWatched = false;
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isWatched() {
		return isWatched;
	}

	public void setWatched(boolean isWatched) {
		this.isWatched = isWatched;
	}
	
}
