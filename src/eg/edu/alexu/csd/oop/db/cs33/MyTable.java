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
  private ArrayList<String> ColumnsOrder;
  private int Size;
  private String name;
  private int changedCounter = 0;

  
  
  public MyTable (Map<String,String> Columns)
  {
	  Size = 0;
	  this.ValidColumns = Columns;
  }
  
  public void setName(String name)
  {
	  this.name = name;
  }
  
  public void setOrder(ArrayList<String> order)
  {
	  this.ColumnsOrder = order;
  }
  
  public String getName()
  {
	  return this.name;
  }
  
  public int addRow(Map<String,String> row) {
	boolean b = checkValid(row);
	if (b) {
		table.add(row);
		Size ++;
		changedCounter++;
	}
	int c = changedCounter;
	changedCounter = 0;
	return c ;
  }

  public int remove(String key, String value , int op ) {
	  if (op == 0) {
		  int condVal = Integer.parseInt(value); // the condition's value
		  for (int i=0 ; i<Size ; i++ ) {
			  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
			  if (val > condVal) {
				  table.remove(i);
				  Size -- ; 
				  i -- ;
				  changedCounter++;
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
				  changedCounter++;
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
				  changedCounter++;
			  }
		  }
	  }
	  else {
		  table.clear();
		  changedCounter = Size;
		  Size = 0 ;
	  }
	  int c = changedCounter;
	  changedCounter = 0;
	  return c;
  }

  public int Update(String key, String value, int op,Map<String,String> map) {
	  boolean b = checkValid(map);
	  if (b) {
	  if (op == -1) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return 0;
				  }
				 
				  else {
					  for (int i=0 ; i < Size; i++) {
					  ValidColumns.replace(str, map.get(str));
					  }
			  }

		  }
			  return Size;

	  }
	  else if (op == 0) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return 0;
				  }
				  else {
				  for (int i = 0; i<Size ; i++) {	  
				  int condVal = Integer.parseInt(value); // the condition's value
				  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
				  
				   if (val > condVal) {
					  ValidColumns.replace(str, map.get(str));
					  changedCounter++;
				  }
				  }
			  }
			  }
			 int c = changedCounter;
			 changedCounter = 0;
			 return c;
	  }
	  else if (op == 1) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return 0;
				  }
				  else {
					for (int i=0 ; i<Size ; i++) {  
				  int condVal = Integer.parseInt(value); // the condition's value
				  int val =Integer.parseInt(table.get(i).get(key));//The value form the table to be compared with the condition
				   if (val < condVal) {
					  ValidColumns.replace(str, map.get(str));
					  changedCounter++;
				  }
				}
			  }
		}
			  int c = changedCounter;
			  changedCounter = 0;
			  return c;
	  }
	  
	  else if (op == 2) {
			  Set <String> set = map.keySet();
			  for (String str : set) {
				  if (!ValidColumns.containsKey(str)) {
					  System.out.println("Invalid Column");
					  return 0;
				  }
				  else {
					for (int i=0 ; i<Size ; i++) {  
				  String condVal = value; // the condition's value
				  String val =table.get(i).get(key);//The value form the table to be compared with the condition
				   if (val == condVal) {
					  ValidColumns.replace(str, map.get(str));
					  changedCounter++;
				  }
				}
			  }
			  }
			  int c = changedCounter;
			  changedCounter =0;
			  return c ;
	  }
	  else return 0;
  }
	  else return 0;
  }
  /* Selects specific part of the table*/
  public ArrayList<Map<String,String>> select(String[] Columns, String Condition)
  {
	  
	  ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
	  
	  /*First checks if there isn't a condition*/
	  if(Condition == null)
	  {
		  if(Columns.length==1 && Columns[0].equals("*"))
			  return this.table;
		  
		  for(int i=0 ; i<this.table.size() ; i++)
		  {
			  Map<String,String> element = new HashMap<String,String>();
			  Map<String,String> current = this.table.get(i);
			  
			  for(int j=0 ; j<Columns.length;j++)
			  {
				  if(current.containsKey(Columns[j]))
				  {
					  element.put(Columns[j], current.get(Columns[j]));
				  }
			  }
			  
			  result.add(element);
		  }
		  return result;
	  }
	  
	  if(!validCondition(Condition))
		  return null;
	  
	  String[] strings = parseCondition(Condition);
	  
	  String wantedColumn= strings[1] , wantedValue=strings[2];
	  if(!ValidColumns.containsKey(wantedColumn))
		  return null;
	  
	  for(int i=0 ; i<this.table.size() ; i++)
	  {
		  Map<String,String> current = this.table.get(i);
		  
		  boolean flag = false;
		  switch((int)Double.parseDouble(strings[0]))
		  {
		  /*GREATER THAN case*/
		  case 0:
			  
			  if( Double.parseDouble(current.get(wantedColumn)) > Double.parseDouble(wantedValue))
				  flag = true;
			  break;
			  
		  /*LESS THAN case*/
		  case 1:
			  if( Double.parseDouble(current.get(wantedColumn)) < Double.parseDouble(wantedValue))
				  flag = true;
			  break;
			  
		  /*EQUAL case*/
		  case 2:
			  wantedValue = wantedValue.replace("'", "");
			  if(current.get(wantedColumn).equals(wantedValue))
				  flag = true;
			  break;
		  }
		  
		  if(flag)
		  {
			  if(Columns.length==1 &&Columns[0].equals("*"))
			  {
				  result.add(current);
			  }
			  else
			  {
				  Map<String,String> element = new HashMap<String,String>();
				  for(int j=0;j<Columns.length;j++)
				  {
					  if(current.containsKey(Columns[j]))
					  {
						  element.put(Columns[j], current.get(Columns[j]));	  
					  }
				   }
				  
				  result.add(element);
			  }
		  }
		  
	  }
	  
	  return result;
  }
  
  public void showValidColumns()
  {
	  System.out.println(this.ValidColumns);
  }
  
  public void showName()
  {
	  System.out.println(this.name);
  }
  
  public void showTableContent()
  {
	  for(int i=0;i<this.table.size();i++)
	  {
		  System.out.println("Element no "+(i+1)+","+this.table.get(i));
	  }
  }
  
  private boolean checkValid(Map<String,String> map)
	{
		if(map.size() > ValidColumns.size())
			return false;
		

		
		for(String s: map.keySet())
		{
			if(!ValidColumns.containsKey(s))
			{
				return false;
			}
			
			String type = ValidColumns.get(s);
			if(type=="VARCHAR" && (!map.get(s).contains("'")))
			{
				return false;
			}
			if(type=="INT" && (map.get(s).contains("'")))
			{
				return false;
			}
			
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
  
  private boolean validCondition(String condition)
  {
	  
	  String[] strings = parseCondition(condition);
	  
	  String wantedColumn = strings[1];
	  String wantedValue = strings[2];
	  
	  
	  if(this.ValidColumns.containsKey(wantedColumn.toUpperCase()))
	  {
		  if(wantedValue.contains("'"))
		  {
			  Pattern pattern = Pattern.compile("varchar",Pattern.CASE_INSENSITIVE);
			  Matcher m = pattern.matcher(this.ValidColumns.get(wantedColumn.toUpperCase()));
			  if(!m.find())
				  return false;
		  }
		  else
		  {
			  Pattern pattern = Pattern.compile("int",Pattern.CASE_INSENSITIVE);
			  Matcher m = pattern.matcher(this.ValidColumns.get(wantedColumn.toUpperCase()));
			  if(!m.find())
				  return false;
		  }
	  }
	  else
	  {
		  return false;
	  }
	  
	  return true;
			  
  }
}

