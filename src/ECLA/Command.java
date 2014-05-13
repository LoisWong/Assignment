package ECLA;

import java.io.IOException;
import java.util.ArrayList;

public interface Command {
	public ArrayList<Borrower> read(String filename) throws IOException;
	//public void write(ArrayList<Borrower> b, String output) throws IOException;
}
