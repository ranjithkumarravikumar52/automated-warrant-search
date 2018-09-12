package automatedwarrantsearch.main;

import util.parsepdf.ParsePDF;

public class App {
    public static void main(String[] args) {
        ParsePDF parsePDF = new ParsePDF("C:\\Projects\\automatedwarrantsearch\\src\\main\\resources\\Guest List.pdf");
        parsePDF.readPDFFile();
    }
}
