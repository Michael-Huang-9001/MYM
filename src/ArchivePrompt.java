import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class ArchivePrompt {
	private static ArchivePrompt instance = null;
	private ArchiveDB ArchiveDB;
	private Scanner scanner;
	/*
	private static int state = -2;
	private static final int QUIT = -1;
	private static final int SEARCH_BY_NAME = 0;
	private static final int SEARCH_BY_CITY = 1;
	private static final int SEARCH_BY_YEAR = 2;
	private static final int CREATE = 3;
	private static final int MODIFY = 4;
	private static final int DELETE = 5;
	*/

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private ArchivePrompt(Scanner sc, ArchiveDB adb) {
		this.scanner = sc;
		this.ArchiveDB = adb;
	}

	public static ArchivePrompt getInstance(Scanner sc, ArchiveDB adb) {
		if (instance == null) {
			instance = new ArchivePrompt(sc, adb);
		}
		return instance;
	}

	/** 
	 * Prompt admin user
	 * Admin specify the date
	 * Call stored procedure with the date
	 * Show the Affected tuples
	 */
	public void promptAdmin() {
		final String datePrompt = "[ADMIN] Archiving inactive user's data\n"
				+ "Type date you want to archive from\n"
				+ "YYYY-MM-DD: ";
		final String date = this.getInput(datePrompt);
		if (date == null) {
			return;
		}
		// show the tuples that will be affected
		ResultSet rs = this.ArchiveDB.searchUserInactiveAfter(date);
		this.displayUser(rs, "These rows will be affected", "No user found");
		// If answer is yes call
		final String input = this.getInput("Is it ok to archive these users?[Y/N]: ");
		if (input.toLowerCase().equals("y")) {
			this.ArchiveDB.callStoredProc(date);
			System.out.println("User data archived");
		} 
	}

	/**
	 * Prompt user to type agency name that the user
	 * wants to search.
	 * Given agency name, search agency
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

	/**
	 * Prompt use if wants to try again or not
	 * @return true if wants to try again
	 * false if not want to try again
	 */
	private boolean promptTryAgain(String errMessage) {
		System.out.println(errMessage);
		System.out.println("Would you like to try again? [Y/N]: ");
		final String name = this.scanner.nextLine().toLowerCase();
		if (name.equals("y") || name.equals("yes")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Prompt use if wants to try again or not
	 * @return true if wants to try again
	 * false if not want to try again
	 */
	private boolean promptTryAgain() {
		return this.promptTryAgain("No agency found");
	}

	/**
	 * Output the result sets to stdout
	 * @param rs ResutSet containing agencies' information
	 */
	private void displayUser(ResultSet rs, String successMessage, String errorMessage) {
		try {
			rs.beforeFirst();
			if (!rs.isBeforeFirst()) {
				System.out.println(errorMessage);
				return;
			} else {
				System.out.println(successMessage);
				System.out.println("-----------------------");
				System.out.println("UserName\tPhone#\tIncome\tAgentID\tUpdatedAt"); 
			}
			while(rs.next())
			{
				String userName = rs.getString("userName"); 
				String phoneNumber = rs.getString("phoneNumber"); 
				int income = rs.getInt("income"); 
				int agentId = rs.getInt("agentID");
				String updatedAt = rs.getString("updatedAt");
				System.out.println(
						userName + "\t" + 
						phoneNumber + "\t" + 
						income + "\t" +
						agentId + "\t" +
						updatedAt); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}