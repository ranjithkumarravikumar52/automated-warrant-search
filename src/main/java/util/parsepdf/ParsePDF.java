package util.parsepdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;

public class ParsePDF {

    private String nameOfTheFile;

    public ParsePDF(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }

    /**
     * This method is from a blog https://www.mkyong.com/java/pdfbox-how-to-read-pdf-file-in-java/ <br>
     * Uses dependency PDFBox
     *
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
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    System.out.println(line);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameOfTheFile() {
        return nameOfTheFile;
    }

    public void setNameOfTheFile(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }
}
