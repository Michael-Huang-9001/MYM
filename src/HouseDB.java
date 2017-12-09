/**
 * Archive inactive user
 * by calling stored procedure
 * @author yk
 */

import java.sql.*;


public class HouseDB {
	private static HouseDB instance = null;
	private Connection connection;

	public static HouseDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new HouseDB(conn);
		}
		return instance;
	}

	private HouseDB(Connection conn) {
		this.connection = conn;
	}

	public ResultSet searchHouseByApptCount (String count) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM House h "
					+ "WHERE EXISTS ( "
					+ "SELECT houseID, COUNT(userName) AS count "
					+ "FROM Appointments "
					+ "WHERE h.houseID = Appointments.houseID "
					+ "GROUP BY houseID "
					+ "HAVING count >= ?)"; 
			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setInt(1, Integer.valueOf(count));

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
