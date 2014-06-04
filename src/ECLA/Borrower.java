package ECLA;
/*@Project: ${Electronic_Community_Library_Management}
 *@Author: LuWang 
 *@Date: ${22/05/2014} 
 */

import java.io.IOException;
import java.util.ArrayList;

public class Borrower extends Date{
	String name = "none";
	String email, address;
	Date birthday = new Date();
	int phone;
	ArrayList<Book> booklist = new ArrayList<Book>();

	public Borrower() {
	}

	public Borrower(String report) throws IOException {

	}

	public void addBook(String b_name, int l_d, int l_m, int l_y, long isbn) {
		this.booklist.add(new Book(b_name, l_d, l_m, l_y, isbn));
	}

	public String getBook() {
		//Output the book list of the borrower
		String Booklist = "";
		for (Book b : booklist) {
			Booklist += b.getBook() + "\n" + "                    ";
		}
		return Booklist;
	}

	public void setName(String nm) {
		//Set borrower's name
		name = nm;
	}

	public String getName() {
		//Get borrower's name
		return this.name;
	}

	public void setEmail(String em) {
		//Set borrower's email address
		email = em;
	}

	public String getEmail() {
		//Get borrower's email address
		return this.email;
	}

	public void setPhone(int phone) {
		//Set borrower's phone number
		this.phone = phone;
	}

	public int getPhone() {
		//Get borrower's phone number
		return this.phone;
	}

	public void setAddress(String add) {
		//Set borrower's address
		address = add;
	}

	public String getAddress() {
		//Get borrower's address
		return this.address;
	}

	public void setBirthday(int b_d, int b_m, int b_y) {
		//Set borrower's birthday. If the date is incorrect, then set all as 0 in order to be checked later. 
		if (b_d <= 31 && b_m <= 12 && b_y < 2015) {
			if (b_m == 2 && b_d > 28){
			birthday.day = 0;
			birthday.month = 0;
			birthday.year = 0;
			}
			else{
			birthday.day = b_d;
			birthday.month = b_m;
			birthday.year = b_y;
			}
		} else {
			birthday.day = 0;
			birthday.month = 0;
			birthday.year = 0;
		}
	}

	public String getBirthday() {
		//Get borrower's birthday and return as the format:dd-mm-yyyy
		String formedBD;
		formedBD = String.format("%02d", birthday.day) + "-"
				+ String.format("%02d", birthday.month) + "-"
				+ String.format("%04d", birthday.year);
		return formedBD;
	}

	public String toString() {
		//Get borrower's fully information as the format required.
		String result;
		result = "name                " + this.name
				+ System.getProperty("line.separator");
		result += "birthday            " + this.getBirthday()
				+ System.getProperty("line.separator");
		if (this.phone != 0) {
			result += "phone               " + this.phone
					+ System.getProperty("line.separator");
		}
		if (this.email != null) {
			result += "email               " + this.email
					+ System.getProperty("line.separator");
		}
		if (this.address != null) {
			result += "address             " + this.address
					+ System.getProperty("line.separator");
		}
		if (this.booklist.size() != 0) {
			result += "booklist            " + this.getBook();
		}
		return result;
	}
}
