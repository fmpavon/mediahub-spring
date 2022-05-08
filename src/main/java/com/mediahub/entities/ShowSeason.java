package com.mediahub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Seasons")
public class ShowSeason {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(targetEntity = Show.class)
	private Show show;
	
	@Column(name = "seasonNumber", nullable = false)
	private int seasonNumber;
	
	@Column(name = "title", nullable = true)
	private String title;
	
	@OneToMany(targetEntity = ShowEpisode.class)
	private List<ShowEpisode> episodes;
}
