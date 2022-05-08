package com.mediahub.entities;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Show {
	private long id;
	private String title;
	private Date releaseDate;
	private String image;
	private boolean isAiring; //if the show is still airing live or have seasons to come
	private List<ShowSeason> seasons;
}