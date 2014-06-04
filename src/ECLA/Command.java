package ECLA;
/*@Project: ${Electronic_Community_Library_Management}
 *@Author: LuWang 
 *@Date: ${22/05/2014} 
 */

import java.io.IOException;
import java.util.ArrayList;

public interface Command {
	//There are two read commands in this project need to be done. One for borrowers' file, the other for instruction 
	public ArrayList<Borrower> read(String filename) throws IOException;
}
