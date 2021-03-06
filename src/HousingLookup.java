import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sql.DataSource;

public class HousingLookup {
	private Scanner in;
	private int state;
	private static final int QUIT = -1;
	private static final int ADMIN_OR_USER = 0;
	private static final int LOGIN_OR_REGISTER = 1;
	private static final int LOG_IN = 2;
	private static final int REGISTER = 3;
	private static final int LOGGED_IN = 4;
	private static final int SEARCH_FOR_HOMES = 5;
	private static final int ADMIN_LOGIN = 100;
	private static final int ADMIN_LOGGED_IN = 101;
	private final String ADMIN_PW = "admin";
	private DataSource ds = DataSourceFactory.getMySQLDataSource();
	private PreparedStatement statement;
	private Connection connection;
	private User user;

	private AgencyPrompt agencyPrompt;
	private ArchivePrompt archivePrompt;
	private AgentPrompt agentPrompt;
	private AdminUserPrompt adminUserPrompt;
	private UserPrompt userPrompt;
	private AppointmentPrompt apptPrompt;
	private HousePrompt housePrompt;

	private AgencyDB agencyDb;
	private AgentDB agentDb;
	private ArchiveDB archiveDb;
	private AdminUserDB adminUserDb;
	private UserDB userDb;
	private AppointmentDB apptDb;
	private HouseDB houseDb;
	

	public static void main(String args[]) {
		HousingLookup housingApp = new HousingLookup();
	}

	public HousingLookup() {
		try {
			connection = ds.getConnection();
			statement = connection.prepareStatement("USE Housing_Lookup;");
			statement.executeUpdate();

			state = ADMIN_OR_USER;
			in = new Scanner(System.in);

			this.createDbInstances(connection);
			this.createPromptInstances(in);

			System.out.println("Welcome to Housing Lookup! Enter 'q' to quit the application.");
			while (state != QUIT) {
				try {
					// System.out.println("State = " + state);
					switch (state) {
					case ADMIN_OR_USER:
						promptUserType();
						break;
					case LOGIN_OR_REGISTER:
						promptUser();
						break;
					case LOG_IN:
						login();
						break;
					case REGISTER:
						register();
						break;
					case LOGGED_IN:
						loggedIn();
						break;
					case ADMIN_LOGGED_IN:
						adminLoggedIn();
						break;
					case SEARCH_FOR_HOMES:
						searchForHomes();
						break;
					case ADMIN_LOGIN:
						promptAdmin();
						break;
					}
					System.out.println("----------------");
				} catch (Exception e) {

				}
			}
			System.out.println("Thank you for using Housing Lookup! We hope to see you again!");
		} catch (SQLException sql) {
			// sql.printStackTrace();
			System.out.println("Could not connect to database.");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Some other error has occurred.");
		} finally {
			try {
				in.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Database error.");
			} catch (Exception e) {
				// e.printStackTrace();
				// Scanner cannot be closed.
			}
		}
	}

	/**
	 * Get instance of AgencyDB 
	 * @param in	to get user input
	 * @return true if created, false if not
	 */
	private boolean createDbInstances(Connection conn) {
		this.agencyDb = AgencyDB.getInstance(conn);
		this.archiveDb = ArchiveDB.getInstance(conn);
		this.agentDb = AgentDB.getInstance(conn);
		this.adminUserDb = AdminUserDB.getInstance(conn);
		this.userDb = UserDB.getInstance(conn);
		this.apptDb = AppointmentDB.getInstance(conn);
		this.houseDb = HouseDB.getInstance(conn);

		if ( this.agencyDb == null || 
			 this.archiveDb == null ||
			 this.adminUserDb == null ||
			 this.userDb == null ||
			 this.apptDb == null ||
			 this.houseDb == null ||
			 this.agentDb == null) {
			System.err.println("Failed to get instance of AgencyDB or ArchiveDB\n"
					+ "This class is singleton cannot be instanciated more than one");
			return false;
		}
		return true;
	}

