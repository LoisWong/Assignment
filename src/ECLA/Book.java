package ECLA;

public class Book {
	String title;
	Date lenddate = new Date();
	long ISBN;
	
	public Book(){}
	public Book(String b_name, int l_d, int l_m, int l_y, long ISBN){
		
		title = b_name;
		lenddate.day = l_d;
		lenddate.month = l_m;
		lenddate.year = l_y;
		this.ISBN = ISBN;
	}
	
	public String getBook(){
		String format;
		format = title + "," +lenddate.day + "-" + lenddate.month + "-" + lenddate.year + "," + ISBN;
		return format;
	}
	
}
