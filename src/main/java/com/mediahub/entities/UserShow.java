package com.mediahub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserShow {
	
	@Id
	@Column(name = "id", nullable = false)
	private long id;
	
	@OneToOne(targetEntity = Show.class)
	private Show show;
	
	@OneToMany(targetEntity = UserShowSeason.class)
	private List<UserShowSeason> userShowSeasons;
}
