package service;

import dao.DAO;
import dao.DAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ServiceImpl<GuestDTO> implements Service<GuestDTO> {
	private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);

	private DAO<GuestDTO> dao;

	public ServiceImpl() {
	}

	public ServiceImpl(DAO dao) {
		log.info("Inside service impl constructor");
		this.dao = new DAOImpl<>();
	}

	@Override
	public Set<GuestDTO> getDataFromTheAPI(String firstName, String lastName) {
		log.info("Inside the service impl method");
		//Please sleep to avoid server overload
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return dao.getDataFromTheAPI(firstName, lastName);
	}

	@Override
	public boolean matchFound(String firstName, String lastName) {
		return false;
	}
}
