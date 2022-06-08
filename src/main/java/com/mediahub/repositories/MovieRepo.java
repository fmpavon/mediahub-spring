package com.mediahub.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediahub.entities.Movie;

public interface MovieRepo extends JpaRepository<Movie, Long>{
	
	List<Movie> findByTitleLikeIgnoreCase(String title);

	List<Movie> findByReleaseDate(Date releaseDate);
	
}
