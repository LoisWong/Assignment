package ECLA;
/*@Project: ${Electronic_Community_Library_Management}
 *@Author: LuWang 
 *@Date: ${22/05/2014} 
 */

public class Book extends Date{
	String title;
	Date lenddate = new Date();
	long ISBN;

	public Book() {
	}

	public Book(String b_name, int l_d, int l_m, int l_y, long ISBN) {

		title = b_name;
		if (l_d <= 31 && l_m <= 12 && l_y < 2015) {
			lenddate.day = l_d;
			lenddate.month = l_m;
			lenddate.year = l_y;
		} else {
			lenddate.day = 0;
			lenddate.month = 0;
			lenddate.year = 0;
		}
		this.ISBN = ISBN;
	}

	public String getBook() {
		//Output the book's information in the format: title, dd-mm-yyyy, ISBN
		String format;
		format = title + "," + String.format("%02d", lenddate.day) + "-"
				+ String.format("%02d", lenddate.month) + "-"
				+ String.format("%04d", lenddate.year) + "," + ISBN;
		return format;
	}

}
