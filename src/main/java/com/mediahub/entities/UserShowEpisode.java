package com.mediahub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserShowEpisode {
	
	@Id
	@Column(name = "id", nullable = false)
	private long id;
	
	@OneToOne(targetEntity = UserShowSeason.class)
	private UserShowSeason userShowSeason;
	
	@OneToOne(targetEntity = ShowEpisode.class)
	private ShowEpisode showEpisode;
	
	@Column(name = "isWatched", nullable = false)
	private boolean isWatched; //if the user has watched this episode
}
