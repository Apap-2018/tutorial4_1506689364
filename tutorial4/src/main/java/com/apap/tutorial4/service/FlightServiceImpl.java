package com.apap.tutorial4.service;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.repository.FlightDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * FlightServiceImpl
 *
 */

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
	}
	
	@Override
	public void deleteFlight(FlightModel flight) {
		flightDb.delete(flight);
	}
	
	@Override
	public FlightModel getFlightDetailById(Long id) {
		return flightDb.getOne(id);
	}
	
	@Override
	public List<FlightModel> getFlightList(){
		return flightDb.findAll();
	}

}
