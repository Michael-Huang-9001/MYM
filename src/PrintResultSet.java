import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintResultSet {
	private PrintResultSet() {};

	/**
	 * Output the result sets to stdout
	 * @param rs ResutSet containing agencies' information
	 */
	public static void displayHouse(ResultSet rs, String successMessage, String errorMessage) {
		try {
			rs.beforeFirst();
			if (!rs.isBeforeFirst()) {
				System.out.println(errorMessage);
				return;
			} else {
				System.out.println(successMessage);
				System.out.println("-----------------------");
				System.out.println(
						"houseID\t"
						+ "houseType\t"
						+ "street\t"
						+ "city\t"
						+ "state\t"
						+ "zipcode\t"
						+ "year\t"
						+ "cost\t"
						+ "bedroomCount\t"
						+ "bathroomCount\t"
						+ "squareFeet\t"
						+ "street"
						); 
			}
			while(rs.next())
			{
				String houseType = rs.getString("houseType"); 
				String street = rs.getString("street"); 
				String city = rs.getString("city"); 
				String state = rs.getString("state"); 
				String zipcode = rs.getString("zipcode"); 
				int year = rs.getInt("year"); 
				int cost = rs.getInt("cost"); 
				int bedroomCount = rs.getInt("bedroomCount"); 
				int bathroomCount = rs.getInt("bathroomCount"); 
				double squareFeet = rs.getDouble("squareFeet"); 
				int houseID = rs.getInt("houseID"); 

				System.out.println(
						houseID + "\t" + 
						houseType + "\t" + 
						street + "\t" + 
						city + "\t" + 
						state + "\t" + 
						zipcode + "\t" + 
						year + "\t" + 
						cost + "\t" + 
						bedroomCount + "\t" + 
						bathroomCount + "\t" + 
						squareFeet 
						); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
