package com.mediahub.back;


import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mediahub.entities.Movie;
import com.mediahub.entities.User;
import com.mediahub.entities.UserMovie;
import com.mediahub.services.MovieService;
import com.mediahub.services.UserService;

@SpringBootApplication
@ComponentScan({"com.mediahub.services"})
@EntityScan("com.mediahub.entities")
@EnableJpaRepositories("com.mediahub.repositories")
public class MediahubBackApplication implements CommandLineRunner {

	@Autowired
	private UserService us;
	
	@Autowired
	private MovieService ms;
	
	private Scanner userInput;
	
	public static void main(String[] args) {
		SpringApplication.run(MediahubBackApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//command line menu here
		userInput = new Scanner(System.in);
		System.out.print("To start the manager write 'manager': ");
		String input = userInput.nextLine();
		
		if (input.equalsIgnoreCase("manager")) {
			mediahubManager();
		}
		
	}
	
	public void mediahubManager() {
		String input = "manager";
		
		while (!input.equalsIgnoreCase("exit")) {
			System.out.print("------------------------------------------------\n"
					+ "MediaHub Manager\n\n"
					+ "\tusers - to manage users\n"
					+ "\ttv - to manage TV Shows\n"
					+ "\tmovies - to manage movies\n\n"
					+ "\t*or any command to exit\n"
					+ "------------------------------------------------\n"
					+ "\n\nCommand: ");
			
			input = userInput.nextLine();
			
			switch (input) {
			case "users":
				usersManager();
				break;
				
			case "tv":
				System.out.print("[!] Not implemented.\n");
				break;

			case "movies":
				moviesManager();
				break;

			default:
				input = "exit";
				break;
			}
			
		}
		
		System.out.println("--- MediaHub Manager :: Bye! ---");
	}
	
	public void moviesManager() {
		System.out.print("------------------------------------------------\n"
				+ "MediaHub Manager || Movies\n\n"
				+ "\tlist - list all movies in database\n"
				+ "\tadd - add a movie\n\n"
				+ "\t*or any command to exit\n"
				+ "\t**for more options, manage in DB or API\n"
				+ "------------------------------------------------\n"
				+ "\n\nCommand: ");
		String input = userInput.nextLine();
		switch (input) {
		
			case "list":
				List<Movie> movies = ms.getMovies();
				for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext();) {
					Movie m = (Movie) iterator.next();
					System.out.print("\n" + m.getId() + "\t" + m.getTitle() + "\t" + m.getImage());
				}
				break;
				
			case "add":
				System.out.print("[!] ReleaseDate filed can only be added in database or API.\n");
				Movie movieAdd = new Movie();
				System.out.print("Title: ");
				movieAdd.setTitle(userInput.nextLine());
				System.out.print("Image: ");
				movieAdd.setImage(userInput.nextLine());
				movieAdd.setId(0);
				ms.addMovie(movieAdd);
				System.out.print("[OK] Movie added.\n");
				break;
				
			default:
				break;
		}
		
		System.out.print("\nGoing back to main menu...\n");
	}
	
	public void usersManager() {
		User user;
		System.out.print("------------------------------------------------\n"
				+ "MediaHub Manager || Users\n\n"
				+ "\tlist - list all users in database\n"
				+ "\tadd - add a user\n\n"
				+ "\tmanage - manage an specific user\n\n"
				+ "\t*or any command to exit\n"
				+ "\t**for more options, manage in DB or API\n"
				+ "------------------------------------------------\n"
				+ "\n\nCommand: ");
		String input = userInput.nextLine();
		switch (input) {
		
			case "list":
				List<User> users = us.getUsers();
				for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
					User u = (User) iterator.next();
					System.out.print("\n" + u.getUsername() + "\t" + u.getPassword() + "\t" + u.getCreationDate());
				}
				break;
				
			case "add":
				User uAdd = new User();
				System.out.print("Username: ");
				uAdd.setUsername(userInput.nextLine());
				System.out.print("Password: ");
				uAdd.setPassword(userInput.nextLine());
				System.out.print("[OK] User added.\n");
				break;
				
			case "manage":
				System.out.print("Username: ");
				input = userInput.nextLine();
				user = us.getUserByUsername(input);
				if (user != null) {
					userManager(user);
				}
				break;
				
			default:
				break;
		}
		
		System.out.print("\nGoing back to main menu...\n");
	}
	
	public void userManager(User user) {
		List<UserMovie> userMovies;
		boolean movieCheck;
		
		System.out.print("------------------------------------------------\n"
				+ "MediaHub Manager || " + user.getUsername() + " (User Management)\n\n"
				+ "\tlist_movies - list all movies in user collection\n"
				+ "\tadd_movie - add a movie to user collection\n\n"
				+ "\t*or any command to exit\n"
				+ "\t**for more options, manage in DB or API\n"
				+ "------------------------------------------------\n"
				+ "\n\nCommand: ");
		String input = userInput.nextLine();
		switch (input) {
		
			case "list_movies":
				userMovies = user.getUserMovies();
				for (Iterator<UserMovie> iterator = userMovies.iterator(); iterator.hasNext();) {
					UserMovie um = (UserMovie) iterator.next();
					Movie m = um.getMovie();
					System.out.print("\n" + um.getId() + "\t" + m.getTitle());
				}
				break;
				
			case "add_movie":
				userMovies = user.getUserMovies();
				System.out.print("Id of the movie: ");
				int mId = Integer.parseInt(userInput.nextLine());
				movieCheck = false;
				for (Iterator<UserMovie> iterator = userMovies.iterator(); iterator.hasNext();) {
					UserMovie um = (UserMovie) iterator.next();
					if (um.getMovie().getId() == mId) {
						movieCheck = true;
					}
				}
				if (!movieCheck) {
					//TODO: Create UserMovie service
					//TODO: Add new UserMovie
					//TODO: Add to user list of movies the new UserMovie
				}
				System.out.print("[OK] Movie added to user collection.\n");
				break;
				
			default:
				break;
		}
		
		System.out.print("\nGoing back to main menu...\n");
	}
	
}