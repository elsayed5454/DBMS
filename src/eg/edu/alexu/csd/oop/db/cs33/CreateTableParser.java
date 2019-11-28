package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateTableParser {
	
	// Split query in the constructor to retrieve its info
	private String[] querySplit;
	
	// CREATE TABLE tableName (Column1 data type, ..)
	// The 3rd index in array is the start of columns and their data types
	private final int startIndexOfColumns = 3;
	
	// Creates a map of all columns in the table with their data types
	private Map<String,String> columnsMap = new HashMap<String,String>();
	
	// Temporary list to preserve the order of the columns
	private List<String> columnsOrdered = new ArrayList<String>();
	
	public CreateTableParser(String query) {
		
		this.querySplit = query.split("[\\s(,);=]+");
		for (int i = startIndexOfColumns; i < querySplit.length; i+=2) {
			columnsMap.put(querySplit[i], querySplit[i + 1].toLowerCase());
			columnsOrdered.add(querySplit[i]);
		}
	}
	
	public Map<String,String> getColumnsMap() {
		
		return columnsMap;
	}
	
	public ArrayList<String> getOrderedColumns() {
		
		return (ArrayList<String>) columnsOrdered;
	}
	
	public String getName() {
		
		return querySplit[2];
	}
}
