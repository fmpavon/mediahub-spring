package com.mediahub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediahub.entities.Show;
import com.mediahub.repositories.ShowRepo;

@Service
public class ShowService implements ShowServiceI{
	
	@Autowired
	private ShowRepo showRepo;

	@Override
	public List<Show> getShows() {
		return showRepo.findAll();
	}

	@Override
	public Show getShowById(long id) {
		return showRepo.findById(id).get();
	}

	@Override
	public Show addShow(Show show) {
		return showRepo.save(show);
	}

	@Override
	public Show updateShow(Show show) {
		return showRepo.save(show);
	}

	@Override
	public void removeShow(Show show) {
		showRepo.delete(show);
	}

	@Override
	public void removeShowById(long id) {
		showRepo.deleteById(id);
	}
}
