package client.main;

import util.parsepdf.ParsePDF;

public class App {

    //TODO relative path
    static final String GUEST_LIST_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\Guest List.pdf";
    static final String POLICE_REPORT_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\policereport.pdf";

    public static void main(String[] args) {
        ParsePDF parsePDF = new ParsePDF(POLICE_REPORT_PDF);
        parsePDF.readPDFFile();
    }
}
