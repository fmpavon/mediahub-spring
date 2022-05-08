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
@Table(name = "UserSeasons")
public class UserShowSeason {
	
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
}
