package com.apap.tutorial4.service;



import com.apap.tutorial4.model.PilotModel;

/**
 * PilotService
 *
 */
public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	
	void addPilot(PilotModel pilot);
	
	void deletePilot(PilotModel pilot);
}
