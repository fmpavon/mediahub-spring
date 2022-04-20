package com.mediahub.entities;

import java.util.Date;
import java.util.List;

public class User {
	private long id;
	private String username;
	private String password;
	private Date creationDate;
	private List<UserMovie> userMovies;
	private List<UserShow> userShow;
}
