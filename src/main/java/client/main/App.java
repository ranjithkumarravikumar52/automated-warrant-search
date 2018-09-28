package client.main;

import util.parsepdf.ParsePDF;

public class App {

    static final String NAME_OF_THE_FILE = "C:\\Projects\\client\\src\\main\\resources\\policereport.pdf";

    public static void main(String[] args) {
        ParsePDF parsePDF = new ParsePDF(NAME_OF_THE_FILE);
        parsePDF.readPDFFile();
    }
}
