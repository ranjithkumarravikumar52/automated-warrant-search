package util.parsepdf;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParsePDFTest {

    @Test
    public void isValidPersonNameTest(){
        String input = "ARCHER-SINGLETON";
        assertTrue(new ParsePDF(null).isValidPersonName(input));
        input = "DR.";
        assertTrue(new ParsePDF(null).isValidPersonName(input));
        input = "abc";
        assertFalse(new ParsePDF(null).isValidPersonName(input));
    }

    @Test
    public void isLineValidTest(){
        String rawLine = "9/06/18 Southern Comfort Inn";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = " 7:58PM 4419 Tuckaseegee Road";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = "Charlotte, NC 28208";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = "Current Guest list as of: 9/06/18    7:58PM";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = "   1Page:";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = "Room# Name          NightsCust# Balance TagChek-outChek-in";
        assertFalse(new ParsePDF(null).isLineValid(rawLine));

        rawLine = "10/03/18 101 JACKSON, ABRIONA HUDSON  8138  2/02/18 243     741.00";
        assertTrue(new ParsePDF(null).isLineValid(rawLine));


    }
}