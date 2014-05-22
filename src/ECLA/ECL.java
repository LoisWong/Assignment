package ECLA;

import java.util.ArrayList;

/*
 * This class is the entry point for the COMP 5214 assignment.
 */
public class ECL {
	public static void main(String[] args) {
		// EDIT THE MAIN METHOD AS NEEDED FOR YOUR ASSIGNMENT
		// args[0] is the path to borrower file
		// args[1] is the path to the instruction file
		// args[2] is the path to the output file
		// args[3] is the path to the report file
		try {
			ArrayList<Borrower> borrower = new ArrayList<Borrower>();
			Logging logging = new Logging();

			borrower = logging.read(args[0]);
			borrower = logging.removeInVData(borrower);

			Instruction instruction = new Instruction(borrower, args[2],
					args[3]);
			borrower = instruction.read(args[1]);

		} catch (Exception e) {
			System.out.println("");
		}
	}
}