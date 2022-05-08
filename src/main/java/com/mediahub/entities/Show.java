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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Shows")
public class Show {
	
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
}