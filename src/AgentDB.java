/**
 * Archive inactive user
 * by calling stored procedure
 * @author yk
 */

import java.sql.*;


public class AgentDB {
	private static AgentDB instance = null;
	private Connection connection;

	public static AgentDB getInstance(Connection conn) {
		if (instance == null) {
			instance = new AgentDB(conn);
		}
		return instance;
	}

	private AgentDB(Connection conn) {
		this.connection = conn;
	}

	public ResultSet searchUserInactiveAfter(String cutOffDate) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT userName, phoneNumber, income, agentID, updatedAt "
					+ "FROM User "
					+ "WHERE updatedAt < ?;";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, cutOffDate);

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void callStoredProc(String date) {
		String sql = "{CALL archiveInactiveUser(?)}";

		CallableStatement cs;
		try {
			cs = this.connection.prepareCall(sql);
			cs.setString(1,date);
			cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
