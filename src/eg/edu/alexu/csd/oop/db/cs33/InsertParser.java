package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InsertParser {
 
	private String[] querySplit;
	private String operation;
	private int len;
	private String condition;
 
	public InsertParser(String s ,String op) {
		
		this.operation = op;
		if (op == "DELETE" || op == "UPDATE") {
			this.condition = getCond(s);
		}
		s = s.toUpperCase();
		this.querySplit = s.split("[\\s(,);=]+");
		this.len = querySplit.length;
	}
	
	public Map<String,String> getMap() {
		if (operation == "UPDATE") {
		 
			// iterator that indicates the element after SET in the query (UPDATE table_name SET col1=val1 ,col2=val2,... WHERE condition )
			int i = 3; 
	     
			Map<String,String> setMap = new HashMap<String,String>();
			while (i < len && !querySplit[i].contains("WHERE") ) {
				setMap.put(querySplit[i], querySplit[i+1]);
				i += 2 ;
			}
			return setMap;
		}
		else if (operation == "INSERT") {
		 
			Map<String,String> map = new HashMap<String,String>();
			if (querySplit[3].contains("VALUE")) {
				// TODO: 
				return null;
			}
			else {
				int i = 3 ;
				ArrayList<String> list = new ArrayList<String>();
				while (!querySplit[i].contains("VALUE")) {
					list.add(querySplit[i]);
					i++;
				}
				i++; 
				int size = list.size();
				int j = 0 ;
				if (len-i != size) {
					System.out.println("Number of keys not equal number of values");
					return null;
				}
				while (i < len && j< size) {
					map.put(list.get(j), querySplit[i]);
					i++;
					j++;
				}
				return map;
			}
		}
		else {
			return null ;
		}
	}
	
	public String getName() {
		if (operation == "DELETE" || operation == "INSERT") 
			return querySplit[2];
		else
			return querySplit[1];
	}
	
	private String getCond(String str) {
		
		if (str.contains("WHERE")) {
			return str.substring(str.indexOf(" WHERE "), str.length());
		}
		else {
			return "";
		} 
	}
	
	public String getCondition() {
		return condition;
	}
}
