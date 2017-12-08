import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Takes care of table RealEstateCompany
 * Singleton
 * @author yk
 *
 */

public class AgentPrompt {
	private static AgentPrompt instance = null;
	private AgentDB agentDb;
	private static int state = -2;
	private Scanner scanner;
	private static final int QUIT = -1;
	private static final int SEARCH_BY_PRICE = 0;
	private static final int SEARCH_BY_INCOME = 11;
	private static final int SEARCH_BY_YEAR = 2;
	private static final int CREATE = 3;
	private static final int MODIFY = 4;
	private static final int DELETE = 5;

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private AgentPrompt(Scanner sc, AgentDB adb) {
		this.scanner = sc;
		this.agentDb = adb;
	}

	public static AgentPrompt getInstance(Scanner sc, AgentDB adb) {
		if (instance == null) {
			instance = new AgentPrompt(sc, adb);
		}
		return instance;
	}

	/** 
	 * Prompt non-admin user
	 * User can do 
	 * search
	 */
	public void promptUser() {
		while (state != QUIT) {
			int newState = getUserInput();
			switch (newState) {
			case QUIT:
				state = QUIT;
				break;
			case SEARCH_BY_PRICE:
				state = SEARCH_BY_PRICE;
				promptSearchByPrice();
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
				+ "What would you like to know about agent?\n"
				+ "Select from below\n"
				+ "0: Search agencies who can show you houses\n"
				+ "1: Search agencies that have houses located in a certain city.\n"
				+ "2: Search agencies that have agents who can show houses built before some year\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "0":
			returnInt = SEARCH_BY_PRICE;
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
			case SEARCH_BY_PRICE:
				state = SEARCH_BY_PRICE;
				promptSearchByPrice();
				break;
			case SEARCH_BY_INCOME:
				state = SEARCH_BY_INCOME;
				promptSearchByIncome();
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
				+ "0: Search agencies who can show you houses\n"
				+ "11: Search agent by client's income\n"
				+ "B: Type back to go back");
		String command = scanner.nextLine().toLowerCase();
		switch (command) {
		case "0":
			returnInt = SEARCH_BY_PRICE;
			break;

		case "11":
			returnInt = SEARCH_BY_INCOME;
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
	 * Prompt user to search agent by house of price
	 */
	private void promptSearchByPrice() {
		final String input = this.getInput("Type your budget: ");

		if (input == null) {
			return;
		}

		ResultSet AgentRsultSet = this.agentDb.searchByPrice(input);
		this.agentDb.displayAget(AgentRsultSet, "Found Agent", "No Agent found");
	}
	
	private void promptSearchByIncome() {
		final String input = this.getInput("Type client annual income: ");

		if (input == null) {
			return;
		}

		ResultSet AgentRsultSet = this.agentDb.searchByIncome(input);
		this.agentDb.displayAget(AgentRsultSet, "Found Agent", "No Agent found");
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