	/**
	 * Get instance of AgencyPrompt 
	 * @param in	to get user input
	 * @return true if created, false if not
	 */
	private boolean createPromptInstances(Scanner in) {
		this.agencyPrompt = AgencyPrompt.getInstance(in, this.agencyDb);
		this.archivePrompt = ArchivePrompt.getInstance(in, this.archiveDb);
		this.agentPrompt = AgentPrompt.getInstance(in, this.agentDb);
		this.adminUserPrompt = AdminUserPrompt.getInstance(in, this.adminUserDb);
		this.userPrompt = UserPrompt.getInstance(in, this.userDb);
		this.apptPrompt = AppointmentPrompt.getInstance(in, this.apptDb);
		this.housePrompt = HousePrompt.getInstance(in, this.houseDb);

		if ( this.agencyPrompt == null ||
			 this.agentDb == null ||
			 this.adminUserPrompt == null ||
			 this.userPrompt == null ||
			 this.apptPrompt == null ||
			 this.housePrompt == null ||
			 this.archivePrompt == null ) {
			System.err.println("Failed to get instance of AgencyPrompt or ArchivePrompt\n"
					+ "This class is singleton cannot be instanciated more than one");
			return false;
		}
		return true;
	}

	private void promptUserType() {
		System.out.println("Are you a user or an admin?");
		System.out.print("1: User\t2: Admin\nYour choice: ");
		String command = in.nextLine().toLowerCase();
		// System.out.println("in.next() = " + command);
		switch (command) {
		case "1":
			state = LOGIN_OR_REGISTER;
			break;
		case "user":
			state = LOGIN_OR_REGISTER;
			break;
		case "2":
			state = ADMIN_LOGIN;
			break;
		case "admin":
			state = ADMIN_LOGIN;
			break;
		case "q":
			state = QUIT;
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
			break;
		}
	}

	private void promptUser() {
		System.out.println("Login if you have an existing account. Please register an account if you do not.");
		System.out.print("1: Login\t2: Register\nYour choice: ");
		String command = in.nextLine().toLowerCase();
		switch (command) {
		case "1":
			state = LOG_IN;
			break;
		case "login":
			state = LOG_IN;
			break;
		case "2":
			state = REGISTER;
			break;
		case "register":
			state = REGISTER;
			break;
		case "q":
			state = QUIT;
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
		}
	}

	private void login() {
		System.out.print("Please enter your username: ");
		String username = in.nextLine();
		System.out.print("Please enter your phone number (no dashes): ");
		String phoneNumber = in.nextLine();
		User login = User.login(connection, username, phoneNumber);
		if (login == null) {
			System.out.print("Would you like to try again? Y/N: ");
			String res = in.nextLine();
			if (!res.toLowerCase().equals("y") && !res.toLowerCase().equals("yes")) {
				state = LOGIN_OR_REGISTER;
			} else {
				state = LOG_IN;
			}
		} else {
			user = login;
			state = LOGGED_IN;
		}
	}

	private void register() {
		boolean attempt = false;
		User newUser = new User();
		try {
			System.out.print("Please enter a username: ");
			String username = in.nextLine();
			newUser.setUsername(username);
			System.out.print("Please enter your phone number (no dashes): ");
			String phoneNumber = in.nextLine();
			if (!phoneNumber.matches("^(\\d{7}|\\d{10})$")) {
				throw new Exception("Phone numbers should be 7 or 10 digits only.");
			}
			newUser.setPhoneNumber(phoneNumber);
			System.out.print("Please enter your annual income: $");
			int income = Integer.parseInt(in.nextLine());
			newUser.setIncome(income);
			attempt = newUser.createUser(connection);
		} catch (NumberFormatException n) {
			System.out.println("Your income should only be numbers.");
		} catch (Exception e) {
			// System.out.println("Something went wrong when trying to register
			// new user.");
			System.out.println(e.getMessage());
		}

		if (attempt) {
			state = LOGIN_OR_REGISTER;
		} else {
			System.out.print("Would you like to try again? Y/N: ");
			String res = in.nextLine();
			if (!res.toLowerCase().equals("y") && !res.toLowerCase().equals("yes")) {
				state = LOGIN_OR_REGISTER;
			}
		}
	}

