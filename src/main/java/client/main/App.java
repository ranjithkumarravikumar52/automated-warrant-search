package client.main;

import util.parsepdf.ParsePDF;

public class App {

    //TODO relative path
    static final String NAME_OF_THE_FILE = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\Guest List.pdf";

    public static void main(String[] args) {
        ParsePDF parsePDF = new ParsePDF(NAME_OF_THE_FILE);
        parsePDF.readPDFFile();
    }
}
