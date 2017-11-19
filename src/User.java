import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class User {
	private String username;
	private String phoneNumber;
	private int income;
	private int agentID;
	private int userID;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public boolean createUser(Connection conn) {
		DataSource ds = DataSourceFactory.getMySQLDataSource();
		Connection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
