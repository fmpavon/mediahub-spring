package com.mediahub.controllers;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mediahub.entities.Movie;
import com.mediahub.entities.User;
import com.mediahub.entities.UserMovie;
import com.mediahub.services.MovieServiceI;
import com.mediahub.services.UserMovieServiceI;
import com.mediahub.services.UserServiceI;

@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private UserServiceI us;

	@Autowired
	private MovieServiceI ms;

	@Autowired
	private UserMovieServiceI ums;

	public String showIndex() {
		return "index";
	}

	@GetMapping("/login")
	public String redirectToLogin() {
		return "login";
	}

	@PostMapping("/home")
	public String homeLogin(@RequestParam String username,
			@RequestParam String password,
			Model model,
			HttpServletResponse response) {

		// Check username
		if (!us.userExists(username)) {
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			return "error";
		}

		// Set cookies
		Cookie userIdCookie = new Cookie("user-id", user.getUsername());
		userIdCookie.setSecure(true);
		userIdCookie.setHttpOnly(true);
		response.addCookie(userIdCookie);

		Cookie userCredentialsCookie = new Cookie("user-credentials", user.getPassword());
		userCredentialsCookie.setSecure(true);
		userCredentialsCookie.setHttpOnly(true);
		response.addCookie(userCredentialsCookie);

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "home";
	}

	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";
		// Check username
		if (!us.userExists(username)) {
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			return "error";
		}

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "home";
	}

	@GetMapping("/movie")
	public String movie(HttpServletRequest request, @RequestParam long movieId,
			Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";
		// Check username
		if (!us.userExists(username)) {
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			return "error";
		}

		// Check movie
		if (!ms.movieExists(movieId)) {
			return "error";
		}

		// (Optional) Check userMovie
		for (UserMovie um : user.getUserMovies()) {
			if (um.getMovie().getId() == movieId) {
				model.addAttribute("userMovie", um);
			}
		}

		model.addAttribute("movie", ms.getMovieById(movieId));

		return "movie";
	}

	@GetMapping("/movie/add")
	public String addUserMovie(HttpServletRequest request, @RequestParam long movieId,
			Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";
		// Check username
		if (!us.userExists(username)) {
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			return "error";
		}

		// Check movie
		if (!ms.movieExists(movieId)) {
			return "error";
		}

		// Check if UserMovie already exists
		for (UserMovie um : user.getUserMovies()) {
			if (um.getMovie().getId() == movieId) {
				return "error";
			}
		}

		Movie movie = ms.getMovieById(movieId);

		// Add UserMovie
		List<UserMovie> userMovies = user.getUserMovies();
		UserMovie userMovieAdd = new UserMovie(user, movie);
		ums.addUserMovie(userMovieAdd);
		userMovies.add(userMovieAdd);
		user.setUserMovies(userMovies);
		us.updateUser(user);

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/movie/remove")
	public String removeUserMovie(HttpServletRequest request, @RequestParam long userMovieId, Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";
		// Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}

		// Check UserMovie exists
		if (!ums.userMovieExists(userMovieId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Movie container with the specified id doesn't exists in user's collection");
		}

		User user = us.getUserByUsername(username);

		// Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}

		// First remove FK
		List<UserMovie> userMovieList = user.getUserMovies();
		userMovieList.remove(ums.getUserMovieById(userMovieId));
		user.setUserMovies(userMovieList);
		us.updateUser(user);

		// Finally remove UserMovie from db
		ums.removeUserMovieById(userMovieId);

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/movie/update")
	public String updateUserMovie(HttpServletRequest request, @RequestParam long userMovieId,
			@RequestParam boolean isWatched,
			Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";
		// Check user exists
		if (!us.userExists(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}

		// Check UserMovie exists
		if (!ums.userMovieExists(userMovieId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Movie container with the specified id doesn't exists in user's collection");
		}

		User user = us.getUserByUsername(username);

		// Check credentials
		if (!user.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
		}

		UserMovie userMovie = ums.getUserMovieById(userMovieId);
		userMovie.setWatched(isWatched);
		ums.updateUserMovie(userMovie);

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/collection")
	public String collection(HttpServletRequest request, Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty())
			return "error";

		// Check username
		if (!us.userExists(username)) {
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			return "error";
		}

		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}
}
