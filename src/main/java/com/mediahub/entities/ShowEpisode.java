package com.mediahub.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Episodes")
public class ShowEpisode {
	
	public ShowEpisode() {
		super();
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ShowSeason getShowSeason() {
		return showSeason;
	}

	public void setShowSeason(ShowSeason showSeason) {
		this.showSeason = showSeason;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public boolean isReleased() {
		return isReleased;
	}

	public void setReleased(boolean isReleased) {
		this.isReleased = isReleased;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
