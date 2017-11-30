import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class House {
	
	private String houseType;
	private String street;
	private String city;
	private String stateOfResidence;
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

	public String getStateOfResidence() {
		return stateOfResidence;
	}

	public void setStateOfResidence(String house_state) {
		stateOfResidence = house_state;
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

}
