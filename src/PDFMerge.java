import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

public class PDFMerge {

	public static void merge(String destination, String[] files) {

		// Ensure we create the correct file extension
		if (!destination.endsWith(".pdf")) {
			destination.concat(".pdf");
		}

		/*
		 * If a list of files to merge have not been specified, scrape
		 * the current directory for PDF files and merge those based on
		 * the AlphaNumeric sort
		 */
		if (files.length == 0) {

			File fileFolder = new File(System.getProperty("user.dir"));
			File[] fileList = fileFolder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {

					if (pathname.getName().endsWith(".pdf")) {
						return true;
					} else {
						return false;
					}
				}
			});

			// Add the file names from the directory to a new array
			files = new String[fileList.length];
			for (int i = 0; i < files.length; i++) {
				files[i] = fileList[i].getName();
			}

			// Sort the PDF file names based on a AlphaNumeric sort
			// ** Note here that it is assumed that if the user has
			// entered the list of files themselves, the order is
			// intentional. Therefore the sorting shall only be done
			// if there is no reason to believe otherwise.
			Arrays.sort(files, new AlphanumComparator());
		}

		// Create a PDFMergerUtility object to perform the merge
		PDFMergerUtility merger = new PDFMergerUtility();

		// Set the output file name of the merged document
		merger.setDestinationFileName(destination);

		// Loop through the names of the files and add them to the
		// PDFMergerUtility object
		for (String s : files) {
			merger.addSource(s);
		}

		// Merge the documents
		try {
			merger.mergeDocuments();
		} catch (COSVisitorException | IOException e) {
			System.out.println("Could not merge documents.");
			e.printStackTrace();
		}
	}

}
