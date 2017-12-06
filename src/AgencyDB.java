/**
 * Manipulate database related to Agency table
 * @author yk
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyDB {
	private static AgencyDB instance = null;
	private Connection connection;

	public static AgencyDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new AgencyDB(conn);
		}
		return instance;
	}

	private AgencyDB(Connection conn) {
		this.connection = conn;
	}

	/**
	 * Perform query on Agency by name
	 * @param name	Name of agency to be searched
	 * @return	query result contains Agency information
	 */
	public ResultSet searchAngecyByName(String name) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM RealEstateCompany "
					+ "WHERE agencyName=?;";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, name);

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Perform query on Agency by name and phonenumber
	 * @param name	Name of agency to be searched
	 * @return	query result contains Agency information
	 * @throws SQLException 
	 */
	public ResultSet searchAngecyByNameAndPhone(String name, String phoneNum) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String sql = null;
		sql = "SELECT * "
				+ "FROM RealEstateCompany "
				+ "WHERE agencyName=? AND phoneNumber=?;";

		preparedStatement = connection.prepareStatement(sql);

		// Set the name
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, phoneNum);

		// Execute
		rs = preparedStatement.executeQuery();

		printResultSetfromFaculty(rs);
		return rs;
	}

	/**
	 * Perform query on Agency
	 * Find agencies who has agents who can show houses located in a city
	 * @param city name of city
	 * @return	query result contains Agency information
	 */
	public ResultSet searchAngecyByCity(String city) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM RealEstateCompany "
					+ "WHERE agencyID IN ( "
					+ "SELECT agencyID "
					+ "FROM Agent LEFT JOIN House "
					+ "ON Agent.agentID = House.agentID "
					+ "WHERE city = ?);";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, city);

			// Execute
			rs = preparedStatement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Perform query on Agency by name
	 * Find agencies who has agents who can show houses that are build after a certain year
	 * @param year	year in int
	 * @return	query result contains Agency information
	 */
	public ResultSet searchAngecyByYear(int yaer) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM RealEstateCompany "
					+ "WHERE agencyID IN ( "
					+ "SELECT agencyID "
					+ "FROM Agent "
					+ "WHERE agentID IN ( "
					+ "SELECT agentID "
					+ "FROM House "
					+ "WHERE year < ?)"
					+ ");";
			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setInt(1, yaer);

			// Execute
			rs = preparedStatement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Create an agency in database
	 * @param phoneNum
	 * @param name
	 * @return	void
	 */
	public void create(String name, String phoneNum) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = null;

		sql = "INSERT INTO RealEstateCompany (phoneNumber, agencyName) VALUES (?, ?);";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, phoneNum);
		preparedStatement.setString(2, name);
		preparedStatement.executeUpdate();
	}

	/**
	 * Modify an agency in database
	 * @param phoneNum
	 * @param name
	 * @return	void
	 */
	public void modify(
			int aId, 
			String newName, 
			String newPhoneNum
			) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = null;

		sql = "UPDATE RealEstateCompany "
				+ "SET agencyName = ? , phoneNumber = ? "
				+ "WHERE agencyID = ?;";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, newName);
		preparedStatement.setString(2, newPhoneNum);
		preparedStatement.setInt(3, aId);
		preparedStatement.executeUpdate();
	}

	/**
	 * Delete agency in database
	 * @return	void
	 */
	public void delete(String name, String phoneNum) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = null;

		sql = "INSERT INTO RealEstateCompany (phoneNumber, agencyName) VALUES (?, ?);";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, phoneNum);
		preparedStatement.setString(2, name);
		preparedStatement.executeUpdate();
	}

	private static void printResultSetfromFaculty(ResultSet rs) throws SQLException
	{
		while(rs.next())
		{
			String phoneNumber = rs.getString("phoneNumber"); 
			String agencyName = rs.getString("agencyName"); 
			int id = rs.getInt("agencyID");
			System.out.println("ID:" + id + " Name:" + agencyName + " phone#: " + phoneNumber); 
		}
	}
}
