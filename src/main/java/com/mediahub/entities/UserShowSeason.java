package com.mediahub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
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
	private List<UserShowEpisode> userShowEpisodes;
}
