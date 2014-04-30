package ECLA;

public class Book {
	String title;
	Date lenddate = new Date();
	int ISBN;
	
	public Book(){}
	public Book(String b_name, int l_d, int l_m, int l_y, int ISBN){
		
		title = b_name;
		lenddate.day = l_d;
		lenddate.month = l_m;
		lenddate.year = l_y;
		this.ISBN = ISBN;
	}
}
