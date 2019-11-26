package eg.edu.alexu.csd.oop.db.cs33;

import java.util.HashMap;
import java.util.Map;

public class CreateTableParser {
	
	//* Creates a map of all columns in the table with the equivalent data type
	public Map<String,String> createValidMap(String query)
	{
		String[] results = query.split("\\(",2);
		query = results[1];
		results = query.split("\\,");
		
		Map<String,String> validMap = new HashMap<String,String>();
		
		for (String s : results)
		{
			s=s.trim();
			String[] temp = s.split(" ");
			
			if(temp.length==2)
			{
				validMap.put(temp[0], temp[1]);
			}
			
		}
		
		return validMap;
	}
	
	public String nameGetter(String query)
	{
		String[] results = query.split("\\(",2);
		query = results[0].trim();
		results = query.split(" ");
		
		return results[results.length-1];
	}

}
