import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		int argLen = args.length;

		if (argLen == 0) {
			message();
			return;
		}

		if (argLen == 1) {
			System.out.println("Incorrect input format.");
			message();
			return;
		}

		switch (args[0]) {
			case "-m":
			case "--merge":
				
				PDFMerge.merge(args[1], Arrays.copyOfRange(args, 2, args.length));
				break;

			case "-s":
			case "--split":
				
				if (argLen < 4) {
					System.out.println("Incorrect input format.");
					message();
					break;
				}
				
				PDFSplit.split(args[2], args[1], Arrays.copyOfRange(args, 3, args.length));
				break;

			default:
				message();
				break;
		}

	}
	
	/*
	 * Usage information 
	 */
	public static void message() {
		System.out.println("Merge pdf files into a single document, or split a pdf file into pages and return the merged result.");
		System.out.println();
		System.out.println("Usage:");
		System.out.println("  pdf (-m | --merge) <output> [input...]");
		System.out.println("  pdf (-s | --split) <output> <input> <pages>...");
		System.out.println();
		System.out.println("Options:");
		System.out.println("  output  Destination file");
		System.out.println("  input   Input file(s)");
		System.out.println("          If input is blank, merge pdf files in directory");
		System.out.println("  pages   List of page numbers [1-n] separated by spaces");
	}
}
