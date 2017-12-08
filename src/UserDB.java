/**
 * Archive inactive user
 * by calling stored procedure
 * @author yk
 */

import java.sql.*;


public class UserDB {
	private static UserDB instance = null;
	private Connection connection;

	public static UserDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new UserDB(conn);
		}
		return instance;
	}

	private UserDB(Connection conn) {
		this.connection = conn;
	}

	public ResultSet searchAffordableHome (int income) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM House "
					+ "WHERE ? >= 2*cost;";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setInt(1, income);

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Output the result sets to stdout
	 * @param rs ResutSet containing agencies' information
	 */
	public void displayUser(ResultSet rs, String successMessage, String errorMessage) {
		try {
			rs.beforeFirst();
			if (!rs.isBeforeFirst()) {
				System.out.println(errorMessage);
				return;
			} else {
				System.out.println(successMessage);
				System.out.println("-----------------------");
				System.out.println("userName\tphoneNumber\tincome"); 
			}
			while(rs.next())
			{
				int income = rs.getInt("income"); 
				String userName = rs.getString("userName"); 
				String phoneNumber = rs.getString("phoneNumber"); 

				System.out.println(userName + "\t" + phoneNumber + "\t" + income); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
