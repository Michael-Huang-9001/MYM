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
	private static final int SEARCH_BY_CITY = 1;
	private static final int SEARCH_BY_YEAR = 2;
	private static final int CREATE = 3;
	private static final int MODIFY = 4;
	private static final int DELETE = 5;

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
				promptSearchByName();
				break;

			case 1:
				state = SEARCH_BY_CITY;
				promptSearchByCity();
				break;

			case 2:
				state = SEARCH_BY_YEAR;
				promptSearchByYear();
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
				+ "1: Search agencies that have houses located in a certain city.\n"
				+ "2: Search agencies that have agents who can show houses built before some year\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "0":
			returnInt = SEARCH_BY_NAME;
			break;

		case "1":
			returnInt = SEARCH_BY_CITY;
			break;

		case "2":
			returnInt = SEARCH_BY_YEAR;
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
		System.out.println("[ADMIN] What would you like to do about agencies?");
		while (state != QUIT) {
			int newState = getAdminInput();
			switch (newState) {
			case -1:
				state = QUIT;
				break;
			case 0:
				state = SEARCH_BY_NAME;
				promptSearchByName();
				break;

			case 1:
				state = SEARCH_BY_CITY;
				promptSearchByCity();
				break;

			case 2:
				state = SEARCH_BY_YEAR;
				promptSearchByYear();
				break;

			case 3:
				state = CREATE;
				promptCreate();
				break;

			case 4:
				state = MODIFY;
				this.promptModify();
				break;

			case 5:
				state = DELETE;
				System.out.println("Delete");
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
				+ "Select from below\n"
				+ "0: Search agencies by name\n"
				+ "1: Search agencies that have houses located in a certain city.\n"
				+ "2: Search agencies that have agents who can show houses built before some year\n"
				+ "3: Create new agency\n"
				+ "4: Update an existing agency\n"
				+ "5: Delete an existing agency\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "0":
			returnInt = SEARCH_BY_NAME;
			break;

		case "1":
			returnInt = SEARCH_BY_CITY;
			break;

		case "2":
			returnInt = SEARCH_BY_YEAR;
			break;

		case "3":
			returnInt = CREATE;
			break;

		case "4":
			returnInt = MODIFY;
			break;

		case "5":
			returnInt = DELETE;
			break;

		case "b":
		case "back":
			returnInt = QUIT;
			break;
		}
		return returnInt;
	}

	/**
	 * Prompt user to type agency name that the user
	 * wants to search.
	 * Given agency name, search agency
	 */
	private void promptSearchByName() {
		System.out.print("Type a name of agency you want to search: ");
		final String agencyName = this.scanner.nextLine();
		ResultSet AgencyRsultSet = this.agencyDb.searchAngecyByName(agencyName);

		this.displayAgecy(AgencyRsultSet);
	}

	/**
	 * Prompt user to type city of house s/he is looking for 
	 * and shows the agency who has agent who can show that house
	 * Given agency name, search agency
	 */
	private void promptSearchByCity() {
		System.out.print("Type a city of house that you wan to know: ");
		final String cityName = this.scanner.nextLine();
		ResultSet AgencyRsultSet = this.agencyDb.searchAngecyByCity(cityName);

		this.displayAgecy(AgencyRsultSet);
	}

	/**
	 * Prompt user to type year of house s/he is looking for
	 * and shows the agency who has agent who can show that house
	 * Given agency name, search agency
	 */
	private void promptSearchByYear() {
		System.out.print("Type a year of houses that are built before: ");
		final String year = this.scanner.nextLine();
		ResultSet AgencyRsultSet = this.agencyDb.searchAngecyByYear(Integer.valueOf(year));

		this.displayAgecy(AgencyRsultSet);
	}

	/**
	 * Prompt user to create new agency
	 * ask name and phone# of agency
	 * If success go back show success message and go back
	 * else as if wants to try again
	 */
	private void promptCreate() {
		boolean isQuit = false;
		while (isQuit == false) {
			System.out.println("Type name of new agency: ");
			final String name = this.scanner.nextLine();
			System.out.println("Type phone# of new agency (No dash No parenthesis: ");
			final String phoneNum = this.scanner.nextLine();
			
			try {
				this.agencyDb.create(name, phoneNum);
				System.out.println("New agency created");
				System.out.println("Name: " + name + " Phone#: " + phoneNum);
				
				isQuit = true;
			} catch (SQLException e) {
				e.printStackTrace();
				if (!this.promptTryAgain()) {
					isQuit = true;
				}
			}
		}
	}

	/**
	 * Prompt user to modify agency
	 * If success show success message and go back
	 * else ask if wants to try again
	 */
	private void promptModify() {
		boolean isQuit = false;
		while (isQuit == false) {
			try {
				final int agencyID = this.promptSearchByNameAndPhone();
				if (agencyID == -1) {
					isQuit = true;
					continue;
				}
				this.promptNewAgency(agencyID);
				isQuit = true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getSQLState());

				if (!this.promptTryAgain()) {
					isQuit = true;
				}
			}
		}
	}
	
	/**
	 * Ask user to type new name and new phone number for existing agency
	 * 
	 * @param agencyID	ID of agency to be modified
	 * @throws SQLException
	 */
	private void promptNewAgency(int agencyID) throws SQLException {
		System.out.println("Type new name and new phone# of agency");
		System.out.print("New Name: ");
		final String newName = this.scanner.nextLine();
		System.out.print("New Phone#: ");
		final String newPhoneNum = this.scanner.nextLine();
		this.agencyDb.modify(agencyID, newName, newPhoneNum);
	}

	/**
	 * Search agency by name and phone number
	 * @return agency id if no agency found by name and phone#, then -1 is returned
	 * @throws SQLException 
	 */
	private int promptSearchByNameAndPhone() throws SQLException {
		System.out.println("Type name and phone# of agency you want to modify");
		System.out.print("Name: ");
		final String name = this.scanner.nextLine();
		System.out.print("Phone#: ");
		final String phoneNum = this.scanner.nextLine();
		
		ResultSet rs = null;
		rs = this.agencyDb.searchAngecyByNameAndPhone(name, phoneNum);
		this.displayAgecy(rs, "Modifying this agency", "No agency found");
		
		final int aId = this.getAgencyID(rs);
		if (aId == -1) {
			
		}

		return aId;
	}
	
	/**
	 * Get first tuple of id
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private int getAgencyID(ResultSet rs) throws SQLException {
		int id = -1;
		rs.beforeFirst();
		
		// No result
		if (!rs.isBeforeFirst()) {
			return -1;
		}
		rs.next();
		id = rs.getInt("agencyID");
		return id;
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
	private void displayAgecy(ResultSet rs, String successMessage, String errorMessage) {
		try {
			rs.beforeFirst();
			if (!rs.isBeforeFirst()) {
				System.out.println(errorMessage);
				return;
			} else {
				System.out.println(successMessage);
				System.out.println("-----------------------");
				System.out.println("ID\tAgency Name\t\t\tPhone#"); 
			}
			while(rs.next())
			{
				String phoneNumber = rs.getString("phoneNumber"); 
				String agencyName = rs.getString("agencyName"); 
				int id = rs.getInt("agencyID");
				System.out.println(id + "\t" + agencyName + "\t\t\t" + phoneNumber); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To have default params
	 * @param rs
	 */
	private void displayAgecy(ResultSet rs) {
		this.displayAgecy(rs, "Found agencies", "No agency found");
	}

	public static void main(String[] args) {
	}
}