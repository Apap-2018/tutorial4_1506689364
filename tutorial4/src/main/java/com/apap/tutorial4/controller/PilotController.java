	package com.apap.tutorial4.controller;
	
	import com.apap.tutorial4.model.FlightModel;
	import com.apap.tutorial4.model.PilotModel;
	import com.apap.tutorial4.service.PilotService;

	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.ResponseBody;
	
	/**
	 * PilotController
	 * 
	 */
	
	@Controller
	public class PilotController {
		@Autowired
		private PilotService pilotService;
		
		@RequestMapping("/")
		private String home() {
			return "home";
		}
		
		@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
		private String add(Model model) {
			model.addAttribute("pilot", new PilotModel());
			return "addPilot";
		}
		
		@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
		private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
			pilotService.addPilot(pilot);
			return "add";
		}
		
		/**
		 * 
		 * View Pilot by License Number
		 * @param licenseNumber
		 * @param model
		 * @return
		 */
		@RequestMapping("/pilot/view")
		private String viewPilot(@RequestParam(value = "licenseNumber") String licenseNumber, Model model)
		{
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
			
			if(pilot != null) {
				List <FlightModel> flightList = (List<FlightModel>) pilot.getPilotflight();
				model.addAttribute("pilot", pilot);
				model.addAttribute("flightList", flightList);
				return "view-pilot";
				
			} else {
				return "not-found";
			}
					
		}
		
		/**
		 * Delete Pilot by License Number
		 * @param licenseNumber
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.POST)
		private String deletePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
			pilotService.deletePilot(pilot);
			return "delete";
			
		}
		
		/**
		 * Update Pilot
		 * @param licenseNumber
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
		private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
			if(pilot != null) {
				model.addAttribute("pilot", pilot);
				return "update-pilot";
			} else {
				return "not-found";
			}
			
		}
		
		/**
		 * Update Pilot When Submit
		 * @param pilot
		 * @return
		 */
		@RequestMapping(value = "/pilot/update", method = RequestMethod.POST)
		private String updatePilotSubmit(@ModelAttribute PilotModel pilot) {
			PilotModel pilotArchive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
			
			pilotArchive.setFlyHour(pilot.getFlyHour());
			pilotArchive.setName(pilot.getName());
			
			pilotService.addPilot(pilotArchive);
			
			return "update-submit";
			
		}
		
		
		
	}
