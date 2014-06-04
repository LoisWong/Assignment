package ECLA;
/*@Project: ${Electronic_Community_Library_Management}
 *@Author: LuWang 
 *@Date: ${22/05/2014} 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Logging implements Command {

	private ArrayList<Borrower> recordList = new ArrayList<Borrower>();
	
	public Logging() {
	}

	@Override
	public ArrayList<Borrower> read(String fileName) {

		try {	
			int index = -1;
			File file = new File(fileName);
			Scanner reader = new Scanner(file);
			// System.out.println(file.getCanonicalPath());
			String s;
			Borrower b = new Borrower();
			char locationActive = 0;
			index++;
			recordList.add(b);
			while (reader.hasNextLine()) {
				s = reader.nextLine().trim();
				if (s.equals("")) {
					// There is blank line and create a new borrower
					b = new Borrower();
					recordList.add(b);
					index++;
					locationActive = 0;
				}
				Scanner line = new Scanner(s);
				String cmd;

				if (line.hasNext()) {
					cmd = line.next();
					if (cmd.equalsIgnoreCase("name")) {
						//Get borrower's name, if illegal then set as "none"
						if (line.hasNextLine()) {
							b.setName(Verification.name(line.nextLine()).trim());
						} else {
							b.setName("none");
						}
						recordList.set(index, b);
						locationActive = 0;
					} else if (cmd.equalsIgnoreCase("birthday")) {
						//Get borrower's birthday, if illegal then set as "0"
						if (line.hasNextLine()) {
							String date = line.nextLine().trim();
							Scanner dateScan = new Scanner(date);
							dateScan.useDelimiter("[-\t\n\f\r]");
							try {
								b.setBirthday(dateScan.nextInt(),
										dateScan.nextInt(), dateScan.nextInt());
							} catch (Exception e) {
								b.setBirthday(0, 0, 0);
							}
						} else {
							b.setBirthday(0, 0, 0);
						}
						recordList.set(index, b);
						locationActive = 0;
					} else if (cmd.equalsIgnoreCase("email")) {
						//Get borrower's email, if illegal then ignore(Verification class)
						if (line.hasNextLine()) {
							b.setEmail(Verification.email(line.nextLine()
									.trim()));
							recordList.set(index, b);
						}
						locationActive = 0;
					} else if (cmd.equalsIgnoreCase("phone")) {
						//Get borrower's phone, delete first 0s. If illegal then set as 0(Verification class)
						if (line.hasNextLine()) {
							//String num = line.nextLine().trim();
							String num = line.nextLine().replaceFirst("^0*", "").trim();
//							int i = 0;
//							while (num.charAt(i) == '0') {
//								i++;
//							}
//							num = num.substring(i);
							if (num.length() < 13 && num.length() > 0) {
								b.setPhone(Verification.phone(Integer
										.parseInt(num)));
								recordList.set(index, b);
							}
						}
						locationActive = 0;
					} else if (cmd.equalsIgnoreCase("address")) {
						//Get borrower's address, check illegal and format(Verification class)
						if (line.hasNextLine()) {
							b.setAddress(Verification.address(line.nextLine()
									.trim()));
							recordList.set(index, b);
							locationActive = 1;
							//Mark the next line still could be address
						}
					} else if (cmd.equalsIgnoreCase("booklist")) {
						if (line.hasNext()) {
							String data = line.nextLine().trim();
							String[] temp = data.split("\\s*,\\s*");
							String b_name = null;
							int l_d = 0, l_m = 0, l_y = 0;
							long isbn = 0;
							if (temp.length == 3) {
								for (int i = 0; i < temp.length; i++) {
									String detail = temp[i].trim();
									if (Pattern.matches("^\\d{13}$", detail)) {
										//Find ISBN, which is a 13 digital number
										isbn = Long.parseLong(detail);
									} else if (Pattern.matches(
											"^\\d{1,2}-\\d{1,2}-\\d{4}$",
											detail)) {
										//Find lend date
										Scanner dateScan = new Scanner(detail);
										dateScan.useDelimiter("[-\t\n\f\r]");
										l_d = dateScan.nextInt();
										l_m = dateScan.nextInt();
										l_y = dateScan.nextInt();
									} else
										//Anything else regards as the book title
										b_name = detail;
								}
								b.addBook(b_name, l_d, l_m, l_y, isbn);
								recordList.set(index, b);
							}
						}
						locationActive = 2;
						//Mark the next line still could be book item
					} else if (locationActive == 1) {
						//If there is no filed name and mark equals to 1, then this line is address
						String location = b.getAddress() + " "
								+ Verification.address(s.trim());
						b.setAddress(location);
						recordList.set(index, b);
					} else if (locationActive == 2) {
						//If there is no filed name and mark equals to 2, then this line is book item
						String[] temp = s.split("\\s*,\\s*");
						String b_name = null;
						int l_d = 0, l_m = 0, l_y = 0;
						long isbn = 0;
						for (int i = 0; i < 3; i++) {
							String detail = temp[i].trim();
							if (Pattern.matches("^\\d{13}$", detail)) {
								// if (detail.length() == 13) {
								isbn = Long.parseLong(detail);
							} else if (Pattern.matches(
									"^\\d{1,2}-\\d{1,2}-\\d{4}$", detail)) {
								Scanner dateScan = new Scanner(detail);
								dateScan.useDelimiter("[-\t\n\f\r]");
								l_d = dateScan.nextInt();
								l_m = dateScan.nextInt();
								l_y = dateScan.nextInt();
							} else
								b_name = detail;
						}
						b.addBook(b_name, l_d, l_m, l_y, isbn);
						recordList.set(index, b);
					} else
						System.out.println("Error! Incorrect information: " + s);
				}
			}
			reader.close();
			return recordList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: Cannot read borrowerfile ");
			// File.pathSeparator +e.getMessage());
			return null;
		}
	}

	public ArrayList<Borrower> removeInVData(ArrayList<Borrower> list) {
		//Remove invalid data from borrower's list
		ArrayList<Borrower> copyList = new ArrayList<Borrower>();
		for (Borrower b : list) {
			if (!b.getName().equalsIgnoreCase("none")
					&& !b.getBirthday().equalsIgnoreCase("00-00-0000")) {
				copyList.add(b);
			}
		}
		return copyList;
	}

}
