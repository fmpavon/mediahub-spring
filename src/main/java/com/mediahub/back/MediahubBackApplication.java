package com.mediahub.back;


import java.util.Date;
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
import com.mediahub.services.MovieServiceI;
import com.mediahub.services.UserService;
import com.mediahub.services.UserServiceI;

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
					System.out.print("[!] Not implemented.\n");
					break;
					
				case "tv":
					System.out.print("[!] Not implemented.\n");
					break;

				case "movies":
					movieManager();
					break;

				default:
					input = "exit";
					break;
				}
			}
			System.out.println("--- MediaHub Manager :: Bye! ---");
		}
	}
	
	public void movieManager() {
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
				for (Iterator iterator = movies.iterator(); iterator.hasNext();) {
					Movie m = (Movie) iterator.next();
					System.out.print("\n" + m.getId() + "\t" + m.getTitle() + "\t" + m.getReleaseDate() + "\t" + m.getImage());
				}
				break;
				
			case "add":
				System.out.print("[!] ReleaseDate filed can only be added in database or API.\n");
				System.out.print("Title: ");
				String title = userInput.nextLine();
				System.out.print("Image: ");
				String image = userInput.nextLine();
				Movie movieAdd = new Movie();
				movieAdd.setTitle(title);
				movieAdd.setImage(image);
				movieAdd.setId(0);
				ms.addMovie(movieAdd);
				System.out.print("[OK] Movie added.\n");
				break;
			default:
				break;
		}
		System.out.print("\nGoing back to main menu...\n");
	}
}