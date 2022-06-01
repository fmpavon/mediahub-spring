package com.mediahub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserEpisodes")
public class UserShowEpisode {
	
	public UserShowEpisode() {
		super();
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserShowSeason getUserShowSeason() {
		return userShowSeason;
	}

	public void setUserShowSeason(UserShowSeason userShowSeason) {
		this.userShowSeason = userShowSeason;
	}

	public ShowEpisode getShowEpisode() {
		return showEpisode;
	}

	public void setShowEpisode(ShowEpisode showEpisode) {
		this.showEpisode = showEpisode;
	}

	public boolean isWatched() {
		return isWatched;
	}

	public void setWatched(boolean isWatched) {
		this.isWatched = isWatched;
	}
	
}