	private void loggedIn() {
		if (user == null) {
			System.out.println("Invalid user.");
			state = QUIT;
			return;
		}
		System.out.println(String.format("Hello, %s! What would you like to do?", user.getUsername()));
		System.out.print(
				"0: View/update my information\n" + 
				"1: Search for homes\n" +
				"2: Search for agents\n" +
				"3: Find out more about our agencies\n" +
				"4: Delete account\n" +
				"5: Find out more about our agents\n" +
				"6: Find out affordable houses\n" +
				"7: Find out appointments\n" +
				"Q: Logout\nYour choice: ");
		String command = in.nextLine().toLowerCase();
		switch (command) {
		case "0":
			promptUpdateInfo();
			break;
		case "1":
			state = SEARCH_FOR_HOMES;
			searchForHomes();
			break;
		case "2":
			searchForAgents();
			break;
		case "3":
			this.agencyPrompt.promptUser();
			break;
		case "4":
			promptDeleteAccount();
			break;
		case "5":
			this.agentPrompt.promptUser();
			break;
		case "6":
			this.userPrompt.promptAffordableHome(user.getIncome());
			break;
		case "7":
			this.apptPrompt.promptUser(user.getUsername());
			break;
		case "q":
			this.logout();
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
		}
	}

	private void adminLoggedIn() {
		System.out.println("Hello, Admin! What would you like to do?");
		System.out.print(
				"0: View/update my information\n"
				+ "1: Search for homes\n"
				+ "2: Search for agents\n"
				+ "3: Find out more about our agencies\n"
				+ "4: Delete account\n"
				+ "5: Find out more about our agents\n"
				+ "6: Archive user\n"
				+ "7: More about user\n"
				+ "8: More about houses\n"
				+ "Q: Logout\nYour choice: ");
		String command = in.nextLine().toLowerCase();
		switch (command) {
		case "0":
			promptUpdateInfo();
			break;
		case "3":
			this.agencyPrompt.promptAdmin();

			break;
		case "4":
			promptDeleteAccount();
			break;
			
		case "5":
			this.agentPrompt.promptAdmin();
			break;

		case "6":
			this.archivePrompt.promptAdmin();
			break;

		case "7":
			this.adminUserPrompt.promptAdmin();
			break;

		case "8":
			this.housePrompt.promptAdmin();
			break;

		case "Q":
			this.logout();
			state = QUIT;
			break;
		case "q":
			state = QUIT;
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
		}
	}
	private void promptUpdateInfo() {
		if (user == null) {
			System.out.println("Invalid user.");
			state = QUIT;
			return;
		}
		System.out.println("Here is your current personal information.");
		System.out.println("Username: " + user.getUsername());
		System.out.println("Phone number: " + user.getPhoneNumber());
		System.out.println("Income: $" + user.getIncome());
		System.out.print("Assigned agent's ID: ");
		if (user.getAgentID() == 0) {
			System.out.println("No agent assigned.");
		} else {
			System.out.println(user.getAgentID());
		}
		System.out.print("Would you like to update your information? Y/N: ");
		String res = in.nextLine().toLowerCase();
		switch (res) {
		case "y":
			updatingInfo();
			break;
		case "yes":
			updatingInfo();
			break;
		case "n":
			break;
		case "no":
			break;
		default:
			System.out.println("Unrecognized input.");
		}
	}

