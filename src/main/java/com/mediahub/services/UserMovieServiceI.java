package com.mediahub.services;

import java.util.List;

import com.mediahub.entities.UserMovie;

public interface UserMovieServiceI {
	
	public List<UserMovie> getMovies();
	
	public UserMovie getUserMovieById(long id);
		
	public UserMovie addUserMovie(UserMovie userMovie);
	
	public UserMovie updateUserMovie(UserMovie userMovie);
	
	public boolean userMovieExists(long id);
	
	public void removeUserMovie(UserMovie userMovie);
	
	public void removeUserMovieById(long id);
}
