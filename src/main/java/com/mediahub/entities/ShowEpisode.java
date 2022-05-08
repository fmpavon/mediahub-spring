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
@Table(name = "Episodes")
public class ShowEpisode {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = ShowSeason.class)
	private ShowSeason showSeason;
	
	@Column(name = "episodeNumber", nullable = false)
	private int episodeNumber;
	
	@Column(name = "isReleased", nullable = true)
	private boolean isReleased; //if the episode is already available
	
	@Column(name = "title", nullable = true)
	private String title;
}
