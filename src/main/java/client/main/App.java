package client.main;

import model.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.parsepdf.ParsePDF;
import util.parsepdf.ParsePDFPoliceReportImpl;

import java.util.Set;

public class App {

	//TODO relative path
	public static final String GUEST_LIST_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\Guest List.pdf";
	public static final String POLICE_REPORT_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\policereport.pdf";
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		ParsePDF<Guest> parsePDFPoliceReportImpl = new ParsePDFPoliceReportImpl(POLICE_REPORT_PDF);
		Set<Guest> guestList = parsePDFPoliceReportImpl.readPDFFile();
		for (Guest guest:  guestList){
			System.out.println("Room# : "+guest.getRoomNumber());
			System.out.println("First Name: "+guest.getFirstName());
			System.out.println("Last Name: "+guest.getLastName());
			System.out.println();
		}
		/*DAO<GuestDTO> dao = new DAOImpl();
		Service<GuestDTO> service = new ServiceImpl(dao);
		Controller controller = new Controller(service);

		Guest dummyGuest = Guest.builder()
				.dob("9/11/1980")
				.firstName("KARINA")
				.lastName("ISHAN")
				.middleName("")
				.roomNumber(101)
				.build();
		System.out.println(controller.checkWarrant(dummyGuest));*/
	}

}
