package com.mediahub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediahub.entities.Movie;
import com.mediahub.entities.User;
import com.mediahub.entities.UserMovie;
import com.mediahub.services.MovieServiceI;
import com.mediahub.services.UserMovieServiceI;
import com.mediahub.services.UserServiceI;

@RestController
public class UserController {
	
	@Autowired
	private UserServiceI us;
	
	@Autowired
	private MovieServiceI ms;
	
	@Autowired
	private UserMovieServiceI ums;
	
	//This string must be passed as an 'adminToken' header to access certain actions
	private String internalAdminToken = "HVWJdkM0MYqoCj7LPh6lE4VErsSYa0VqbRvQ0OYtLYNIEUhhAyT5o5OQZ25hDH1nLAsnNcf2GFHidSPgYQg4qYGy665neDZkBpPJHUGBoVofaW2cuFjaCHQT9yKauzHZ";
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(@RequestHeader("adminKey") String externalAdminToken) throws Exception{
		
		//Administration check
		if (!internalAdminToken.equals(externalAdminToken)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Administration access required");
		}
		
		final List<User> userList = us.getUsers();
		
		if (userList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users stored in database");
		}
		
		return ResponseEntity.ok(userList);
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username, @RequestHeader("password") String password) throws Exception{
		
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		User user = us.getUserByUsername(username);
		
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<?> deleteUser(@RequestHeader("adminKey") String externalAdminToken, @PathVariable String username) {
		
		//Administration check
		if (!internalAdminToken.equals(externalAdminToken)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Administration access required");
		}
		
		if (us.getUserByUsername(username) == null)
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find user");
		
		us.removeUserById(username);
		new ResponseStatusException(HttpStatus.ACCEPTED, "User removed");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("users/add")
	public ResponseEntity<?> addUser(@RequestBody User targetUser) {
		
		//Check all values has been passed and are valid
		if (targetUser.getUsername() == null 
				|| targetUser.getPassword() == null) {
			new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must specify username and password");
		} else if (targetUser.getUsername().length() < 4) {
			new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username length must be greater than 4");
		} else if (targetUser.getPassword().length() < 4) {
			new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be greater than 4");
		}
		
		//Check if user exists
		if (us.userExists(targetUser.getUsername())) {
			new ResponseStatusException(HttpStatus.CONFLICT, "Username already used");
		}
		
		//Adds the new user
		User userAdd = new User(targetUser.getUsername(), targetUser.getPassword());
		us.addUser(userAdd);
		new ResponseStatusException(HttpStatus.ACCEPTED, "User added");
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * MOVIES
	 */
	
	@GetMapping("/users/{username}/movies")
	public ResponseEntity<?> getMovies(@PathVariable String username, @RequestHeader("password") String password){
		
		//Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		User user = us.getUserByUsername(username);
		
		//Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		//Get all UserMovies
		return ResponseEntity.ok(user.getUserMovies());
	}
	
	@GetMapping("/users/{username}/movies/{movieId}")
	public ResponseEntity<?> getMovie(@PathVariable String username, @PathVariable int movieId, @RequestHeader("password") String password){
		
		//Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		User user = us.getUserByUsername(username);
		
		//Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		//Get all UserMovies
		for (UserMovie um : user.getUserMovies()) {
			if (um.getMovie().getId() == movieId) {
				return ResponseEntity.ok(um);
			}
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User's collection doesn't contain a movie with the specified id");
	}
	
	@PostMapping("/users/{username}/movies/add/{movieId}")
	public ResponseEntity<?> addMovie(@PathVariable String username, @PathVariable int movieId, @RequestHeader("password") String password){
		
		//Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		//Check movie exists
		if (!ms.movieExists(movieId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie doesn't exist");
		}
		
		User user = us.getUserByUsername(username);
		Movie movie = ms.getMovieById(movieId);
		
		//Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		//Check if UserMovie already exists
		for (UserMovie um : user.getUserMovies()) {
			if (um.getMovie().getId() == movieId) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie already exists in user's collection");
			}
		}
		
		//Add UserMovie
		List<UserMovie> userMovies = user.getUserMovies();
		UserMovie userMovieAdd = new UserMovie(user, movie);
		ums.addUserMovie(userMovieAdd);
		userMovies.add(userMovieAdd);
		user.setUserMovies(userMovies);
		us.updateUser(user);
		throw new ResponseStatusException(HttpStatus.ACCEPTED, "Movie added to user's collection");
	}
	
	@PostMapping("/users/{username}/movies/update")
	public ResponseEntity<?> updateMovie(@PathVariable String username,  
			@RequestHeader("password") String password, 
			@RequestBody UserMovie targetUserMovie){
		
		if (targetUserMovie.getUser() != null  || targetUserMovie.getMovie() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User/Movie entities can't be updated");
		}
		
		//Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		//Check UserMovie exists
		if (!ums.userMovieExists(targetUserMovie.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie container with the specified id doesn't exists in user's collection");
		}
		
		User user = us.getUserByUsername(username);
		UserMovie userMovie = ums.getUserMovieById(targetUserMovie.getId());
		
		//Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		userMovie.setWatched(targetUserMovie.isWatched());
		ums.updateUserMovie(userMovie);
		throw new ResponseStatusException(HttpStatus.ACCEPTED, "User's movie container updated");	
	}
	
	@DeleteMapping("/users/{username}/movies/{userMovieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable String username, 
			@PathVariable int userMovieId, 
			@RequestHeader("password") String password){
		
		//Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}
		
		//Check UserMovie exists
		if (!ums.userMovieExists(userMovieId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie container with the specified id doesn't exists in user's collection");
		}
		
		User user = us.getUserByUsername(username);
		
		//Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}
		
		//First remove FK
		List<UserMovie> userMovieList = user.getUserMovies();
		userMovieList.remove(ums.getUserMovieById(userMovieId));
		user.setUserMovies(userMovieList);
		us.updateUser(user);
		
		//Finally remove UserMovie from db
		ums.removeUserMovieById(userMovieId);
		throw new ResponseStatusException(HttpStatus.ACCEPTED, "User's movie container removed");	
	}
	
}
