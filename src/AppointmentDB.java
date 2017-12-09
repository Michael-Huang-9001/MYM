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

	/**
	 * 
	 * @param userName
	 * @param agentId
	 * @param houseId
	 * @param dateTime
	 * @return True if created false if failed
	 */
	public boolean insertAppt (
			String userName,
			String agentId,
			String houseId,
			String dateTime
			) {

		PreparedStatement preparedStatement = null;
		try {
			String sql = null;
			sql = "INSERT INTO Appointments(userName, agentID, houseID, date_time) "
					+ "VALUE(?, ?, ?, ?);";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, userName);
			preparedStatement.setInt(2, Integer.valueOf(agentId));
			preparedStatement.setInt(3, Integer.valueOf(houseId));
			preparedStatement.setString(4, dateTime);

			// Execute
			preparedStatement.executeUpdate();

			// printResultSetfromFaculty(rs);
			return true;
		} catch (SQLException e) {
			PrintErrorMessage.PrintMessage(e.getSQLState());
			return false;
		}
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
