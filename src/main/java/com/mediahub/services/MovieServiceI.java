package com.mediahub.services;

import java.util.List;

import com.mediahub.entities.Movie;

public interface MovieServiceI {
	
	public List<Movie> getMovies();
	
	public Movie getMovieById(long id);
	
	public List<Movie> getMoviesByTitle(String title);
	
	public Movie addMovie(Movie movie);
	
	public Movie updateMovie(Movie movie);
	
	public void removeMovie(Movie movie);
	
}