	private void updatingInfo() {
		if (user == null) {
			System.out.println("Invalid user.");
			state = QUIT;
			return;
		}
		try {
			System.out.print("New username: ");
			String newUsername = in.nextLine();
			if (newUsername.isEmpty()) {
				newUsername = user.getUsername();
			}
			System.out.print("New phone number (no dashes): ");
			String newPhoneNumber = in.nextLine();
			if (newPhoneNumber.isEmpty()) {
				newPhoneNumber = user.getPhoneNumber();
			} else if (!newPhoneNumber.matches("^(\\d{7}|\\d{10})$")) {
				throw new Exception("Phone numbers should be 7 or 10 digits only.");
			}
			System.out.print("Updated income: ");
			int newIncome;
			String newIncomeLine = in.nextLine();
			if (newIncomeLine.isEmpty()) {
				newIncome = user.getIncome();
			} else {
				newIncome = Integer.parseInt(newIncomeLine);
			}
			System.out.print("Remove current agent? Y/N: ");
			String res = in.nextLine().toLowerCase();
			if (newUsername.isEmpty()) {
				System.out.println("Username cannot be empty.");
			} else if (!newPhoneNumber.matches("^(\\d{7}|\\d{10})$")) {
				throw new Exception("Phone numbers should be 7 or 10 digits only.");
			} else if ("y".equals(res) || "yes".equals(res)) {
				user.updateUserInfo(connection, newUsername, newPhoneNumber, newIncome, true);
			} else {
				user.updateUserInfo(connection, newUsername, newPhoneNumber, newIncome, false);
			}
		} catch (NumberFormatException n) {
			System.out.println("Your income should only be numbers.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateInfoInDB() {

	}

	private void promptDeleteAccount() {
		System.out.print("Are you sure you want to delete your account?" + "\nIf you want to delete, type [delete]: ");
		String res = in.nextLine().toLowerCase();
		if (res.equals("delete")) {
			user.deleteThisUser(connection);
			logout();
		} else {
			System.out.println("Canceled deleting account.");
		}
	}

	private void logout() {
		this.state = LOGIN_OR_REGISTER;
		user = null;
		System.out.println("You've logged out.");
	}

	private void searchForHomes() {
		try {
			System.out.println("What kind of unit are you looking for? (Leave blank for no preference)");
			System.out.print("Apartment/House/Condo: ");
			String houseType = in.nextLine();

			System.out.print("Minimum rent per month: $");
			String minCos = in.nextLine();
			int minCost;
			if (!minCos.matches("[0-9]*")) {
				throw new Exception("Minimum cost is not numeric.");
			} else {
				minCost = convertToNumber(minCos);
			}

			System.out.print("Maximum rent per month: $");
			String maxCos = in.nextLine();
			int maxCost;
			if (!maxCos.matches("[0-9]*")) {
				throw new Exception("Maximum cost is not numeric.");
			} else {
				maxCost = convertToNumber(maxCos);
			}

			System.out.print("City: ");
			String city = in.nextLine();

			System.out.print("State: ");
			String state = in.nextLine().toUpperCase();

			System.out.print("Zipcode: ");
			String zip = in.nextLine();
			int zipcode;
			if (!zip.matches("^(\\d{5})$") && !zip.matches("")) {
				throw new Exception("Zipcode should be 5 numbers.");
			} else {
				zipcode = convertToNumber(zip);
			}

			System.out.print("Minimum bedrooms: ");
			String minBedroom = in.nextLine();
			int minBedroomCount;
			if (!minBedroom.matches("[0-9]*")) {
				throw new Exception("Minimum bedroom count is not numeric.");
			} else {
				minBedroomCount = convertToNumber(minBedroom);
			}

			System.out.print("Maximum bedroom: ");
			String maxBedroom = in.nextLine();
			int maxBedroomCount;
			if (!maxBedroom.matches("[0-9]*")) {
				throw new Exception("Maximum bedroom count is not numeric.");
			} else {
				maxBedroomCount = convertToNumber(maxBedroom);
			}

			System.out.print("Minimum bathroom count: ");
			String minBathroom = in.nextLine();
			int minBathroomCount;
			if (!minBathroom.matches("[0-9]*")) {
				throw new Exception("Minimum bathroom count is not numeric.");
			} else {
				minBathroomCount = convertToNumber(minBathroom);
			}

			System.out.print("Maximum bathroom count: ");
			String maxBathroom = in.nextLine();
			int maxBathroomCount;
			if (!maxBathroom.matches("[0-9]*")) {
				throw new Exception("Maximum bathroom count is not numeric.");
			} else {
				maxBathroomCount = convertToNumber(maxBathroom);
			}

			System.out.print("Oldest year built: ");
			String miYear = in.nextLine();
			int minYear;
			if (!miYear.matches("[0-9]*")) {
				throw new Exception("Invalid year.");
			} else {
				minYear = convertToNumber(miYear);
			}

			System.out.print("Newest year built: ");
			String maYear = in.nextLine();
			int maxYear;
			if (!maYear.matches("[0-9]*")) {
				throw new Exception("Invalid year.");
			} else {
				maxYear = convertToNumber(maYear);
			}

			ArrayList<House> result = House.searchHomes(connection, houseType, minCost, maxCost, city, state, zipcode,
					minBedroomCount, maxBedroomCount, minBathroomCount, maxBathroomCount, minYear, maxYear);

			System.out.println("\nSearch parameters yielded " + result.size() + " results.");
			if (result.size() > 0) {
				System.out.println();
				System.out.printf("%-15s %-10s %-30s %-20s %-7s %-10s %-10s %-10s %-15s %-12s %-15s", "House Type",
						"House ID", "Street", "City", "State", "Zipcode", "Bedrooms", "Bathrooms", "Square Feet",
						"Year Built", "Monthly Cost");
				System.out.println();
				for (House h : result) {
					System.out.println(h.toString());
				}
				System.out.print("\nInterested in a home? Please enter the House ID to contact the agent: ");
				String house = in.nextLine();
				if (!house.matches("[0-9]*")) {
					throw new Exception("A House ID should be numeric.");
				} else if (!house.isEmpty()) {
					int houseID = Integer.parseInt(house);
					House houseTarget = null;
					for (House h : result) {
						if (h.getHouseID() == houseID) {
							houseTarget = h;
							break;
						}
					}
					if (houseTarget == null) {
						System.out.println("No such house exists in the search result.");
					} else {
						/*
						 * TODO Create getAgent method
						Agent agent = houseTarget.getAgent(connection);
						if (agent != null) {
							System.out.println("\nThis is the agent associated with the house.");
							System.out.println(agent.toString());
							System.out.printf("%-15s %-10s %-30s %-20s %-7s %-10s %-10s %-10s %-15s %-12s %-15s",
									"House Type", "House ID", "Street", "City", "State", "Zipcode", "Bedrooms",
									"Bathrooms", "Square Feet", "Year Built", "Monthly Cost");
							System.out.println("\n" + houseTarget.toString() + "\n");
							System.out.println(
									"Would you like to schedule an appointment to speak to this agent regarding this home?");
						}
						*/
					}
				}
			} else {
				System.out.println("Please check your search parameters.");
			}
			System.out.println();

		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// bedroomCount INT, bathroomCount INT, squareFeet DOUBLE, agentID INT,
		// houseID INT AUTO_INCREMENT, FOREIGN KEY(agentID) REFERENCES
		// Agent(agentID) ON DELETE CASCADE, PRIMARY KEY(houseID)

		System.out.print("Would you like to perform another search? Y/N: ");
		String res = in.nextLine();
		if (!res.toLowerCase().equals("y") && !res.toLowerCase().equals("yes")) {
			state = LOGGED_IN;
		} else {
			state = SEARCH_FOR_HOMES;
		}
	}

	private int convertToNumber(String str) {
		if (str.isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	private void searchForAgents() {

	}

	private void promptAdmin() {
		System.out.print("Please enter admin password: ");
		String password = in.nextLine();
		if (password.equals(this.ADMIN_PW)) {
			System.out.print("Please enter admin password: ");
			this.state = this.ADMIN_LOGGED_IN;
		} else {
			System.out.println("Incorrect password");
			state = QUIT;
		}
	}
}
