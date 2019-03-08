package client.main;

import controller.Controller;
import dao.DAO;
import dao.DAOImpl;
import model.Guest;
import model.GuestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;
import service.ServiceImpl;
import util.parsepdf.ParsePDF;
import util.parsepdf.ParsePDFPoliceReportImpl;

import java.util.Set;

public class App {

	//TODO relative path
	public static final String GUEST_LIST_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\Guest List.pdf";
	public static final String POLICE_REPORT_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\policereport.pdf";
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		DAO<GuestDTO> dao = new DAOImpl();
		Service<GuestDTO> service = new ServiceImpl(dao);
		Controller controller = new Controller(service);

		ParsePDF<Guest> parsePDFPoliceReportImpl = new ParsePDFPoliceReportImpl(POLICE_REPORT_PDF);
		Set<Guest> guestList = parsePDFPoliceReportImpl.readPDFFile();

		for(Guest guest: guestList){
			log.info("Checking..."+guest.getRoomNumber());
			System.out.println(controller.checkWarrant(guest));
		}

	}

}
