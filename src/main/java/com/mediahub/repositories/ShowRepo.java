package com.mediahub.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediahub.entities.Show;

public interface ShowRepo extends JpaRepository<Show, Long>{

	List<Show> findByTitle(String title);
	List<Show> findByReleaseDate(Date releaseDate);
	List<Show> findByisAiring(Boolean isAiring);

}
