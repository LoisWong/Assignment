package ECLA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Instruction implements Command{
	private ArrayList<Borrower> borrowerList;
	private String output;
	private String report;

	public Instruction(ArrayList<Borrower> borrowerList, String output, String report){
		this.borrowerList = borrowerList;
		this.output = output;
		this.report = report;
	}

	@Override
	public ArrayList<Borrower> read(String fileName){
		try{
			File file = new File(fileName);
			Scanner reader = new Scanner(file);
			String s;
			Borrower borrower = new Borrower();
			
			while(reader.hasNextLine()){
				s = reader.nextLine();
				Scanner line = new Scanner(s);
				String cmd;
				
				while(line.hasNext()){
					cmd = line.next();
					if(cmd.equalsIgnoreCase("add")){
						cmd = line.nextLine();
						String [] temp = cmd.split(";");
						
						//Construct a new borrower
						for (int i=0;i<temp.length;i++){
							temp[i] = temp[i].trim();
							String  row[] = temp[i].split("\\s+",2);
							this.construct_borrower(borrower, row);
						}
						//Check if it is exist. Then add or modify the list
						for (int i=0; i<this.borrowerList.size(); i++){
							if (borrowerList.get(i).getName().equalsIgnoreCase(borrower.getName()) && borrowerList.get(i).getBirthday().equals(borrower.getBirthday())){
								borrowerList.set(i, borrower);
							}
							else
								borrowerList.add(borrower);
						}
					}
					else if(cmd.equalsIgnoreCase("delete")){
						cmd = line.nextLine();
						String [] temp = cmd.split(";");
						
						//Construct a new borrower
						for (int i=0;i<temp.length;i++){
							temp[i] = temp[i].trim();
							String  row[] = temp[i].split("\\s+",2);
							this.construct_borrower(borrower, row);
						}
						//Check if it is exist
						for (int i=0; i<this.borrowerList.size(); i++){
							if (borrowerList.get(i).getName().equalsIgnoreCase(borrower.getName()) && borrowerList.get(i).getBirthday().equals(borrower.getBirthday())){
								borrowerList.remove(i);
							}
						}
					}
					else if(cmd.equalsIgnoreCase("sort")){
						
					}
					else if(cmd.equalsIgnoreCase("query")){
						
						
					}
					else if(cmd.equalsIgnoreCase("save")){
						this.write(borrowerList, output);
					}
					else 
						System.out.println("Error: No instruction in " + s);
				}
			}
			reader.close();
			return borrowerList;
		}catch(Exception e){
			System.out.println("Error:-Cannot read instruction file"+ e.getMessage());
			return null;
		}
	}

	public void construct_borrower(Borrower borrower, String[] data) throws Exception{
		if ((data[0]).equalsIgnoreCase("name")){
			borrower.setName(Verification.name(data[1]));
		}else if (data[0].equalsIgnoreCase("birthday")){
			Scanner line = new Scanner(data[1]);
			line.useDelimiter("[-\t\n\f\r]");
			borrower.setBirthday(line.nextInt(),line.nextInt(),line.nextInt());
		}else if (data[0].equalsIgnoreCase("phone")){
			borrower.setPhone(Verification.phone(Integer.parseInt(data[1])));
		}else if (data[0].equalsIgnoreCase("address")){
			borrower.setAddress(data[1]);
		}else if (data[0].equalsIgnoreCase("email")){
			borrower.setEmail(Verification.email(data[1]));
		}else if (data[0].equalsIgnoreCase("booklist")){
			String[] row = data[1].split(",");
			//In order to split different book item
			for (int i=0; i<row.length; i++) {
				String[] temp = row[i].split("\\s+");
				String b_name = null;
				int l_d = 0, l_m = 0, l_y = 0, isbn = 0;
				for (int j = 0; j < temp.length; j++) {
					if (Pattern.matches("[0-9]*", temp[i])) {
						isbn = Integer.parseInt(temp[i]);
					} else if (Pattern.matches("^\\d{1,2}-\\d{1,2}-\\d{4}$",
							temp[i])) {
						Scanner dateScan = new Scanner(temp[i]);
						dateScan.useDelimiter("[-\t\n\f\r]");
						l_d = dateScan.nextInt();
						l_m = dateScan.nextInt();
						l_y = dateScan.nextInt();
					} else
						b_name = temp[i];
				}
				borrower.addBook(b_name, l_d, l_m, l_y, isbn);
			}
		}else
			System.out.println("There is something wrong eith your input information.");
	}


	public void write(ArrayList<Borrower> borrowerList, String output) throws IOException {
		if(borrowerList.size()==0){
			System.out.println("no borrower!");
			return;
		}
		try {
			File outFile = new File(output);
			PrintWriter out = new PrintWriter(outFile); 
			//PrintWriter out = new PrintWriter(new FileWriter(outFile, true)); 
			//This is for saving new records without delete old files 
			for (Borrower b : borrowerList) {
				out.print(b.toString());
				out.println();
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found!"); }
		
	}

}
