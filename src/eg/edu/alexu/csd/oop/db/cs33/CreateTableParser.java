package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateTableParser {
	
	//* Creates a map of all columns in the table with their equivalent data type
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
						temp[0] = temp[0].replace(")", "");
						temp[0] = temp[0].replace(";", "").trim();
						
						temp[1] = temp[1].replace(")", "");
						temp[1] = temp[1].replace(";", "").trim();

						validMap.put(temp[0], temp[1].toUpperCase());
					}
					
				}
				
				return validMap;
			}
			
			public ArrayList<String> createArrayList(String query)
			{
				String[] results = query.split("\\(",2);
				query = results[1];
				
				results = query.split("\\)",2);
				query = results[0];
				
				results = query.split("\\,");
				ArrayList<String> arr = new ArrayList<String>();

				for(String s :results)
				{
					s=s.trim();
					String[] temp = s.split(" ");
					
					if(temp.length==2)
					{
						arr.add(temp[0]);
					}
					
				}
				
				return arr;
				
			}
			
			public String nameGetter(String query)
			{
				String[] results = query.split("\\(",2);
				query = results[0].trim();
				results = query.split(" ");
				
				return results[results.length-1];
			}
			
			//*Returns the name of the table in DROP TABLE statement
			public String nameGetterDrop(String query)
			{
								
				Pattern pattern = Pattern.compile("table",Pattern.CASE_INSENSITIVE);
				Matcher m = pattern.matcher(query);
				if(m.find())
				{
					query = query.substring(m.end(),query.length()-1);
				}
				
				return query.replace(";", "").trim();
				
			}

}
