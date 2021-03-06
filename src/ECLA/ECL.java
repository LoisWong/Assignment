package ECLA;
/*@Project: ${Electronic_Community_Library_Management}
 *@Author: LuWang 
 *@Date: ${22/05/2014} 
 */

import java.util.ArrayList;

/*
 * This class is the entry point for the COMP 5214 assignment.
 */
public class ECL {
	static ArrayList<Borrower> borrower;
	static Logging logging;
	static Instruction instruction;
	
	public static void main(String[] args) {
		// EDIT THE MAIN METHOD AS NEEDED FOR YOUR ASSIGNMENT
		// args[0] is the path to borrower file
		// args[1] is the path to the instruction file
		// args[2] is the path to the output file
		// args[3] is the path to the report file
		try {
			borrower = new ArrayList<Borrower>();
			logging = new Logging();

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