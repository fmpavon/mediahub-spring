package com.mediahub.services;

import java.util.List;

import com.mediahub.entities.Show;

public interface ShowServiceI {
	
	public List<Show> getShows();
	
	public Show getShowById(long id);
	
	public Show addShow(Show show);
	
	public Show updateShow(Show show);
	
	public void removeShow(Show show);
	
	public void removeShowById(long id);

}
