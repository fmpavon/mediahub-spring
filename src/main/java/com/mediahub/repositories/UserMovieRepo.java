package com.mediahub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediahub.entities.UserMovie;

public interface UserMovieRepo extends JpaRepository<UserMovie, Long>{

}
