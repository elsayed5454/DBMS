package eg.edu.alexu.csd.oop.db.cs33;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTable {
  private ArrayList<Map<String,String>> table = new ArrayList<Map<String,String>>(); //This ArrayList is the table, it stores rows of maps , and the columns will be the keys of the map 
  private Map<String,String> ValidColumns ;//this set is to store all the Keys of the first map in the ArrayList so that no one add maps with different keys 
  private int Size;
  private String name;

  
  
  public MyTable (Map<String,String> Columns)
  {
	  Size = 0;
	  this.ValidColumns = Columns;
  }
  
  public void setName(String name)
  {
	  this.name = name;
  }
  
  public String getName()
  {
	  return this.name;
  }
  
  public void addRow(Map<String,String> row) {
	boolean b = checkValid(row);
	if (b) {
		table.add(row);
		Size ++;
	}
  }

  public void remove(String key, String value , int op ) {
	  if (op == 0) {
		  int condVal = Integer.parseInt(value); // the condition's value
		  for (int i=0 ; i<Size ; i++ ) {
			  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
			  if (val > condVal) {
				  table.remove(i);
				  Size -- ; 
				  i -- ;
			  }
		  }
	  }
	  else if (op == 1) {
		  int condVal = Integer.parseInt(value); // the condition's value
		  for (int i=0 ; i<Size ; i++ ) {
			  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
			  if (val < condVal) {
				  table.remove(i);
				  Size -- ;
				  i -- ;
			  }
		  }
	  }
	  else if (op == 2)  {
		  String condVal = value;// the condition's value
		  for (int i=0 ; i<Size ; i++ ) {
			  String val = table.get(i).get(key);//The value form the table to be compared with the condition
			  if (val == condVal) {
				  table.remove(i);
				  Size -- ;
				  i -- ;
			  }
		  }
	  }
	  else {
		  table.clear();
		  Size = 0 ;
	  }
  }

  public void Update(String key, String value, int op,Map<String,String> map) {
	  if (op == -1) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
				  }
				 
				  else {
					  for (int i=0 ; i < Size; i++) {
					  ValidColumns.replace(str, map.get(str));
					  }
			  }

		  }

	  }
	  else if (op == 0) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return ;
				  }
				  else {
				  for (int i = 0; i<Size ; i++) {	  
				  int condVal = Integer.parseInt(value); // the condition's value
				  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
				  
				   if (val > condVal) {
					  ValidColumns.replace(str, map.get(str));
				  }
				  }
			  }
			  }
	  }
	  else if (op == 1) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return ;
				  }
				  else {
					for (int i=0 ; i<Size ; i++) {  
				  int condVal = Integer.parseInt(value); // the condition's value
				  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
				   if (val < condVal) {
					  ValidColumns.replace(str, map.get(str));
				  }
				}
			  }
		}
	  }
	  else if (op == 2) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return ;
				  }
				  else {
					for (int i=0 ; i<Size ; i++) {  
				  String condVal = value; // the condition's value
				  String val =table.get(i).get(key);//The value form the table to be compared with the condition
				   if (val == condVal) {
					  ValidColumns.replace(str, map.get(str));
				  }
				}
			  }
			  }
	  }
  }
  
  public Object[][] select(String[] Columns, String Condition)
  {
	  
	  return null;
  }
  
  public void showValidColumns()
  {
	  System.out.println(this.ValidColumns);
  }
  
  public void showName()
  {
	  System.out.println(this.name);
  }
  
  private boolean checkValid(Map<String,String> map)
	{
		if(map.size() > ValidColumns.size())
			return false;
		
		Set<String> keySet =  map.keySet();
		Set<String> validColumnsSet = this.ValidColumns.keySet();
		
		boolean flag = false;
		
		for(String s1: keySet)
		{
			Pattern pattern =  Pattern.compile(s1, Pattern.CASE_INSENSITIVE);
			for(String s2: validColumnsSet)
			{
				Matcher m = pattern.matcher(s2);
				if(m.find())
				{
					String type1 = (String)map.get(s1);
					String type2 = (String)ValidColumns.get(s2);
					
					Pattern pattern2 = Pattern.compile(type1, Pattern.CASE_INSENSITIVE);
					Matcher m2 = pattern2.matcher(type2);
					if(m2.find())
					{
						flag = true;
						break;
					}
									
				}
			}
			
			if(flag ==false)
				return false;
			flag = false;
		}

		return true;
	}
  
  public String[] parseCondition(String condition)
  {
	  
	  String[] results = new String[3];
	  String[] split;
	  
	  if(condition.contains(">"))
	  {
		  results[0] = "0";
		  split = condition.split(">");
		  results[1] = split[0].trim();
		  results[2] = split[1].trim();
		  return results;
	  }
	  else if(condition.contains("<"))
	  {
		  results[0] = "1";
		  split = condition.split("<");
		  results[1] = split[0].trim();
		  results[2] = split[1].trim();
		  return results;
	  }
	  else if(condition.contains("="))
	  {
		  results[0] = "2";
		  split = condition.split("=");
		  results[1] = split[0].trim();
		  results[2] = split[1].trim();
		  return results;
	  }
	  
	  return null;
  }
}

