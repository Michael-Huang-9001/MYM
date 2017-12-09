import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class AppointmentPrompt {
	private static AppointmentPrompt instance = null;
	private AppointmentDB apptDb;
	private Scanner scanner;
	
	private static String PROMPT_USER_STRING = 
			"What would you like to do about appointment?\n"
			+ "1: See your appointment\n"
			+ "2: Make new appointment\n"
			+ "3: Cancel appointment\n"
			+ "B: Type back to go back";

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private AppointmentPrompt(Scanner sc, AppointmentDB adb) {
		this.scanner = sc;
		this.apptDb = adb;
	}

	public static AppointmentPrompt getInstance(Scanner sc, AppointmentDB adb) {
		if (instance == null) {
			instance = new AppointmentPrompt(sc, adb);
		}
		return instance;
	}

	/** 
	 * Prompt user
	 * User can 
	 * search
	 * make
	 * delete
	 * their appointment
	 * @param userName used to query appointment table
	 */
	public void promptUser(String userName) {
		System.out.println("[ADMIN] What would you like to do about agencies?");
		boolean quit = false;
		while (quit == false) {
			String input = this.getInput(PROMPT_USER_STRING).toLowerCase();
			switch (input) {
			// Search
			case "1":
				this.showUserAppointment(userName);
				break;

			// Make new appointment
			case "2":
				break;

			// Delete existing appt
			case "3":
				break;

			// Search
			case "4":
				break;

			// Search
			case "5":
				break;

			// Search
			case "6":
				break;
			

			case "b":
			case "back":
				quit = true;
				break;

			default:
				System.out.println("Unrecognized input. Please try again.");
			}
		}
	}
	
	/**
	 * Show the user's booked appointment
	 * @param userName
	 */
	private void showUserAppointment(String userName) {
		ResultSet rs = this.apptDb.searchApptByUserName(userName);
		PrintResultSet.displayAppt(rs, "Found Appointment", "No appointment found");
	}

	/**
	 * Used to get user input
	 * @param inputPrompt Text to be shown to get input 
	 * @return String of input return null if user input empty string
	 */
	private String getInput(String inputPrompt) {
		System.out.println(inputPrompt);
		final String input = this.scanner.nextLine();
		if (input.equals("")) {
			System.out.println("Invalid input");
			return null;
		}
		return input;
	}
}