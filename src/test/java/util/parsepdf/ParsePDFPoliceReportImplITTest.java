package util.parsepdf;

import client.main.App;
import model.Guest;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//TODO incorrect last name format. will be resolved when the pdf file is corrected in the actual software
public class ParsePDFPoliceReportImplITTest {
	private ParsePDFPoliceReportImpl guestParsePDF;

	@Before
	public void setUp() throws Exception {
		guestParsePDF = new ParsePDFPoliceReportImpl(App.POLICE_REPORT_PDF);
	}

	/**
	 * Read is a high level operation which returns a List of Guest
	 */
	@Test
	public void readPDFFile_CheckIfListContainsGuest() {
		Set<Guest> guestList = guestParsePDF.readPDFFile();
		assertNotNull(guestList);
		assertTrue(guestList.size() > 0);
		assertTrue(guestList.size() <= 120);
	}
}
