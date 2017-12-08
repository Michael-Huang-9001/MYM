/**
 * Archive inactive user
 * by calling stored procedure
 * @author yk
 */

import java.sql.*;


public class AdminUserDB {
	private static AdminUserDB instance = null;
	private Connection connection;

	public static AdminUserDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new AdminUserDB(conn);
		}
		return instance;
	}

	private AdminUserDB(Connection conn) {
		this.connection = conn;
	}

	public ResultSet searchUserByIncome (String incomeMin, String incomeMax) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * FROM User WHERE User.income BETWEEN ? and ?";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setInt(1, Integer.valueOf(incomeMin));
			preparedStatement.setInt(2, Integer.valueOf(incomeMax));

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
