package com.mediahub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserSeasons")
public class UserShowSeason {

	public UserShowSeason() {
		super();
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = UserShow.class)
	private UserShow userShow;
	
	@OneToOne(targetEntity = ShowSeason.class)
	private ShowSeason showSeason;
	
	@OneToMany(targetEntity = UserShowEpisode.class)
	@JoinTable(name = "user_seasons_episodes")
	private List<UserShowEpisode> userShowEpisodes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserShow getUserShow() {
		return userShow;
	}

	public void setUserShow(UserShow userShow) {
		this.userShow = userShow;
	}

	public ShowSeason getShowSeason() {
		return showSeason;
	}

	public void setShowSeason(ShowSeason showSeason) {
		this.showSeason = showSeason;
	}

	public List<UserShowEpisode> getUserShowEpisodes() {
		return userShowEpisodes;
	}

	public void setUserShowEpisodes(List<UserShowEpisode> userShowEpisodes) {
		this.userShowEpisodes = userShowEpisodes;
	}
	
}
