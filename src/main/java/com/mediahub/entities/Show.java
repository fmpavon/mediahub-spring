package com.mediahub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Shows")
public class Show {
	
	public Show() {
		super();
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Temporal(TemporalType.DATE)
	@Column(name="releaseDate", nullable = true)
	private Date releaseDate;
	
	@Column(name = "image", nullable = true)
	private String image;
	
	@Column(name = "isAiring", nullable = true)
	private boolean isAiring; //if the show is still airing live or have seasons to come
	
	@OneToMany(targetEntity = ShowSeason.class)
	@Column(name = "seasons")
	private List<ShowSeason> seasons;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isAiring() {
		return isAiring;
	}

	public void setAiring(boolean isAiring) {
		this.isAiring = isAiring;
	}

	public List<ShowSeason> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<ShowSeason> seasons) {
		this.seasons = seasons;
	}
	
}