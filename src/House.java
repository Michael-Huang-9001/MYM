import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

public class House {

	private String houseType;
	private String street;
	private String city;
	private String state;
	private int zipcode;
	private int year;
	private int cost;
	private int bedroomCount;
	private int bathroomCount;
	private double squareFeet;
	private int agentID;
	private int houseID;

	public House() {

	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String house_type) {
		houseType = house_type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String house_street) {
		street = house_street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String house_city) {
		city = house_city;
	}

	public String getState() {
		return state;
	}

	public void setState(String house_state) {
		state = house_state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int house_year) {
		year = house_year;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int house_cost) {
		cost = house_cost;
	}

	public int getBedroomCount() {
		return bedroomCount;
	}

	public void setBedroomCount(int house_bed_count) {
		bedroomCount = house_bed_count;
	}

	public int getBathroomCount() {
		return bathroomCount;
	}

	public void setBathroomCount(int house_bath_count) {
		bathroomCount = house_bath_count;
	}

	public double getSquareFeet() {
		return squareFeet;
	}

	public void setSquareFeet(double house_squ_ft) {
		squareFeet = house_squ_ft;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agent_id) {
		agentID = agent_id;
	}

	public int getHouseID() {
		return houseID;
	}

	public void setHouseID(int house_id) {
		houseID = house_id;
	}

	public String toString() {
		return String.format("%-15s %-30s %-20s %-7s %-10s %-10s %-10s %-12s %-15s", houseType, street, city, state,
				zipcode, bedroomCount, bathroomCount, year, "$" + cost);
	}

	public static ArrayList<House> searchHomes(Connection connection, String houseType, int minCost, int maxCost,
			String city, String state, int zipcode, int minBedroom, int maxBedroom, int minBathroom, int maxBathroom,
			int minYear, int maxYear) {
		// house, costs, city, state, zip, bedroom, bathroom,year
		try {
			String query = "SELECT * FROM HOUSE";

			if (houseType.isEmpty()) {
				query += " WHERE houseType LIKE '%'";
			} else {
				query += " WHERE houseType LIKE '" + houseType + "'";
			}

			if (minCost != 0 && maxCost != 0) {
				query += " AND cost BETWEEN " + minCost + " AND " + maxCost;
			}

			if (!city.isEmpty()) {
				query += " AND city LIKE '" + city + "'";
			}

			if (!state.isEmpty()) {
				query += " AND state LIKE '" + state + "'";
			}

			if (zipcode != 0) {
				query += " AND zipcode LIKE " + zipcode;
			}

			if (minBedroom != 0 && maxBedroom != 0) {
				query += " AND bedroomCount BETWEEN " + minBedroom + " AND " + maxBedroom;
			}

			if (minBathroom != 0 && maxBathroom != 0) {
				query += " AND bathroomCount BETWEEN " + minBathroom + " AND " + maxBathroom;
			}

			if (minYear != 0 && maxYear != 0) {
				query += " AND year BETWEEN " + minYear + " AND " + maxYear;
			}

			query += " ORDER BY houseType ASC, city ASC;";
			// System.out.println(query);
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			ArrayList<House> search = new ArrayList<House>();
			while (result.next()) {
				House house = new House();
				house.setHouseType(result.getString("houseType"));
				house.setStreet(result.getString("street"));
				house.setCity(result.getString("city"));
				house.setState(result.getString("state"));
				house.setZipcode(result.getInt("zipcode"));
				house.setYear(result.getInt("year"));
				house.setCost(result.getInt("cost"));
				house.setBedroomCount(result.getInt("bedroomCount"));
				house.setBathroomCount(result.getInt("bathroomCount"));
				house.setSquareFeet(result.getDouble("squareFeet"));
				house.setAgentID(result.getInt("agentID"));
				house.setHouseID(result.getInt("houseID"));
				search.add(house);
			}
			return search;
		} catch (SQLException e) {
			System.out.println("Could not log in.");
			// System.out.println(e.getErrorCode());
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

	}

}