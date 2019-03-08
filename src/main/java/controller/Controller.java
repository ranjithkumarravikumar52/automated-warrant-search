package controller;

import model.Guest;
import model.GuestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;

public class Controller {
	private Service<GuestDTO> service;
	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	public Controller(Service<GuestDTO> service) {
		log.info("Inside controller constructor");
		this.service = service;
	}

	public boolean checkWarrant(Guest guest){
		return service.matchFound(guest);
	}
}
