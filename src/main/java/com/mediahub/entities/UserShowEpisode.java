package com.mediahub.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserShowEpisode {
	private long id;
	private long episodeId;
	private boolean isWatched; //if the user has watched this episode
}
