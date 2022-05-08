package com.mediahub.entities;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShowEpisode {
	private long id;
	private long showId;
	private long seasonId;
	private int episodeNumber;
	private boolean isReleased; //if the episode is already available
	private String title;
}
