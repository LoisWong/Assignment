package ECLA;

import java.util.regex.Pattern;

public class Verification {
	public static String email(String email) throws Exception{
		Pattern checkEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
		if (!checkEmail.matcher(email).matches()) {
		    //throw new Exception("Invalid email address");
			return null;
		}
	return email;
	}
	
	public static String name(String name) throws Exception{
		if (!Pattern.matches("[A-Za-z\\s]+$", name)){
			return "none";
		}
		return name;
	}
	
	public static int phone(int phone) throws Exception{
		if (1000000000-phone > 900000000){
			return phone;
		}
		return 0;
	}
}
