package util.parsepdf;

import model.Guest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class performs the following operations
 * <p>
 * connects/opens a pdf file
 * reads pdf file
 * lots of helper methods
 * <p>
 * Let's try to check for methods violating S rule or think of the ways of how difficult it is to test a module
 */
public class ParsePDFPoliceReportImpl implements LineValidity, ParsePDF<Guest> {

	/**
	 * application logger
	 **/
	private static final Logger LOG = LoggerFactory.getLogger(ParsePDFPoliceReportImpl.class);
	private String nameOfTheFile;
	private static Set<Integer> excludeRoomNumberSet = new HashSet<>();

	static {
		excludeRoomNumberSet.add(114);
		excludeRoomNumberSet.add(115);
		excludeRoomNumberSet.add(259);
		excludeRoomNumberSet.add(260);
	}

	public ParsePDFPoliceReportImpl(String nameOfTheFile) {
		this.nameOfTheFile = nameOfTheFile;
	}

	/**
	 * Stripping the pdf document based on the delimiters like new line, white space etc. <br>
	 * Change the delimiter in the string variable whiteSpaceRegExp in future if you have to deal with any other pdf files<br>
	 *
	 * @param document - PDDocument object which has to read a raw pdf file
	 * @return parsed text format of pdf file in the form of array of string
	 * @throws IOException when failed to read the PDF file
	 */
	@Override
	public String[] stripPDFFile(PDDocument document) {
		PDFTextStripperByArea stripper = null;
		try {
			stripper = new PDFTextStripperByArea();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stripper.setSortByPosition(true);
		PDFTextStripper tStripper = null;
		try {
			tStripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String pdfFileInText = null;
		try {
			pdfFileInText = tStripper.getText(document);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String whiteSpaceRegExp = "\n";
		return pdfFileInText.split(whiteSpaceRegExp);
	}

	/**
	 * This method is from a blog https://www.mkyong.com/java/pdfbox-how-to-read-pdf-file-in-java/ <br>
	 * Uses dependency PDFBox, check pom.xml for reference<br>
	 * This method parses the pdf file based on the delimiters given in the stripPDFFile method <br>
	 */
	@Override
	public Set<Guest> readPDFFile() {
		Set<Guest> guestList = new LinkedHashSet<>();
		LOG.info("Scanning from pdf file: " + nameOfTheFile);
		try (PDDocument document = PDDocument.load(new File(nameOfTheFile))) {
			if (!document.isEncrypted()) {
				String[] lines = stripPDFFile(document);
				for (String line : lines) {
					if (line != null && !line.isEmpty()) {
						Guest guest = parseEachLine(line);
						if (guest != null && !excludeRoomNumberSet.contains(guest.getRoomNumber())) {
							guestList.add(guest);
						}
					}
				}
			}
		} catch (IOException e) {
			LOG.debug(e.getMessage());
		}
		return guestList;
	}

	/**
	 * for each line, group them into the following required fields: <br>
	 * <ul>
	 * <ol>roomNumber</ol>
	 * <ol>lastName</ol>
	 * <ol>firstName</ol>
	 * <ol>middleName</ol>
	 * <ol>DOB</ol>
	 * </ul>
	 *
	 * This method is a combination of isLineValid followed by cleanTheLine
	 * @param line each line in the pdf file
	 */
	@Override
	public Guest parseEachLine(String line) {
		if (isLineValid(line)) {
			return cleanTheLine(line);
		}
		return null;
	}
	/**
	 * If the code reaches here, then we must be dealing ONLY with the guests info. So do your necessary cleaninq for each line and associate them into an user-model object<br>
	 * Now the simplest way to do about this is to imagine each of these lines as an array and get their associated indexed values on what we require<br>
	 * Now, to do that we could do something like this <br>
	 * if string is room# add it to the array,
	 * if string is a valid name add it to the array (lastName),
	 * if string is a valid name add it to the array (firstName),
	 * if string is a valid name add it to the array (middleName - optional),
	 * if string is a valid DATE, add it to the array (DOB) <br>
	 * <p>
	 * Using boolean flags for each of these requirements, we can maintain the order of parsing <br>
	 */
	@Override
	public Guest cleanTheLine(String line) {

		boolean isRoomNumberFound = false;
		boolean isLastNameFound = false;
		boolean isFirstNameFound = false;
		boolean isMiddleNameFound = false;
		boolean isDOBFound = false;
		int dobCount = 0;

		int roomNumber = 0;
		String lastName = null;
		String firstName = null;
		String middleName = null;
		String dob = null;

		String[] tokens = line.split(" ");

		for (String s : tokens) {
			//1. print information containing only roomNumber
			if (isValidRoomNumber(s) && !isRoomNumberFound) {
				roomNumber = Integer.parseInt(s);
				isRoomNumberFound = true;
				continue;
			}

			//2. print information containing only lastName
			if (s.contains(",") && !isLastNameFound) {
				s = s.replaceAll(",", "");
				if (isValidPersonName(s)) {
					lastName = s;
					isLastNameFound = true;
				}
				continue;
			}

			//3. print information containing only firstName
			if (isValidPersonName(s) && !isFirstNameFound) {
				firstName = s;
				isFirstNameFound = true;
				continue;
			}

			//4. print information containing only middleName - optional hence using "if" not "else if"
			if (isValidPersonName(s) && !isMiddleNameFound) {
				middleName = s;
				isMiddleNameFound = true;
				continue;
			}

			//5. print information containing only DOB report contains check in and check out date which we dont need, hence we are skipping the first 2 dates
			if (isValidDate(s) && !isDOBFound) {
				if (dobCount == 2) {
					dob = s;
					isDOBFound = true;
				}
				dobCount++;
			}

		}

		// return new Guest(firstName, lastName, middleName, roomNumber, dob);
		return Guest.builder()
				.firstName(firstName)
				.lastName(lastName)
				.middleName(middleName)
				.roomNumber(roomNumber)
				.dob(dob)
				.build();
	}

	/**
	 * For a line to be valid for this project, the line parsed from raw data should contain both room number and a person name. <br>
	 * In other words, this method removes all the unnecessary header and footers and gives us the information about the guest and ONLY about the guests
	 * If either of the requirements fail, return false.
	 *
	 * @param line A line from the parsed PDF file
	 * @return true or false based on the requirements
	 */
	@Override
	public boolean isLineValid(String line) {
		String[] split = line.split(" ");
		boolean isRoomNumberFound = false;
		boolean isPersonNameFound = false;
		for (String token : split) {
			if (!token.isEmpty()) {
				if (isValidRoomNumber(token) && !isRoomNumberFound) {
					isRoomNumberFound = true;
				} else if (isValidPersonName(token)) {
					isPersonNameFound = true;
				}
			}
		}
		return isRoomNumberFound && isPersonNameFound;
	}

	/**
	 * This method checks if a given string is a valid person name based on a regular expression.
	 *
	 * @param s - A string from a line
	 * @return true if a string is valid person name, false if its not
	 */
	@Override
	public boolean isValidPersonName(String s) {
		String regularExpression = "[A-Z-.]+";
		return s.matches(regularExpression);
	}

	@Override
	public boolean isValidRoomNumber(String s) {
		String regularExpression = "\\d{3}";
		return s.matches(regularExpression) && isValidRoomNumberRange(s);
	}

	private boolean isValidRoomNumberRange(String s) {
		int i = Integer.parseInt(s);
		return (((i >= 101) && (i <= 160)) || (i >= 200 && (i <= 260)));
	}

	@Override
	public boolean isValidDate(String s) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			simpleDateFormat.parse(s);
			return true;
		} catch (ParseException e) {
			LOG.debug(e.getMessage());
			return false;
		}
	}

}
