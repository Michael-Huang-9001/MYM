import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class HousePrompt {
	private static HousePrompt instance = null;
	private HouseDB houseDb;
	private Scanner scanner;
	
	private static String PROMPT_USER_STRING = 
			"What would you like to do about appointment?\n"
			+ "1: Find populer houses\n"
			+ "B: Type back to go back";

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private HousePrompt(Scanner sc, HouseDB adb) {
		this.scanner = sc;
		this.houseDb = adb;
	}

	public static HousePrompt getInstance(Scanner sc, HouseDB adb) {
		if (instance == null) {
			instance = new HousePrompt(sc, adb);
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
	public void promptAdmin() {
		System.out.println("[ADMIN] What would you like to do about houses?");
		boolean quit = false;
		while (quit == false) {
			String input = this.getInput(PROMPT_USER_STRING).toLowerCase();
			switch (input) {
			case "1":
				this.promptPopulerHouse();
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
	private void promptPopulerHouse() {
		final String input = this.getInput("Type count of appointment");
		ResultSet rs = this.houseDb.searchHouseByApptCount(input);
		PrintResultSet.displayHouse(rs, "Found Appointment", "No appointment found");
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