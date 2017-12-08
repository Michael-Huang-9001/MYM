import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class AdminUserPrompt {
	private static AdminUserPrompt instance = null;
	private AdminUserDB adminUserDb;
	private static int state = -2;
	private Scanner scanner;
	private static final int QUIT = -1;
	private static final int CREATE = 1;
	private static final int MODIFY = 2;
	private static final int DELETE = 3;
	private static final int INCOME = 4;

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private AdminUserPrompt(Scanner sc, AdminUserDB adb) {
		this.scanner = sc;
		this.adminUserDb = adb;
	}

	public static AdminUserPrompt getInstance(Scanner sc, AdminUserDB adb) {
		if (instance == null) {
			instance = new AdminUserPrompt(sc, adb);
		}
		return instance;
	}

	/** 
	 * Prompt non-admin user
	 * User can do 
	 * search
	 */
	public void promptAdmin() {
		while (state != QUIT) {
			int newState = getAdminInput();
			switch (newState) {
			case QUIT:
				state = QUIT;
				break;
			case CREATE:
				state = CREATE;
				break;
			case MODIFY:
				state = MODIFY;
				break;
			case DELETE:
				state = DELETE;
				break;
			case INCOME:
				state = INCOME;
				promptSearchUserByIncome();
				break;
			default:
				System.out.println("Unrecognized input. Please try again.");
			}
		}
	}

	/**
	 * Select state
	 */
	private int getAdminInput() {
		int returnInt = -2; // Initialize to invalid int

		System.out.println("-----------------------------------------\n"
				+ "What would you like to know about agent?\n"
				+ "Select from below\n"
				+ "1: Create new user.\n"
				+ "2: Modify existing user\n"
				+ "3: Delete existing user\n"
				+ "4: Search user by income\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "1":
			returnInt = CREATE;
			break;
		case "2":
			returnInt = MODIFY;
			break;
		case "3":
			returnInt = DELETE;
			break;
		case "4":
			returnInt = INCOME;
			break;

		case "b":
		case "back":
			returnInt = QUIT;
			break;
		}
		return returnInt;
	}

	/**
	 * Prompt user to search agent by house of price
	 */
	private void promptSearchUserByIncome() {
		final String incomeMin = this.getInput("Type user Min income: ");
		final String incomeMax = this.getInput("Type user Max income: ");

		if (incomeMin == null || incomeMax == null) {
			return;
		}

		ResultSet rs = this.adminUserDb.searchUserByIncome(incomeMin, incomeMax);
		PrintResultSet.displayUser(rs, "Found User", "No User found");
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