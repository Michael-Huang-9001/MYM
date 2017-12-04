import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class AgencyPrompt {
	private static AgencyPrompt instance = null;
	private AgencyDB agencyDb;
	private static int state = -2;
	private Scanner scanner;
	private static final int QUIT = -1;
	private static final int SEARCH_BY_NAME = 0;
	private static final int MODIFY = 1;
	private static final int DELETE = 2;
	private static final int CREATE = 3;

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private AgencyPrompt(Scanner sc, AgencyDB adb) {
		this.scanner = sc;
		this.agencyDb = adb;
	}

	public static AgencyPrompt getInstance(Scanner sc, AgencyDB adb) {
		if (instance == null) {
			instance = new AgencyPrompt(sc, adb);
		}
		return instance;
	}

	/** 
	 * Prompt non-admin user
	 * User can do 
	 * search
	 * for Real Estate Company
	 */
	public void promptUser() {
		while (state != QUIT) {
			int newState = getUserInput();
			switch (newState) {
			case -1:
				state = QUIT;
				break;
			case 0:
				state = SEARCH_BY_NAME;
				promptSearch();
				break;

			default:
				System.out.println("Unrecognized input. Please try again.");
			}
		}
	}

	/**
	 * Select state
	 */
	private int getUserInput() {
		int returnInt = -2; // Initialize to invalid int

		System.out.println("-----------------------------------------\n"
				+ "What would you like to know about agencies?\n"
				+ "Select from below\n"
				+ "0: Search agencies by name\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "0":
			returnInt = SEARCH_BY_NAME;
			break;

		case "b":
		case "back":
			returnInt = QUIT;
			break;
		}
		return returnInt;
	}
	/** 
	 * Prompt admin user
	 * User can do 
	 * search
	 * delete
	 * create
	 * modify
	 * for Real Estate Company
	 * 
	 * @param connection	To use MySQL
	 * @param scanner	To read stdin
	 */
	public void promptAdmin() {
		System.out.println("What would you like to do about agencies as admin user?");
	}

	/**
	 * Prompt user to type agency name that the user
	 * wants to search.
	 * Given agency name, search agency
	 */
	private void promptSearch() {
		System.out.print("Type a name of agency you want to search: ");
		final String agencyName = this.scanner.nextLine();
		ResultSet AgencyRsultSet = this.agencyDb.searchAngecyByName(agencyName);

		this.displayAgecy(AgencyRsultSet);
	}


	/**
	 * Output the result sets to stdout
	 * @param rs ResutSet containing agencies' information
	 */
	private void displayAgecy(ResultSet rs) {
		try {
			if (!rs.isBeforeFirst()) {
				System.out.println("Nothing found");
				return;
			} else {
				System.out.println("Found Agency");
				System.out.println("-----------------------");
				System.out.println("ID\tAgency Name\tPhone#"); 
			}
			while(rs.next())
			{
				String phoneNumber = rs.getString("phoneNumber"); 
				String agencyName = rs.getString("agencyName"); 
				int id = rs.getInt("agencyID");
				System.out.println(id + "\t" + agencyName + "\t" + phoneNumber); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String input = "";
		try(Scanner scanner = new Scanner(System.in)){
			while (!input.equals("q")) {
				System.out.print("Input: ");
				input = scanner.nextLine();
				System.out.println("Input was: " + input);
			}
		}
	}
}
