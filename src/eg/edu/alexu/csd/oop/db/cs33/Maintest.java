package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eg.edu.alexu.csd.oop.db.Database;

public class Maintest {

	public static void main(String[] args) throws SQLException {
		
		Database d = new DatabaseImp();
		
		String query1 = "CREATE TABLE Persons("
				+ "FirstName varchar,"
				+ "ID int"
				+ ");";
		
		System.out.println("First query: "+d.executeStructureQuery(query1));
		
//		String query2 = "Create TABLE Cars("
//				+ "Model varchar,"
//				+ "Year int"
//				+ ");";
//		
//		System.out.println("Second query: "+d.executeStructureQuery(query2));
//		
//		for(MyTable table : d.getTables())
//		{
//			table.showName();
//			table.showValidColumns();
//		}
//		
//		String query3 = "drop table Cars;";
//		
//		System.out.println("Third query: "+d.executeStructureQuery(query3));
//		
//		System.out.println();
//		System.out.println();
//		for(MyTable table : d.getTables())
//		{
//			table.showName();
//			table.showValidColumns();
//		}
//		
//		String queryInsert = "INSERT INTO Persons (FirstName,ID)"
//				+ "VALUES ('Youssef Sherif',10);";
//		
//		System.out.println("Insert query: "+d.executeUpdateQuery(queryInsert));
//		
//				
		
}
}