package ECLA;

import java.util.regex.Pattern;

public class Verification {
	public static String email(String email) throws Exception {
		Pattern checkEmail = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		if (!checkEmail.matcher(email).matches()) {
			// throw new Exception("Invalid email address");
			return null;
		}
		return email;
	}

	public static String name(String name) throws Exception {
		if (!Pattern.matches("[A-Za-z\\s]+$", name)) {
			return "none";
		}
		int i;
		String[] Name = name.split("\\s+");
		String formatName = "";
		for (i = 0; i < Name.length - 1; i++) {
			formatName += Name[i] + " ";
		}
		formatName += Name[i];
		return formatName;
	}

	public static String address(String address) throws Exception {
		int i;
		String[] Address = address.split("\\s+");
		String formatAddress = "";
		for (i = 0; i < Address.length - 1; i++) {
			formatAddress += Address[i] + " ";
		}
		formatAddress += Address[i];
		return formatAddress;
	}

	public static int phone(int phone) throws Exception {
		if (1000000000 - phone > 90000000) {
			return phone;
		}
		return 0;
	}
}
