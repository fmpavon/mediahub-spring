package com.mediahub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediahub.entities.UserMovie;
import com.mediahub.repositories.UserMovieRepo;

@Service
public class UserMovieService implements UserMovieServiceI{

	@Autowired
	private UserMovieRepo userMovieRepo;
	
	@Override
	public List<UserMovie> getMovies() {
		return userMovieRepo.findAll();
	}

	@Override
	public UserMovie getUserMovieById(long id) {
		return userMovieRepo.findById(id).get();
	}

	@Override
	public UserMovie addUserMovie(UserMovie userMovie) {
		return userMovieRepo.save(userMovie);
	}

	@Override
	public UserMovie updateUserMovie(UserMovie userMovie) {
		return userMovieRepo.save(userMovie);
	}

	@Override
	public void removeUserMovie(UserMovie userMovie) {
		userMovieRepo.delete(userMovie);
	}

	@Override
	public void removeUserMovieById(long id) {
		userMovieRepo.deleteById(id);
	}

	@Override
	public boolean userMovieExists(long id) {
		for (UserMovie um : userMovieRepo.findAll()) {
			if (um.getId() == id) {
				return true;
			}
		}
		return false;
	}

}
