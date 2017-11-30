/**
 * Takes care of table RealEstateCompany
 * Static class?
 * @author yk
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RealEstateCompany {
	private static int state;
	private static final int QUIT = -1;
	private static final int SEARCH_BY_NAME = 0;
	private static final int MODIFY = 1;
	private static final int DELETE = 2;
	private static final int CREATE = 3;

	/**
	 * To prevent from being instantiated
	 * @param args
	 */
	private RealEstateCompany() {}

	/** 
	 * Prompt non-admin user
	 * User can do 
	 * search
	 * for Real Estate Company
	 * 
	 * @param connection	To use MySQL
	 */
	public static void promptUser(Connection connection) {
		while (state != QUIT) {
			try {
				state = selectState();
				switch (state) {
				case SEARCH_BY_NAME:
					System.out.println("In search state");
					break;
				default:
					System.out.println("Unrecognized input, please try again.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Select state
	 */
	private static int selectState() {
		int tempState = -2;
		System.out.print("What would you like to do about agencies?\n"
				+ "0: Search agencies by name\n"
				+ "B: Go back to main page\n"
				+ "Your choice: ");
		Scanner in = new Scanner(System.in);
		// in.nextLine();
		String command = in.nextLine().toLowerCase();
		switch (command) {
		case "0":
			tempState = SEARCH_BY_NAME;
			break;
		case "b":
			tempState = QUIT;
			break;
		default:
			System.out.println("Unrecognized input, please try again.");
		}
		in.close();
		return tempState;
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
	 */
	public static void promptAdmin(Connection connection) {
		System.out.println("What would you like to do about agencies as admin user?");
	}
	/**
	 * Create an agency in database
	 * @param phoneNum
	 * @param name
	 * @return true when an agency created, false when something went wrong
	 */
	private static boolean createCompany(String phoneNum, String name) {
		return false;
		
	}
	
	/**
	 * Prompt user to type agency name that the user
	 * wants to search.
	 * Given agency name, search agency
	 * @param	name agency name to be searched
	 */
	private static void promptSearch(
			Connection connection, 
			String name) {
		
	}

	/**
	 * 
	 * @param connection 	database connection
	 * @param name	Name of agency to be searched
	 * @return	query result contains Agency information
	 */
	private static ResultSet searchAngecyByName(
			Connection connection,
			String name) {
		return null;
		
	}
	
	
	private static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
