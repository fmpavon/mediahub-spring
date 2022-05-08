package com.mediahub.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShowSeason {
	private long id;
	private long showId;
	private int seasonNumber;
	private String title;
	private List<ShowEpisode> episode;
}
