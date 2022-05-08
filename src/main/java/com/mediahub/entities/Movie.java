package com.mediahub.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movie {
	
	@Id
	@Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "releaseDate", nullable = true)
	private Date releaseDate;
	
	@Column(name = "image", nullable = true)
	private String image;
}
