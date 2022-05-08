package com.mediahub.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserMovie {
	private long id;
	private Movie movie;
	private boolean isWatched; //if the user has marked the movie as watched
}
