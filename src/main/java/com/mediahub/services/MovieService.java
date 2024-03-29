package com.mediahub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediahub.entities.Movie;
import com.mediahub.repositories.MovieRepo;

@Service
public class MovieService implements MovieServiceI{

	@Autowired
	private MovieRepo movieRepo;

	@Override
	public List<Movie> getMovies() {
		return movieRepo.findAll();
	}

	@Override
	public Movie getMovieById(long id) {
		return movieRepo.findById(id).get();
	}

	@Override
	public Movie addMovie(Movie movie) {
		return movieRepo.save(movie);
	}

	@Override
	public Movie updateMovie(Movie movie) {
		return movieRepo.save(movie);
	}

	@Override
	public void removeMovie(Movie movie) {
		movieRepo.delete(movie);
	}

	@Override
	public void removeMovieById(long id) {
		movieRepo.deleteById(id);
	}

	@Override
	public boolean movieExists(long id) {
		for (Movie m : movieRepo.findAll()) {
			if (m.getId() == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Movie> getMoviesByTitle(String searchString) {
		return movieRepo.findByTitleLikeIgnoreCase(searchString);
	}
	
}
