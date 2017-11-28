import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class HousingLookup {
	private Scanner in;
	private int state;
	static final int QUIT = -1;
	static final int ADMIN_OR_USER = 0;
	static final int LOGIN_OR_REGISTER = 1;
	static final int LOG_IN = 2;
	static final int REGISTER = 3;
	static final int LOGGED_IN = 4;
	static final int SEARCH_FOR_HOME = 999;
	static final int ADMIN_LOGIN = 100;
	private DataSource ds = DataSourceFactory.getMySQLDataSource();
	private PreparedStatement statement;
	private Connection connection;
	private User user;

	public static void main(String args[]) {
		HousingLookup housingApp = new HousingLookup();
	}

	public HousingLookup() {
		try {
			connection = ds.getConnection();
			statement = connection.prepareStatement("USE Housing_Lookup;");
			statement.executeUpdate();

			// PreparedStatement stmt=con.prepareStatement("insert into Emp
			// values(?,?)");
			// stmt.setInt(1,101);//1 specifies the first parameter in the query
			// stmt.setString(2,"Ratan");
			//
			// int i=stmt.executeUpdate();
			// System.out.println(i+" records inserted");

			state = ADMIN_OR_USER;
			in = new Scanner(System.in);

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
			sql.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("Database error.");
			}
		}
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
			System.out.print(
					"Would you like to try again? (Enter Y or yes to try again, enter any other input to return to login) ");
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
			System.out.println(e.getMessage());
		}

		if (attempt) {
			state = LOGIN_OR_REGISTER;
		} else {
			System.out.print(
					"Would you like to try again? Y/N: ");
			String res = in.nextLine();
			if (!res.toLowerCase().equals("y") && !res.toLowerCase().equals("yes")) {
				state = LOGIN_OR_REGISTER;
			}
		}
	}

	private void loggedIn() {
		System.out.println(String.format("Hello, %s! What would you like to do?", user.getUsername()));
		System.out.print(
				"0: View/update my information\t1: Search for homes\t2: Search for agents\t3: Find out more about our agencies\t4: Delete account\t5: Logout\nYour choice: ");
		String command = in.nextLine().toLowerCase();
		switch (command) {
		case "0":
			promptUpdateInfo();
			break;
		case "4":
			promptDeleteAccount();
			break;
		case "5":
			this.logout();
		case "q":
			state = QUIT;
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
		}
	}

	private void promptUpdateInfo() {
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
		System.out.print("New username (cannot be empty): ");
		String newUsername = in.nextLine();
		System.out.print("New phone number (no dashes, cannot be empty): ");
		String newPhoneNumber = in.nextLine();
		System.out.print("Updated income: ");
		int newIncome = Integer.parseInt(in.nextLine());
		System.out.print("Remove current agent? Y/N: ");
		String res = in.nextLine().toLowerCase();
		if (newUsername.isEmpty()) {
			System.out.println("Username cannot be empty.");
		} else if (newPhoneNumber.isEmpty() || newPhoneNumber.length() > 10) {
			System.out.println("Phone number cannot be empty and must be 10 numbers or less.");
		} else if ("y".equals(res) || "yes".equals(res)) {
			user.updateUserInfo(connection, newUsername, newPhoneNumber, newIncome, true);
		} else {
			user.updateUserInfo(connection, newUsername, newPhoneNumber, newIncome, false);
		}
	}

	private void updateInfoInDB() {

	}
	
	private void promptDeleteAccount() {
		System.out.print("Are you sure you want to delete your account?"
				+ "\nIf you want to delete, type [delete]: ");
		String res = in.nextLine().toLowerCase();
		if (res.equals("delete")) {
			user.deleteThisUser(connection);
			this.logout();
		} else {
			System.out.println("Canceled deleting account.");
		}
	}

	private void logout() {
		this.state = LOGIN_OR_REGISTER;
		user = null;
		System.out.println("You've logged out.");
	}
	private void promptAdmin() {
		System.out.print("Please enter your admin name: ");
		String adminName = in.nextLine();
	}
}
