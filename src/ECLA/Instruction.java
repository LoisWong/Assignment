package ECLA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Instruction implements Command {
	private ArrayList<Borrower> queryList;
	private ArrayList<Borrower> borrowerList;

	private String output;
	private String report;

	public Instruction(ArrayList<Borrower> borrowerList, String output,
			String report) {
		this.borrowerList = borrowerList;
		this.output = output;
		this.report = report;
	}

	@Override
	public ArrayList<Borrower> read(String fileName) {
		try {
			File file = new File(fileName);
			Scanner reader = new Scanner(file);
			String s;
			// Borrower borrower = new Borrower();

			while (reader.hasNextLine()) {
				s = reader.nextLine();
				Scanner line = new Scanner(s);
				String cmd;
				Borrower borrower = new Borrower();

				while (line.hasNext()) {
					cmd = line.next();
					if (cmd.equalsIgnoreCase("add") && line.hasNextLine()) {
						int flag = 0;
						cmd = line.nextLine();
						String[] temp = cmd.split(";");

						// Construct a new borrower
						for (int i = 0; i < temp.length; i++) {
							temp[i] = temp[i].trim();
							String row[] = temp[i].split("\\s+", 2);
							this.construct_borrower(borrower, row);
						}
						// Check if it is exist. Then add or modify the list
						for (int i = 0; i < this.borrowerList.size(); i++) {
							if (borrowerList.get(i).getName()
									.equalsIgnoreCase(borrower.getName())
									&& borrowerList.get(i).getBirthday()
											.equals(borrower.getBirthday())) {
								borrowerList.set(i, borrower);
								flag = 1;
							}
						}
						if (flag == 0) {
							borrowerList.add(borrower);
						}
					} else if (cmd.equalsIgnoreCase("delete")
							&& line.hasNextLine()) {
						cmd = line.nextLine();
						String[] temp = cmd.split(";");

						// Construct a new borrower
						for (int i = 0; i < temp.length; i++) {
							temp[i] = temp[i].trim();
							String row[] = temp[i].split("\\s+", 2);
							this.construct_borrower(borrower, row);
						}
						// Check if it is exist
						for (int i = 0; i < this.borrowerList.size(); i++) {
							if (borrowerList.get(i).getName()
									.equalsIgnoreCase(borrower.getName())
									&& borrowerList.get(i).getBirthday()
											.equals(borrower.getBirthday())) {
								borrowerList.remove(i);
							}
						}
					} else if (cmd.equalsIgnoreCase("sort") && line.hasNext()) {
						cmd = line.next().trim();
						int n = 1;
						if (cmd.equalsIgnoreCase("name")) {
							if (borrowerList.size() > 1) {
								for (int i = 0; i < borrowerList.size(); i++) {
									for (int j = 0; j < borrowerList.size(); j++) {
										n = borrowerList
												.get(i)
												.getName()
												.compareTo(
														borrowerList.get(j)
																.getName());

										if (n <= 0) {
											Collections
													.swap(borrowerList, i, j);
										}
									}
								}
							}
						} else if (cmd.equalsIgnoreCase("birthday")) {
							if (borrowerList.size() > 1) {
								for (int i = 0; i < borrowerList.size(); i++) {
									for (int j = 0; j < borrowerList.size(); j++) {
										n = borrowerList
												.get(i)
												.getBirthday()
												.compareTo(
														borrowerList.get(j)
																.getBirthday());

										if (n <= 0) {
											Collections
													.swap(borrowerList, i, j);
										}
									}
								}
							}
						} else if (cmd.equalsIgnoreCase("address")) {
							if (borrowerList.size() > 1) {
								for (int i = 0; i < borrowerList.size(); i++) {
									for (int j = 0; j < borrowerList.size(); j++) {
										n = borrowerList
												.get(i)
												.getAddress()
												.compareTo(
														borrowerList.get(j)
																.getAddress());

										if (n <= 0) {
											Collections
													.swap(borrowerList, i, j);
										}
									}
								}
							}
						} else if (cmd.equalsIgnoreCase("email")) {
							if (borrowerList.size() > 1) {
								for (int i = 0; i < borrowerList.size(); i++) {
									for (int j = 0; j < borrowerList.size(); j++) {
										n = borrowerList
												.get(i)
												.getEmail()
												.compareTo(
														borrowerList.get(j)
																.getEmail());

										if (n <= 0) {
											Collections
													.swap(borrowerList, i, j);
										}
									}
								}
							}
						} else if (cmd.equalsIgnoreCase("phone")) {
							if (borrowerList.size() > 1) {
								for (int i = 0; i < borrowerList.size(); i++) {
									for (int j = 0; j < borrowerList.size(); j++) {
										n = borrowerList.get(j).getPhone()
												- borrowerList.get(i)
														.getPhone();

										if (n <= 0) {
											Collections
													.swap(borrowerList, i, j);
										}
									}
								}
							}
						}

					} else if (cmd.equalsIgnoreCase("query")
							&& line.hasNextLine()) {
						cmd = line.nextLine();
						String[] temp = cmd.split(";");
						File outFile = new File(report);
						PrintWriter out = new PrintWriter(new FileWriter(
								outFile, true));

						out.print("--- query ");
						for (int i = 0; i < temp.length; i++) {
							temp[i] = temp[i].trim();
							String row[] = temp[i].split("\\s+", 2);

							if ((row[0]).equalsIgnoreCase("name")) {
								out.print(row[0] + " " + row[1] + " ");
								borrower.setName(Verification.name(row[1]));
							} else if (row[0].equalsIgnoreCase("birthday")) {
								out.print(row[0] + " " + row[1] + " ");
								Scanner b_day = new Scanner(row[1]);
								b_day.useDelimiter("[-\t\n\f\r]");
								borrower.setBirthday(b_day.nextInt(),
										b_day.nextInt(), b_day.nextInt());
							} else {
								out.print(row[0] + " ");
								Scanner l_day = new Scanner(row[0]);
								l_day.useDelimiter("[-\t\n\f\r]");
								borrower.addBook(null, l_day.nextInt(),
										l_day.nextInt(), l_day.nextInt(), 0);
							}
						}
						out.println("---");
						out.println();
						out.close();
						queryList = new ArrayList<Borrower>();
						// Check if it is exist, then insert the borrower into
						// list

						for (int i = 0; i < this.borrowerList.size(); i++) {
							if (temp.length == 2
									&& borrowerList
											.get(i)
											.getName()
											.equalsIgnoreCase(
													borrower.getName())
									&& borrowerList.get(i).getBirthday()
											.equals(borrower.getBirthday())) {
								queryList.add(borrowerList.get(i));
							} else if (temp.length == 1
									&& borrowerList
											.get(i)
											.getName()
											.equalsIgnoreCase(
													borrower.getName())) {
								queryList.add(borrowerList.get(i));
							} else if (temp.length == 1
									&& borrowerList.get(i).getBirthday()
											.equals(borrower.getBirthday())) {
								queryList.add(borrowerList.get(i));
							} else if (temp.length > 2
									&& borrowerList
											.get(i)
											.getName()
											.equalsIgnoreCase(
													borrower.getName())
									&& borrowerList.get(i).getBirthday()
											.equals(borrower.getBirthday())) {
								queryList.add(borrowerList.get(i));
							}
						}
						this.writeQuery(queryList, report);

					} else if (cmd.equalsIgnoreCase("save")) {
						this.write(borrowerList, output);
					} else
						System.out.println("Error: No instruction in " + s);
				}
			}
			reader.close();
			return borrowerList;
		} catch (Exception e) {
			System.out.println("Error:-Cannot read instruction file"
					+ e.getMessage());
			return null;
		}
	}

	public void construct_borrower(Borrower borrower, String[] data)
			throws Exception {
		if ((data[0]).equalsIgnoreCase("name")) {
			borrower.setName(Verification.name(data[1]));
		} else if (data[0].equalsIgnoreCase("birthday")) {
			Scanner line = new Scanner(data[1]);
			line.useDelimiter("[-\t\n\f\r]");
			borrower.setBirthday(line.nextInt(), line.nextInt(), line.nextInt());
		} else if (data[0].equalsIgnoreCase("phone")) {
			borrower.setPhone(Verification.phone(Integer.parseInt(data[1])));
		} else if (data[0].equalsIgnoreCase("address")) {
			borrower.setAddress(Verification.address(data[1]));
		} else if (data[0].equalsIgnoreCase("email")) {
			borrower.setEmail(Verification.email(data[1]));
		} else if (data[0].equalsIgnoreCase("booklist")) {
			String[] row = data[1].split(",");
			// In order to split different book item
			for (int i = 0; i < row.length; i++) {
				String[] temp = row[i].split("\\s+");
				String b_name = "";
				long isbn = 0;
				int l_d = 0, l_m = 0, l_y = 0;
				for (int j = 0; j < temp.length; j++) {
					// String detail = temp[i].trim();
					if (Pattern.matches("^\\d{13}$", temp[j])) {
						isbn = Long.parseLong(temp[j]);
					} else if (Pattern.matches("^\\d{1,2}-\\d{1,2}-\\d{4}$",
							temp[j])) {
						Scanner dateScan = new Scanner(temp[j]);
						dateScan.useDelimiter("[-\t\n\f\r]");
						l_d = dateScan.nextInt();
						l_m = dateScan.nextInt();
						l_y = dateScan.nextInt();
					} else
						b_name += temp[j] + " ";
				}
				borrower.addBook(b_name, l_d, l_m, l_y, isbn);
			}
		} else
			System.out
					.println("There is something wrong eith your input information.");
	}

	public void writeQuery(ArrayList<Borrower> queryList, String report)
			throws IOException {
		if (queryList.size() == 0) {
			System.out.println("no result!");
			return;
		}
		try {
			File outFile = new File(report);
			PrintWriter out = new PrintWriter(new FileWriter(outFile, true));
			for (Borrower b : queryList) {
				out.print(b.toString().trim());
				out.println();
				out.println();
			}
			out.println("--- End of Query ---");
			out.println();
			out.println();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found!");
		}

	}

	public void write(ArrayList<Borrower> borrowerList, String output)
			throws IOException {
		if (borrowerList.size() == 0) {
			System.out.println("no borrower!");
			return;
		}
		try {
			File outFile = new File(output);
			PrintWriter out = new PrintWriter(outFile);
			for (Borrower b : borrowerList) {
				out.print(b.toString());
				out.println();
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found!");
		}

	}

}
