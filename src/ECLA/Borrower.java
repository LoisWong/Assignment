package ECLA;

import java.io.IOException;
import java.util.ArrayList;

public class Borrower {
	String name = "none";
	String email, address;
	Date birthday = new Date();
	int phone;
	ArrayList<Book> booklist = new ArrayList<Book>();

	public Borrower(){}
	
	public Borrower(String report) throws IOException{
		
	}
	public void addBook(String b_name, int l_d, int l_m, int l_y, String isbn){
		this.booklist.add(new Book(b_name,l_d, l_m, l_y, isbn));
	}
	public String getBook(){
		String Booklist = null;
		for (Book b : booklist){
			Booklist += b.getBook() + "\n";
		}
		return Booklist;
	}
	
	public void setName(String nm){name = nm;}
	public String getName(){return this.name;}
	
	public void setEmail(String em){email = em;}
	public String getEmail(){return this.email;}
	
	public void setPhone(int phone){this.phone = phone;}
	public int getPhone(){return this.phone;}
	
	public void setAddress(String add){address = add;}
	public String getAddress(){return this.address;}
	
	public void setBirthday(int b_d, int b_m, int b_y){
		birthday.day = b_d;
		birthday.month = b_m;
		birthday.year = b_y;
	}
	public String getBirthday(){
		String formedBD;
		formedBD = birthday.day + "-" + birthday.month + "-" + birthday.year; 
		return formedBD;
	}
	
	public String toString(){
		String result;
		result = "name         "+this.name + System.getProperty("line.separator");
		result +="birthday     "+ this.getBirthday() +System.getProperty("line.separator");
		if (this.phone != 0){
			result +="phone        " + this.phone + System.getProperty("line.separator");
		}
		if (this.email != null){
			result +="email        "+this.email+System.getProperty("line.separator");
		}
		if (this.address!=null){
			result +="address      " + this.address +System.getProperty("line.separator");
		}
		if (this.booklist.size() != 0){
			result +="booklist     " + this.getBook();
		}
		return result;
	}
}
