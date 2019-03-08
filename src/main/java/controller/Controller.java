package controller;

import model.GuestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;

import java.util.Set;

public class Controller {
	private Service<GuestDTO> service;
	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	public Controller(Service<GuestDTO> service) {
		log.info("Inside controller constructor");
		this.service = service;
	}

	public Set<GuestDTO> getDataFromAPI(String firstName, String lastName){
		return service.getDataFromTheAPI(firstName, lastName);
	}
}
