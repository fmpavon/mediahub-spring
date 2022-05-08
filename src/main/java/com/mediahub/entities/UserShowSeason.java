package com.mediahub.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserShowSeason {
	private long id;
	private UserShow userShow;
	private ShowSeason showSeason;
	private List<UserShowEpisode> userShowEpisodes;
}
