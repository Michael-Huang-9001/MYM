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
	 * Create an agency in database
	 * @param phoneNum
	 * @param name
	 * @return true when an agency created, false when something went wrong
	 */
	private static boolean createCompany(String phoneNum, String name) {
		return false;
		
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
