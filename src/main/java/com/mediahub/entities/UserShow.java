package com.mediahub.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserShow {
	private long id;
	private long showId;
	private List<UserShowSeason> userShowSeasons;
}
