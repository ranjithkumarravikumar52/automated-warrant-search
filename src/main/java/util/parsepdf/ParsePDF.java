package util.parsepdf;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

/**
 * OCP: If we need to add some additional logic where the parsing rules have changed for the pdf file
 *
 * @param <T>
 */
public interface ParsePDF<T> {
	String[] stripPDFFile(PDDocument document);

	List<T> readPDFFile();

	T parseEachLine(String line);

	T cleanTheLine(String line);
}
