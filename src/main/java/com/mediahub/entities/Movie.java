package com.mediahub.entities;

import java.awt.Image;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Movie {
	private long id;
	private String title;
	private Date releaseDate;
	private Image image;
	
}
