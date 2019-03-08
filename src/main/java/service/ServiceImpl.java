package service;

import dao.DAO;
import dao.DAOImpl;
import model.Guest;
import model.GuestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ServiceImpl implements Service<GuestDTO> {
	private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);

	private DAO<GuestDTO> dao;

	public ServiceImpl() {
	}

	public ServiceImpl(DAO dao) {
		log.info("Inside service impl constructor");
		this.dao = new DAOImpl();
	}

	@Override
	public boolean matchFound(Guest guest) {
		Set<GuestDTO> dataFromAPI = getDataFromAPI(guest);

		//we need a unique name from all the records
		for (GuestDTO guestDTO : dataFromAPI) {
			// Split first name and last name from the DTO object
			String[] strings = guestDTO.getName().split(",");
			String dtoLastName = strings[0].trim();
			String dtoFirstName = strings[1].trim();
			String dtoDateOfBirthFormatted = guestDTO.getDateOfBirthFormatted().trim();

			// Match DTO's first name and last name with our Guest object
			// Match DTO's dob with Guest POJO's dob
			// If both matches then HIT else FAIL
			boolean isFirstNameEqual = guest.getFirstName().equals(dtoFirstName);
			boolean isLastNameEqual = guest.getLastName().equals(dtoLastName);
			boolean isDateOfBirthEqual = guest.getDob().equals(dtoDateOfBirthFormatted);

			log.info("Are first names equal? "+guest.getFirstName()+" vs "+dtoFirstName);
			log.info("Are last names equal? "+guest.getLastName()+" vs "+dtoLastName);
			log.info("Are dobs equal? "+guest.getDob()+" vs "+dtoDateOfBirthFormatted);

			if(isFirstNameEqual && isLastNameEqual && isDateOfBirthEqual){
				return true;
			}
		}
		return false;
	}


	private Set<GuestDTO> getDataFromAPI(Guest guest) {
		log.info("Inside the service impl method to get data from API");
		//Please sleep to avoid server overload
		try {
			log.info("Initiating sleep for 15 seconds. Please wait....");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<GuestDTO> dataFromTheAPI = dao.getDataFromTheAPI(guest.getFirstName(), guest.getLastName());
		return dataFromTheAPI;
	}


}
