package com.mediahub.services;

import java.util.List;

import com.mediahub.entities.Movie;

public interface MovieServiceI {
	
	public List<Movie> getMovies();
	
	public Movie getMovieById(long id);
	
	public Movie addMovie(Movie movie);
	
	public Movie updateMovie(Movie movie);
	
	public boolean movieExists(long id);
	
	public void removeMovie(Movie movie);
	
	public void removeMovieById(long id);
	
}
