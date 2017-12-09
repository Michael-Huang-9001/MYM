public class PrintErrorMessage {
	 private PrintErrorMessage() {}
	 
	 public static void PrintMessage(String sqlState, String message) {
		 String errorMessage = null;
		 
		 System.out.println("SQL state: " + sqlState);
		 switch(sqlState) {
		 case "22001":
			 errorMessage = "Your input format is not correct.\n"
			 		+ "For example, date and time has to be in YYYY-MM-DD HH:MM";
			 break;
		 
		 case "23000":
			 errorMessage = integrityDetail(message);
			 break;

		 default:
			 errorMessage = "Something unexpected happened.";
			 break;
		 }
		 System.out.println(errorMessage);
	 }
	 
	 private static String integrityDetail(String message) {
		String res = null;
		
		if (message.contains("passed date")) {
			res = "You tried to book an appointment that has already passed.\n"
					+ "Please make sure to input the date and time that are in the future.";
		} else if (message.contains("One user can book only")) {
			res = "You tried to book an appointment even though you already have one appointment booked\n"
					+ "Please wait until you completed the appointment.";
		} else {
			 res = "Your input does not exist on database\n"
			 		+ "You get this error when you input data that is supposed to be referencing other table\n"
			 		+ "but could not find it on the table. For example, when making new appointment agent id and house id\n"
			 		+ "have to already exist on the database.";
		}
		return res;
	 }
}