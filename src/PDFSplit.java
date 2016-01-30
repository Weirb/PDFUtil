import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFSplit {

	/*
	 * IMPROVEMENTS
	 * Add method to handle input of ranges of pages
	 * e.g. 2-5 7-9 would expand to 2 3 4 5 7 8 9
	 */

	/*
	 * String input: the input file name containing all of the pages, some
	 * of which we want to extract
	 * String output: the output file name
	 * String[] p: array of page numbers, raw input from
	 */
	public static void split(String input, String output, String[] p) {

		// Load the input document and grab the pages
		PDDocument document;
		try {
			document = PDDocument.load(input);
		} catch (IOException e) {
			System.out.println("Could not load the input file.");
			e.printStackTrace();
			return;
		}

		/*
		 * Convert our input from an array of strings to an
		 * array of ints.
		 * The human readable page numbers of a pdf file are indexed
		 * from 1-n, Java indexes from 0, so we subtract one from each
		 * value of the input
		 */
		int[] pages = new int[p.length];
		for (int i = 0; i < p.length; i++) {
			pages[i] = Integer.parseInt(p[i]) - 1;
		}

		// Check to see we are not trying to merge pages that do not
		// exist
		int d = document.getNumberOfPages();
		for (int i = 0; i < pages.length; i++) {
			if (pages[i] > d) {
				System.out.println("Page numbers out of range.");
				return;
			}
		}

		// Return a list of all of the pages, so that they can be
		// selected
		List cat = document.getDocumentCatalog().getAllPages();

		// Parse input and output for correct file ending
		if (!input.endsWith(".pdf")) {
			input.concat(".pdf");
		}
		if (!output.endsWith(".pdf")) {
			output.concat(".pdf");
		}

		// Create a new PDDocument object and add the pages we want from
		// our input.
		PDDocument outputDocument = new PDDocument();
		for (int i : pages) {
			outputDocument.addPage((PDPage) cat.get(i));
		}

		// Save and close the files
		try {
			outputDocument.save(output);
			outputDocument.close();
			document.close();
		} catch (COSVisitorException | IOException e) {
			System.out.println("Could not cleanup on exit.");
			e.printStackTrace();
			return;
		}

	}

}

