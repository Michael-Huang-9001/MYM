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
}
