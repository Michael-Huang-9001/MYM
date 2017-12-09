/**
 * Archive inactive user
 * by calling stored procedure
 * @author yk
 */

import java.sql.*;


public class AppointmentDB {
	private static AppointmentDB instance = null;
	private Connection connection;

	public static AppointmentDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new AppointmentDB(conn);
		}
		return instance;
	}

	private AppointmentDB(Connection conn) {
		this.connection = conn;
	}

	public ResultSet searchApptByUserName (String userName) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM Appointments A "
					+ "WHERE EXISTS ( "
					+ "SELECT * "
					+ "FROM User "
					+ "WHERE User.userName = A.userName AND "
					+ "User.userName = ?);";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, userName);

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
