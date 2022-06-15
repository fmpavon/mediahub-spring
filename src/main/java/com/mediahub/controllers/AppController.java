package com.mediahub.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediahub.classes.UserRole;
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

	@GetMapping("")
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
			model.addAttribute("errorTitle", "Credenciales incorrectas");
			model.addAttribute("errorDescription", "No se ha encontrado el usuario especificado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales incorrectas");
			model.addAttribute("errorDescription", "Contraseña incorrecta.");
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

		model.addAttribute("user", user);
		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "home";
	}

	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		model.addAttribute("user", user);
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
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check movie
		if (!ms.movieExists(movieId)) {
			model.addAttribute("errorTitle", "Película no encontrada");
			model.addAttribute("errorDescription",
					"La película especificada no ha sido encontrada, es posible que fuera eliminada.");
			return "error";
		}

		// (Optional) Check userMovie
		for (UserMovie um : user.getUserMovies()) {
			if (um.getMovie().getId() == movieId) {
				model.addAttribute("userMovie", um);
			}
		}

		model.addAttribute("user", user);
		model.addAttribute("movie", ms.getMovieById(movieId));

		return "movie";
	}

	@GetMapping("/movie/add")
	public String addUserMovie(HttpServletRequest request, @RequestParam long movieId,
			Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check movie
		if (!ms.movieExists(movieId)) {
			model.addAttribute("errorTitle", "Película no encontrada");
			model.addAttribute("errorDescription",
					"La película especificada no ha sido encontrada, es posible que fuera eliminada.");
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

		model.addAttribute("user", user);
		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/movie/remove")
	public String removeUserMovie(HttpServletRequest request, @RequestParam long userMovieId, Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check user exists
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		// Check UserMovie exists
		if (!ums.userMovieExists(userMovieId)) {
			model.addAttribute("errorTitle", "No encontrado");
			model.addAttribute("errorDescription", "Esta película no se encuentra en la colección.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check credentials
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// First remove FK
		List<UserMovie> userMovieList = user.getUserMovies();
		userMovieList.remove(ums.getUserMovieById(userMovieId));
		user.setUserMovies(userMovieList);
		us.updateUser(user);

		// Finally remove UserMovie from db
		ums.removeUserMovieById(userMovieId);

		model.addAttribute("user", user);
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
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check user exists
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		// Check UserMovie exists
		if (!ums.userMovieExists(userMovieId)) {
			model.addAttribute("errorTitle", "No encontrado");
			model.addAttribute("errorDescription", "La película no se encuentra en la colección.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check credentials
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		UserMovie userMovie = ums.getUserMovieById(userMovieId);
		userMovie.setWatched(isWatched);
		ums.updateUserMovie(userMovie);

		model.addAttribute("user", user);
		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/collection")
	public String collection(HttpServletRequest request, Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}

		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		model.addAttribute("user", user);
		model.addAttribute("userMovies", user.getUserMovies());

		return "collection";
	}

	@GetMapping("/admin/users")
	public String adminUsers(HttpServletRequest request, Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}

		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check role
		if (user.getUserRole() != UserRole.Administrator) {
			model.addAttribute("errorTitle", "Sin permisos");
			model.addAttribute("errorDescription", "");
			return "error";
		}

		model.addAttribute("users", us.getUsers());
		model.addAttribute("user", user);

		return "administration/user/userManagement";
	}

	@GetMapping("/admin/user")
	public String adminUser(HttpServletRequest request, @RequestParam String action,
			@RequestParam String targetUsername, @RequestParam(required = false) String targetPassword,
			@RequestParam(required = false) String targetUserRole, Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}

		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check role
		if (user.getUserRole() != UserRole.Administrator) {
			model.addAttribute("errorTitle", "Sin permisos");
			model.addAttribute("errorDescription", "");
			return "error";
		}

		model.addAttribute("user", user);

		UserRole targetUserRoleFinal;

		switch (action) {
			case "addPassthrough":
				return "administration/user/addPassthrough";
			case "add":
				targetUserRoleFinal = UserRole.User;
				switch (targetUserRole) {
					case "Administrator":
						targetUserRoleFinal = UserRole.Administrator;
						break;
				}
				User targetUser = new User(targetUsername, targetPassword, targetUserRoleFinal);
				if (targetUser.getUsername() == null
						|| targetUser.getPassword() == null) {
							model.addAttribute("errorTitle", "Sin completar");
							model.addAttribute("errorDescription", "No ha completado los campos necesarios.");
					return "error";
				} else if (targetUser.getUsername().length() < 4) {
					model.addAttribute("errorTitle", "Longitud de usuario menor a 4 caracteres");
					model.addAttribute("errorDescription", "El nombre de usuario debe ser mayor a 4 caracteres.");
					return "error";
				} else if (targetUser.getPassword().length() < 4) {
					model.addAttribute("errorTitle", "Longitud de contraseña menor a 4 caracteres");
					model.addAttribute("errorDescription", "La contraseña debe ser mayor a 4 caracteres");
					return "error";
				}
				if (us.userExists(targetUser.getUsername())) {
					model.addAttribute("errorTitle", "Escoga otro nombre de usuario");
					model.addAttribute("errorDescription", "El nombre de usuario escogido ya es propiedad de otro usuario.");
					return "error";
				}
				User userAdd = new User(targetUser.getUsername(), targetUser.getPassword(), targetUser.getUserRole());
				us.addUser(userAdd);
				model.addAttribute("users", us.getUsers());
				return "administration/user/userManagement";
			case "updatePassthrough":
				model.addAttribute("targetUser", us.getUserByUsername(targetUsername));
				return "administration/user/updatePassthrough";
			case "update":
				targetUserRoleFinal = UserRole.User;
				switch (targetUserRole) {
					case "Administrator":
						targetUserRoleFinal = UserRole.Administrator;
						break;
				}
				User targetUserUpdate = us.getUserByUsername(targetUsername);
				// Checks
				if (targetUserUpdate.getUsername() == null
						|| targetUserUpdate.getPassword() == null) {
							model.addAttribute("errorTitle", "Sin completar");
							model.addAttribute("errorDescription", "No ha completado los campos necesarios.");
					return "error";
				} else if (targetUserUpdate.getUsername().length() < 4) {
					model.addAttribute("errorTitle", "Longitud de usuario menor a 4 caracteres");
					model.addAttribute("errorDescription", "El nombre de usuario debe ser mayor a 4 caracteres.");
					return "error";
				} else if (targetUserUpdate.getPassword().length() < 4) {
					model.addAttribute("errorTitle", "Longitud de contraseña menor a 4 caracteres");
					model.addAttribute("errorDescription", "La contraseña debe ser mayor a 4 caracteres");
					return "error";
				}
				targetUserUpdate.setPassword(targetPassword);
				targetUserUpdate.setUserRole(targetUserRoleFinal);
				User userUpdate = new User(targetUserUpdate.getUsername(), targetUserUpdate.getPassword(),
						targetUserUpdate.getUserRole());
				us.updateUser(userUpdate);
				model.addAttribute("users", us.getUsers());
				return "administration/user/userManagement";
			case "removePassthrough":
				model.addAttribute("targetUsername", targetUsername);
				return "administration/user/removePassthrough";
			case "remove":
				if (us.userExists(targetUsername)) {
					// First remove FK
					User targetFk = us.getUserByUsername(targetUsername);
					List<UserMovie> stFk = targetFk.getUserMovies();
					targetFk.setUserMovies(null);
					us.updateUser(targetFk);
					for (UserMovie um : stFk) {
						ums.removeUserMovieById(um.getId());
					}
					us.removeUserById(targetUsername);
				}
				model.addAttribute("users", us.getUsers());
				return "administration/user/userManagement";
		}

		model.addAttribute("errorTitle", "No se ha especificado acción");
		model.addAttribute("errorDescription", "No se ha especificado acción (añadir, actualizar o eliminar), debe navegar a esta página desde el menú.");
		return "error";
	}

	@GetMapping("/admin/movies")
	public String adminMovies(HttpServletRequest request, Model model) {

		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}

		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check role
		if (user.getUserRole() != UserRole.Administrator) {
			model.addAttribute("errorTitle", "Sin permisos");
			model.addAttribute("errorDescription", "");
			return "error";
		}

		model.addAttribute("movies", ms.getMovies());
		model.addAttribute("user", user);

		return "administration/content/contentManagement";
	}

	@GetMapping("/admin/movie")
	public String adminMovie(HttpServletRequest request,
			@RequestParam String action,
			@RequestParam long targetMovieId,
			@RequestParam(required = false) String targetImage,
			@RequestParam(required = false) String targetTitle,
			@RequestParam(required = false) String targetBackgroundImage,
			@RequestParam(required = false) Date targetReleaseDate,
			Model model) {

		String username = "", password = "";

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}

		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		// Check role
		if (user.getUserRole() != UserRole.Administrator) {
			model.addAttribute("errorTitle", "Sin permisos");
			model.addAttribute("errorDescription", "");
			return "error";
		}

		model.addAttribute("user", user);

		switch (action) {
			case "addPassthrough":
				return "administration/content/addPassthrough";
			case "add":
				Movie targetMovieAdd = new Movie();
				targetMovieAdd.setTitle(targetTitle);
				targetMovieAdd.setImage(targetImage);
				targetMovieAdd.setReleaseDate(targetReleaseDate);
				targetMovieAdd.setBackgroundImage(targetBackgroundImage);
				ms.addMovie(targetMovieAdd);

				model.addAttribute("movies", ms.getMovies());
				return "administration/content/contentManagement";
			case "updatePassthrough":
				model.addAttribute("targetMovie", ms.getMovieById(targetMovieId));
				return "administration/content/updatePassthrough";
			case "update":
				if (!ms.movieExists(targetMovieId)) {
					model.addAttribute("errorTitle", "Película no encontrada");
					model.addAttribute("errorDescription", "No se ha encontrado la película en la colección");
					return "error";
				}

				Movie targetMovieUpdate = ms.getMovieById(targetMovieId);
				targetMovieUpdate.setTitle(targetTitle);
				targetMovieUpdate.setImage(targetImage);
				targetMovieUpdate.setReleaseDate(targetReleaseDate);
				targetMovieUpdate.setBackgroundImage(targetBackgroundImage);
				ms.updateMovie(targetMovieUpdate);

				model.addAttribute("movies", ms.getMovies());
				return "administration/content/contentManagement";
			case "removePassthrough":
				Movie targetMovieRemoval = ms.getMovieById(targetMovieId);
				model.addAttribute("targetTitle", targetMovieRemoval.getTitle());
				model.addAttribute("targetMovieId", targetMovieRemoval.getId());
				return "administration/content/removePassthrough";
			case "remove":
				if (ms.movieExists(targetMovieId)) {
					ms.removeMovieById(targetMovieId);
				}
				model.addAttribute("movies", ms.getMovies());
				return "administration/content/contentManagement";
		}

		model.addAttribute("errorTitle", "Sin acción");
		model.addAttribute("errorDescription", "Se debe especificar acción (añadir, actualizar o eliminar), navegación por menú requerida.");
		return "error";
	}

	@PostMapping("/searchMovie")
	public String searchMovie(@RequestParam String searchString,
			HttpServletRequest request,
			Model model) {
		String username = "", password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription", "No se ha encontrado token de inicio de sesión en tu navegador.");
			return "error";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user-id"))
				username = cookie.getValue();
			else if (cookie.getName().equalsIgnoreCase("user-credentials"))
				password = cookie.getValue();
		}

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorTitle", "No has iniciado sesión");
			model.addAttribute("errorDescription",
					"No se ha encontrado token de inicio de sesión válido en tu navegador.");
			return "error";
		}
		// Check username
		if (!us.userExists(username)) {
			model.addAttribute("errorTitle", "Usuario no registrado o eliminado");
			model.addAttribute("errorDescription", "Tu usuario no se encuentra registrado.");
			return "error";
		}

		User user = us.getUserByUsername(username);

		// Check password
		if (!user.getPassword().equals(password)) {
			model.addAttribute("errorTitle", "Credenciales inválidas");
			model.addAttribute("errorDescription",
					"La contraseña ha cambiado, si no has cambiado tu contraseña, contacta con un administrador.");
			return "error";
		}

		String finalSearch = "%" + searchString + "%";
		model.addAttribute("user", user);
		model.addAttribute("movies", ms.getMoviesByTitle(finalSearch));
		model.addAttribute("userMovies", user.getUserMovies());

		return "home";
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration/registration";
	}

	@PostMapping("/registration/run")
	public String registrationProcess(@RequestParam String username, @RequestParam String password, Model model) {

		// Check all values has been passed and are valid
		if (username.isBlank()
				|| password.isBlank()) {
					model.addAttribute("errorTitle", "Sin completar");
					model.addAttribute("errorDescription", "No ha completado los campos necesarios.");
			return "error";
		} else if (username.length() < 4) {
			model.addAttribute("errorTitle", "Longitud de usuario menor a 4 caracteres");
			model.addAttribute("errorDescription", "El nombre de usuario debe ser mayor a 4 caracteres.");
			return "error";
		} else if (password.length() < 4) {
			model.addAttribute("errorTitle", "Longitud de contraseña menor a 4 caracteres");
			model.addAttribute("errorDescription", "La contraseña debe ser mayor a 4 caracteres");
			return "error";
		}

		// Check if user exists
		if (us.userExists(username)) {
			model.addAttribute("errorTitle", "Nombre de usuario no disponible");
			model.addAttribute("errorDescription", "Otro usuario ya ha escogido el nombre de usuario especificado.");
			return "error";
		}

		User userAdd = new User(username, password, UserRole.User);
		us.addUser(userAdd);
		return "registration/success";
	}

	@GetMapping("/help")
	public String help() {
		return "help";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
}
