package client.main;

import model.Guest;
import util.parsepdf.ParsePDF;
import util.parsepdf.ParsePDFPoliceReportImpl;

import java.util.Set;

public class App {

    //TODO relative path
    public static final String GUEST_LIST_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\Guest List.pdf";
    public static final String POLICE_REPORT_PDF = "C:\\Projects\\automated-warrant-search\\src\\main\\resources\\policereport.pdf";

    public static void main(String[] args) {
        ParsePDF<Guest> parsePDFPoliceReportImpl = new ParsePDFPoliceReportImpl(POLICE_REPORT_PDF);
	    Set<Guest> guestList = parsePDFPoliceReportImpl.readPDFFile();
        /*for(Guest guest: guestList){
            System.out.println(guest);
        }*/
    }
}
