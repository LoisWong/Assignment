package ECLA;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Logging{
	
	public Logging(){}
	
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
						locationActive = 2;
					}
					else if(locationActive == 1){
						String location = b.getAddress()+" "+s.trim();
						b.setAddress(location);
						recordList.set(index, b);
					}
					else if(locationActive == 2){
						String data = line.nextLine();
						String [] temp = data.split(",");
						
						String b_name = null;
						int l_d = 0,l_m = 0,l_y = 0,isbn = 0;
						for(int i=0; i<temp.length; i++){
							//temp[i] = temp[i].trim();
							if(Pattern.matches("[0-9]*", temp[i])){
								isbn = Integer.parseInt(temp[i]);
							}
							else if(Pattern.matches("^\\d{1,2}-\\d{1,2}-\\d{4}$", temp[i])){
								Scanner dateScan = new Scanner(temp[i]);
								dateScan.useDelimiter("[-\t\n\f\r]");
								l_d = dateScan.nextInt();
								l_m = dateScan.nextInt();
								l_y = dateScan.nextInt();
							}
							else 
								b_name = temp[i];
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
	
	/*@Override
	public void write(ArrayList<Borrower> borrowerList, String output) throws IOException {
		if(borrowerList.size()==0){
			System.out.println("no borrower!");
			return;
		}
		try {
			File outFile = new File(output);
			PrintWriter out = new PrintWriter(outFile); 
			for (Borrower b : borrowerList) {
				out.print(b.toString());
			}
			out.close();
			} catch (FileNotFoundException e) {
			System.out.println("file not found!"); }
	}*/

}
