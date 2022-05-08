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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "UserShows")
public class UserShow {
	
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
}
