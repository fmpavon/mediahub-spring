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
@Table(name = "UserShows")
public class UserShow {
	
	public UserShow() {
		super();
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = Show.class)
	private Show show;
	
	@OneToOne(targetEntity = User.class)
	private User user;
	
	@OneToMany(targetEntity = UserShowSeason.class)
	@JoinTable(name = "user_shows_seasons")
	private List<UserShowSeason> userShowSeasons;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserShowSeason> getUserShowSeasons() {
		return userShowSeasons;
	}

	public void setUserShowSeasons(List<UserShowSeason> userShowSeasons) {
		this.userShowSeasons = userShowSeasons;
	}
	
}
