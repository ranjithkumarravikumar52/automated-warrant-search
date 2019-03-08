package util.parsepdf;

import model.Guest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParsePDF {

    private String nameOfTheFile;
    private List<Guest> guestList = new ArrayList<>();

    public ParsePDF(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }

    /**
     * Stripping the pdf document based on the delimiters like new line, white space etc. <br>
     * Change the delimiter in the string variable whiteSpaceRegExp in future if you have to deal with any other pdf files<br>
     *
     * @param document - PDDocument object which has read a raw pdf file
     * @return parsed text format of pdf file in the form of array of string
     * @throws IOException
     */
    private String[] stripPDFFile(PDDocument document) throws IOException {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        PDFTextStripper tStripper = new PDFTextStripper();
        String pdfFileInText = tStripper.getText(document);
        String whiteSpaceRegExp = "\n";
        return pdfFileInText.split(whiteSpaceRegExp);
    }

    /**
     * This method is from a blog https://www.mkyong.com/java/pdfbox-how-to-read-pdf-file-in-java/ <br>
     * Uses dependency PDFBox, check pom.xml for reference<br>
     * This method parses the pdf file based on the delimiters given in the stripPDFFile method <br>
     */
    public void readPDFFile() {
        try (PDDocument document = PDDocument.load(new File(nameOfTheFile))) {
            if (!document.isEncrypted()) {
                String[] lines = stripPDFFile(document);
                for (String line : lines) {
                    if (line != null && !line.isEmpty()) {
                        parseEachLine(line);
                    }
                }
                printGuestList(guestList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @param line each line in the pdf file
     */
    private void parseEachLine(String line) {
        if (isLineValid(line)) {
           guestList.add(cleanTheLine(line));
        }
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
    private Guest cleanTheLine(String line) {

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
            if(s.contains(",") && !isLastNameFound){
                s = s.replaceAll(",", "");
                if (isValidPersonName(s) ) {
                    lastName = s;
                    isLastNameFound = true;
                }
                continue;
            }

            //3. print information containing only firstName
            if(isValidPersonName(s) && !isFirstNameFound){
                firstName = s;
                isFirstNameFound = true;
                continue;
            }

            //4. print information containing only middleName - optional hence using "if" not "else if"
            if(isValidPersonName(s) && !isMiddleNameFound){
                middleName = s;
                isMiddleNameFound = true;
                continue;
            }

            //5. print information containing only DOB
            if(isValidDate(s) && !isDOBFound ){
                if(dobCount == 2){
                    dob = s;
                    isDOBFound = true;
                }
                dobCount++;
            }

        }

        // return new Guest(firstName, lastName, middleName, roomNumber, dob);
        Guest guest = Guest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .roomNumber(roomNumber)
                .dob(dob)
                .build();
        return guest;
    }


    /**
     * For a line to be valid for this project, the line parsed from raw data should contain both room number and a person name. <br>
     * In other words, this method removes all the unnecessary header and footers and gives us the information about the guest and ONLY about the guests
     * If either of the requirements fail, return false.
     *
     * @param line A line from the parsed PDF file
     * @return true or false based on the requirements
     */
    public boolean isLineValid(String line) {
        String[] split = line.split(" ");
        boolean isRoomNumberFound = false;
        boolean isPersonNameFound = false;
        for (String token : split) {
            if (!isEmptyToken(token)) {
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
    public boolean isValidPersonName(String s) {
        String regularExpression = "[A-Z-.]+";
        return s.matches(regularExpression);
    }

    private boolean isEmptyToken(String token) {
        return token.isEmpty();
    }

    private boolean isValidRoomNumber(String s) {
        String regularExpression = "\\d{3}";
        return s.matches(regularExpression) && isValidRoomNumberRange(s);
    }

    private boolean isValidRoomNumberRange(String s) {
        int i = Integer.parseInt(s);
        return (((i >= 101) && (i <= 160)) || (i >= 200 && (i <= 260)));
    }

    private boolean isValidNumber(String s) {
        try {
            double v = Double.parseDouble(s);
            return !Double.isNaN(v);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = simpleDateFormat.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void printList(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("***END OF LINE***");
    }

    private void printGuestList(List<Guest> list) {
        for (Guest guest : list) {
            System.out.println(guest);
        }
        System.out.println("***END OF LINE***");
    }

}
