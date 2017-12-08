import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class UserPrompt {
	private static UserPrompt instance = null;
	private UserDB userDb;
	private Scanner scanner;

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private UserPrompt(Scanner sc, UserDB adb) {
		this.scanner = sc;
		this.userDb = adb;
	}

	public static UserPrompt getInstance(Scanner sc, UserDB adb) {
		if (instance == null) {
			instance = new UserPrompt(sc, adb);
		}
		return instance;
	}

	/**
	 * Prompt user to search agent by house of price
	 */
	public void promptAffordableHome(int income) {

		ResultSet rs = this.userDb.searchAffordableHome(income);
		PrintResultSet.displayHouse(rs, "Found Houses", "No House found");
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