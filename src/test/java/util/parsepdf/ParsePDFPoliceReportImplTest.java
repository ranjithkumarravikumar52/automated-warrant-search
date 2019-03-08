package util.parsepdf;

import client.main.App;
import model.Guest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * parseEachLine = isLineValid -> cleanTheLine
 * <p>
 * isLineValid = isValidRoomNumber && isValidPersonName
 */
public class ParsePDFPoliceReportImplTest {
	//TODO spring Dependency Injection
	private ParsePDFPoliceReportImpl guestParsePDF;

	@Before
	public void setUp() throws Exception {
		guestParsePDF = new ParsePDFPoliceReportImpl(App.POLICE_REPORT_PDF);
	}

	/**
	 * PDF file is initially loaded into main-memory, gets a stream from it and accesses the PDF file.
	 * If the file doesn't contain any password, then it should successfully open and read through the PDF file
	 */
	@Test
	public void stripPDFFile_checkIfAFileCanBeLoaded() {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(App.POLICE_REPORT_PDF));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(document);
	}

	@Test(expected = IOException.class)
	public void stripPDFFile_checkIfAFileCanBeLoaded_NegativeCase() throws IOException {
		PDDocument document = PDDocument.load(new File(""));
	}

	@Test
	public void parseEachLine_PositiveTestCase() {
		Guest guest = guestParsePDF.parseEachLine("10/03/18 101 JACKSON, ABRIONA HUDSON  8138  2/02/18 243 06/01/2019 27251730");
		assertEquals(guest.getFirstName(), "ABRIONA");
		assertEquals(guest.getLastName(), "JACKSON");
		assertEquals(guest.getMiddleName(), "HUDSON");
		assertEquals(guest.getRoomNumber(), 101);
		assertEquals(guest.getDob(), "06/01/2019");
	}

	@Test
	public void parseEachLine_FailCondition() {
		assertNull(guestParsePDF.parseEachLine("   1Page:"));
	}

	@Test
	public void cleanTheLine() {
		Guest guest = guestParsePDF.cleanTheLine("10/03/18 101 JACKSON, ABRIONA HUDSON  8138  2/02/18 243 06/01/2019 27251730");
		assertEquals(guest.getFirstName(), "ABRIONA");
		assertEquals(guest.getLastName(), "JACKSON");
		assertEquals(guest.getMiddleName(), "HUDSON");
		assertEquals(guest.getRoomNumber(), 101);
		assertEquals(guest.getDob(), "06/01/2019");
	}

	@Test
	public void isLineValid() {
		assertTrue(guestParsePDF.isLineValid("10/03/18 101 JACKSON, ABRIONA HUDSON  8138  2/02/18 243 06/01/2019 27251730"));
		assertFalse(guestParsePDF.isLineValid("11/02/1976 000008954536CHRISTOPHER HALL"));
		assertFalse(guestParsePDF.isLineValid("Room# Name          NightsCust# D.O.B. ID Tag Car ColorMakeChek-outChek-in"));
		assertFalse(guestParsePDF.isLineValid("6:57PM 4419 Tuckaseegee Road"));
		assertFalse(guestParsePDF.isLineValid("Current Guest list as of: 9/26/18    6:57PM"));
	}

}