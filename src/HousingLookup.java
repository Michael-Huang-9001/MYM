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

	public static void main(String args[]) {
		HousingLookup housingApp = new HousingLookup();
	}

	public HousingLookup() {
		try {
			state = ADMIN_OR_USER;
			in = new Scanner(System.in);
			String command = "";

			System.out.println("Welcome to Housing Lookup! Enter 'q' to quit the application at any time.");
			while (state != QUIT) {
				try {
					System.out.println("State = " + state);
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
					}
					System.out.println("----------------");
				} catch (Exception e) {

				}
			}
			System.out.println("Thank you for using Housing Lookup! We hope to see you again!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
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
		System.out.println("Please enter your username: ");
		String username = in.nextLine();
		System.out.print("Please enter your phone number (no dashes): ");
		String phoneNumber = in.nextLine();

		state = QUIT;
	}

	private void register() {
		System.out.print("Please enter a username: ");
		String username = in.nextLine();
		System.out.print("Please enter your phone number (no dashes): ");
		String phoneNumber = in.nextLine();
		System.out.print("Please enter your annual income: $");
		int income = in.nextInt();
		
	}

	private void adminLogin() {
		System.out.print("Please enter your admin name: ");
		String adminName = in.nextLine();
	}
}