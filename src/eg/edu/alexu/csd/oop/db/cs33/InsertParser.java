package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InsertParser {
 
 private String[] query;
 private String operation ;
 private int len ;
 private String condition;
 
 public InsertParser(String s ,String op) {
	 this.operation = op;
	 if (op == "DELETE" || op == "UPDATE") {
		 this.condition = getCond(s);
	 }
	 s = s.toUpperCase();
	 this.query = s.split("[\\s(,);=]+");
	 this.len = query.length;
 }
 public String getName() {
	 if (operation == "DELETE" || operation == "INSERT") 
		 return query[2];
	 else
		 return query[1];
 }
 public Map<String,String> getMap(){
	 if (operation == "UPDATE") {
		 
		 int i = 3; // iterator that indicates the element after SET in the query (UPDATE table_name SET col1=val1 ,col2=val2,... WHERE condition )
	     
	     Map<String,String> setMap = new HashMap<String,String>();
	     
	     while (  i < len && (!query[i].contains("WHERE")) ) {
	    	 setMap.put(query[i], query[i+1]);
	    	 i += 2 ;
	     }
	     return setMap ;
	 }
	 else if (operation == "INSERT") {
		 
		 Map<String,String> map = new HashMap<String,String>();
		 if (query[3].contains("VALUE")) {
			//TO_DO
			 return null;
		 }
		 else {
			 int i = 3 ;
			 ArrayList<String> list = new ArrayList<String>();
			 while (!query[i].contains("VALUE")) {
				 list.add(query[i]);
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
				 map.put(list.get(j), query[i]);
				 i++;
				 j++;
			 }
			 return map;
		 }
	 }
	 else return null ;
 }
 private String getCond(String str) {
	 String st = new String ();
	 
	 if (str.contains("WHERE")) {
		st = str.substring(str.indexOf(" WHERE "), str.length());
		return st;
	 }
	 else {
		 return "";
	 }
	 
 }
 public String getCondition() {
	 return condition;
 }
}
