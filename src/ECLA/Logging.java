package ECLA;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Logging implements Command{
	
	public Logging(){}
	
	@Override
	public ArrayList<Borrower> read(String fileName){
		
		try{
			ArrayList<Borrower> recordList = new ArrayList<Borrower>();
			int index = -1;
			File file = new File(fileName);
			Scanner reader = new Scanner(file);
			//System.out.println(file.getCanonicalPath());
			String s;
			Borrower b = new Borrower();
			char locationActive = 0;
			index++;
			recordList.add(b);
			while(reader.hasNextLine()){
				s = reader.nextLine().trim();
				if(s.equals("")){
				//There is blank line and create a new borrower
						b = new Borrower();
						recordList.add(b);
						index++;
						locationActive = 0;
					}
				Scanner line = new Scanner(s);
				String cmd;
				
				if(line.hasNext()){
					cmd = line.next();
					if(cmd.equalsIgnoreCase("name")){
						b.setName(Verification.name(line.nextLine()).trim());
						recordList.set(index,b);
						locationActive = 0;
					}
					else if(cmd.equalsIgnoreCase("birthday")){
						String date = line.nextLine().trim();
						Scanner dateScan = new Scanner(date);
						dateScan.useDelimiter("[-\t\n\f\r]");
						b.setBirthday(dateScan.nextInt(), dateScan.nextInt(), dateScan.nextInt());
						recordList.set(index, b);
						locationActive = 0;
					}
					else if(cmd.equalsIgnoreCase("email")){
						b.setEmail(Verification.email(line.nextLine().trim()));
						recordList.set(index, b);
						locationActive = 0;
					}
					else if(cmd.equalsIgnoreCase("phone")){
						String num = line.nextLine().trim();
						b.setPhone(Verification.phone(Integer.parseInt(num)));
						recordList.set(index, b);
						locationActive = 0;
					}
					else if(cmd.equalsIgnoreCase("address")){
						b.setAddress(line.nextLine().trim());
						recordList.set(index, b);
						locationActive = 1;
					}
					else if(cmd.equalsIgnoreCase("booklist")){
						if (line.hasNext()) {
							String data = line.nextLine().trim();
							String[] temp = data.split("\\s*,\\s*");
							String b_name = null;
							int l_d = 0, l_m = 0, l_y = 0;
							long isbn = 0;
							for (int i = 0; i < temp.length; i++) {
								String detail = temp[i].trim();
								if (Pattern.matches("^\\d{13}$", detail)) {
								//if (detail.length() == 13){
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
							}
							locationActive = 2;
					}
					else if(locationActive == 1){
						String location = b.getAddress()+" "+s.trim();
						b.setAddress(location);
						recordList.set(index, b);
					}
					else if(locationActive == 2){
						String[] temp = s.split("\\s*,\\s*");
						String b_name = null;
						int l_d = 0, l_m = 0, l_y = 0;
						long isbn = 0;
						for (int i = 0; i < 3; i++) {
							String detail = temp[i].trim();
							if (Pattern.matches("^\\d{13}$", detail)) {
							//if (detail.length() == 13) {
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
					}
					else
						System.out.println("Error! Incorrect information: " + s);
				}
			}
			reader.close();
			return recordList;
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("Error: Cannot read borrowerfile " + File.pathSeparator +e.getMessage());
			return null;
		}
	}

	public ArrayList<Borrower> removeInVData(ArrayList<Borrower> list){
		ArrayList<Borrower> copyList = new ArrayList<Borrower>();
		for(Borrower b : list){
			if (!b.getName().equalsIgnoreCase("none")&&!b.getBirthday().equalsIgnoreCase("none")){
				copyList.add(b);
			}
		}
		return copyList;
	}
	
	/*
	public void write(ArrayList<Borrower> borrowerList, String output) throws IOException {
		if(borrowerList.size()==0){
			System.out.println("no borrower!");
			return;
		}
		try {
			File outFile = new File(output);
			PrintWriter out = new PrintWriter(new FileWriter(outFile, true)); 
			//This is for saving new records without delete old files 
			for (Borrower b : borrowerList) {
				out.print(b.toString());
			}
			out.close();
			} catch (FileNotFoundException e) {
			System.out.println("file not found!"); }
	}*/

}
