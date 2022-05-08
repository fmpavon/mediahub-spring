package com.mediahub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "UserEpisodes")
public class UserShowEpisode {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = UserShowSeason.class)
	private UserShowSeason userShowSeason;
	
	@OneToOne(targetEntity = ShowEpisode.class)
	private ShowEpisode showEpisode;
	
	@Column(name = "isWatched", nullable = false)
	private boolean isWatched; //if the user has watched this episode
}
