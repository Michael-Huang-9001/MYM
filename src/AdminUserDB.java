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
}
