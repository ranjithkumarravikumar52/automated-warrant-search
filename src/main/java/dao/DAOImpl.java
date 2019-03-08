package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class DAOImpl<GuestDTO> implements DAO<GuestDTO> {

	private static final Logger log = LoggerFactory.getLogger(DAOImpl.class);
	private static final String APILink = "https://mecksheriffweb.mecklenburgcountync.gov/Warrant/_Search?";

	public DAOImpl() {
		log.info("Inside DAO impl constructor");
	}

	@Override
	public Set<GuestDTO> getDataFromTheAPI(String firstName, String lastName) {
		log.info("Inside the dao impl method");

		ObjectMapper mapper = new ObjectMapper();
		Set<GuestDTO> myObjects = null;

		//appending firstName and lastName to our API end point to form a GET request
		String spec = APILink + "FirstName=" + firstName + "&LastName=" + lastName;

		try {
			log.info(spec+" connecting");
			myObjects = mapper.readValue(new URL(spec),new TypeReference<Set<GuestDTO>>() {});
			log.info(spec+"....connection successful");

		} catch (IOException e) {
			log.error(spec+" connection failed");
			e.printStackTrace();
		}

		return myObjects;
	}
}
