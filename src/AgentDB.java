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

	public ResultSet searchByPrice (String price) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM Agent "
					+ "WHERE agentID IN ( "
					+ "SELECT agentID "
					+ "FROM House "
					+ "WHERE cost < ?);";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, price);

			// Execute
			rs = preparedStatement.executeQuery();

			// printResultSetfromFaculty(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	public ResultSet searchByIncome (String price) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = null;
			sql = "SELECT * "
					+ "FROM Agent, User "
					+ "WHERE Agent.agentID = User.agentID AND User.income*12 > ?;";

			preparedStatement = connection.prepareStatement(sql);

			// Set the name
			preparedStatement.setString(1, price);

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
	public void displayAget(ResultSet rs, String successMessage, String errorMessage) {
		try {
			rs.beforeFirst();
			if (!rs.isBeforeFirst()) {
				System.out.println(errorMessage);
				return;
			} else {
				System.out.println(successMessage);
				System.out.println("-----------------------");
				System.out.println("ID\tAgent Name\tPhone#"); 
			}
			while(rs.next())
			{
				int agentID = rs.getInt("agentID"); 
				String phoneNumber = rs.getString("phoneNumber"); 
				String agentName = rs.getString("agentName"); 

				System.out.println(agentID + "\t" + agentName + "\t" + phoneNumber); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
