package com.mediahub.entities;

import java.awt.Image;
import java.util.Date;
import java.util.List;

public class Show {
	private long id;
	private String title;
	private Date releaseDate;
	private Image image;
	private boolean isAiring; //if the show is still airing live or have seasons to come
	private List<ShowSeason> seasons;
}