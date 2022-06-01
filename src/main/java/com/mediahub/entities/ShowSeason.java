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

@Entity
@Table(name = "Seasons")
public class ShowSeason {
	
	public ShowSeason() {
		super();
	}

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

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ShowEpisode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<ShowEpisode> episodes) {
		this.episodes = episodes;
	}
	
}
