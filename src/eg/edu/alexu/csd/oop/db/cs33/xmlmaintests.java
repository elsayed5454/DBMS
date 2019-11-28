package eg.edu.alexu.csd.oop.db.cs33;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class xmlmaintests {

	public static void main(String[] args) throws SQLException {
		Database db = new DatabaseImp();
		String query1 = "CREATE TABLE Persons("
				+ "FirstName varchar,"
				+ "ID int"
				+ ");";
		
		System.out.println("First query: "+db.executeStructureQuery(query1));
	}

}
