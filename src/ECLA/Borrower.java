package ECLA;

import java.util.ArrayList;

public class Borrower {
	String name;
	String email, address;
	Date birthday = new Date();
	int phone;
	ArrayList<Book> booklist = new ArrayList<Book>();

	public Borrower(){}
	public Borrower(String nm, int b_d, int b_m, int b_y, int phone, String em, String add){
		name = nm;
		email = em;
		address = add;
		birthday.day = b_d;
		birthday.month = b_m;
		birthday.year = b_y;
	}
	
	public void addBook(String b_name, int l_d, int l_m, int l_y, int isbn){
		booklist.add(new Book(b_name,l_d, l_m, l_y, isbn));
	}
	
	public void setName(String nm){name = nm;}
	public String getName(){return this.name;}
	
	public void setEmail(String em){email = em;}
	public String getEmail(){return this.email;}
	
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
		return formedBD;}
}
