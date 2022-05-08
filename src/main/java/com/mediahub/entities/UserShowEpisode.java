package com.mediahub.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserShowEpisode {
	private long id;
	private UserShowSeason userShowSeason;
	private ShowEpisode showEpisode;
	private boolean isWatched; //if the user has watched this episode
}
