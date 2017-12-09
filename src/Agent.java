import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Agent {

	private String agentName;
	private String phoneNumber;
	private int agentID;
	private int agencyID;

	public Agent() {
		
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *            the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	
	/**
	 * @return the agentID
	 */
	public int getAgentID() {
		return agentID;
	}

	/**
	 * @param agentID
	 *            the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * @return the agencyID
	 */
	public int getAgencyID() {
		return agencyID;
	}

	/**
	 * @param agencyID
	 *            the agencyID to set
	 */
	public void setAgencyID(int agencyID) {
		this.agencyID = agencyID;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return String.format("Agent: %s Phone Number: %s", agentName, phoneNumber);
	}
	
	public String getAgency(Connection connection) {
		String query = "SELECT agencyName FROM Agent NATURAL JOIN RealEstateCompanies WHERE agentID = ?;";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, agentID);
			ResultSet result = statement.executeQuery();
			if (result.first()) {
				return result.getString("agencyName");
			} else {
				throw new Exception("No agency is assigned for this agent in the database.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
