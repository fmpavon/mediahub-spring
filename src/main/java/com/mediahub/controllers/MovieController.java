package com.mediahub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediahub.entities.Movie;
import com.mediahub.services.MovieServiceI;

@RestController
public class MovieController {

	@Autowired
	private MovieServiceI ms;
	
	@GetMapping("/getMovies")
	public ResponseEntity<?> getMovies() throws Exception{
		List<Movie> movieList = ms.getMovies();
		
		if (movieList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies stored in database");
		}
		
		return ResponseEntity.ok(movieList);
	}
	
}