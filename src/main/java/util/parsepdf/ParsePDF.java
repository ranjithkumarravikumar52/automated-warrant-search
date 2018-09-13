package util.parsepdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParsePDF {

    private String nameOfTheFile;

    public ParsePDF(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }

    /**
     * This method is from a blog https://www.mkyong.com/java/pdfbox-how-to-read-pdf-file-in-java/ <br>
     * Uses dependency PDFBox
     */
    public void readPDFFile() {
        try (PDDocument document = PDDocument.load(new File(nameOfTheFile))) {

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

                // split by whitespace
//                String whiteSpaceRegExp = "\\r?\\n";
                String whiteSpaceRegExp = "\n";
                String lines[] = pdfFileInText.split(whiteSpaceRegExp);
                for (String line : lines) {
                    if (line != null && !line.isEmpty()) {
                        parseEachLine(line);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * for each line, group them into guest room#, last Name and first Name
     *
     * @param line each line in the pdf file
     */
    private void parseEachLine(String line) {
        if(isLineValid(line)){
            System.out.println(line);
        }
    }

    private boolean isTokenEmpty(String token) {
        return token.isEmpty();
    }

    private boolean isLineValid(String line) {
        String[] split = line.split(" ");
        boolean isRoomNumberFound = false;
        boolean isPersonNameFound = false;
        for (int i = 0; i < split.length; i++) {
            if (!isTokenEmpty(split[i])) {
                if (isValidRoomNumber(split[i]) && !isRoomNumberFound) {
//                    System.out.println(i +": Room No -> " + split[i]);
                    isRoomNumberFound = true;
                }
                else if(isValidPersonName(split[i])){
//                    System.out.println(i+": Valid Name -> " + split[i]);
                    isPersonNameFound = true;
                }
            }
        }
        return isRoomNumberFound && isPersonNameFound;
    }


    private boolean isValidPersonName(String s) {
        if (s.contains(",")) {
            s = s.replaceAll(",", "");
        }
        String regularExpression = "[A-Z]+";
        return s.matches(regularExpression);
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

    private boolean isDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = simpleDateFormat.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public String getNameOfTheFile() {
        return nameOfTheFile;
    }

    public void setNameOfTheFile(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }
}
