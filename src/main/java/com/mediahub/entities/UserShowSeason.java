package com.mediahub.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserShowSeason {
	private long id;
	private long seasonId;
	private List<UserShowEpisode> userShowEpisodes;
}
