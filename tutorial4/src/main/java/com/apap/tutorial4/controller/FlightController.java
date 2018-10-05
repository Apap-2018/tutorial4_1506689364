	package com.apap.tutorial4.controller;
	
	import com.apap.tutorial4.model.FlightModel;
	import com.apap.tutorial4.model.PilotModel;
	import com.apap.tutorial4.service.FlightService;
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
	 * 
	 * FlightController
	 *
	 */
	@Controller
	public class FlightController {
		@Autowired
		private FlightService flightService;
		
		@Autowired
		private PilotService pilotService;
		
		@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
		private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
			FlightModel flight = new FlightModel();
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
			flight.setPilot(pilot);
			
			model.addAttribute("flight", flight);
			return "addFlight";
			
		}
		
		@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
		private String addFlightSubmit(@ModelAttribute FlightModel flight) {
			flightService.addFlight(flight);
			return "add";
		}
		
		/**
		 * Delete Flight by ID
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/flight/delete/{id}", method = RequestMethod.POST)
		private String deleteFlight(@PathVariable(value = "id") Long id, Model model) {
			FlightModel flight = flightService.getFlightDetailById(id);
			flightService.deleteFlight(flight);
			model.addAttribute("flight", flight);
			return "delete";
		}
		
		
		/**
		 * Update Flight
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
		private String updateFlight(@PathVariable(value = "id") Long id, Model model) {
			FlightModel flight = flightService.getFlightDetailById(id);
			if(flight != null) {
				model.addAttribute("flight", flight);
				return "update-flight";
			} else {
				return "not-found";
			}
			
		}
		
		
		/**
		 * Update Flight When Submit
		 * @param flight
		 * @return
		 */
		@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
		private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
			FlightModel flightArchive = flightService.getFlightDetailById(flight.getId());
			
			flightArchive.setFlightNumber(flight.getFlightNumber());
			flightArchive.setDestination(flight.getDestination());
			flightArchive.setOrigin(flight.getOrigin());
			flightArchive.setTime(flight.getTime());
			
			flightService.addFlight(flightArchive);
			
			return "update-submit";
		}
		
		/**
		 * View All Flight
		 * @param model
		 * @return
		 */
		@RequestMapping("/flight/viewall")
		public String viewAllFlight(Model model) {
			List<FlightModel> flight = flightService.getFlightList();
			model.addAttribute("flightList", flight);
			return "view-all-flight";
		}
		
		
	}
