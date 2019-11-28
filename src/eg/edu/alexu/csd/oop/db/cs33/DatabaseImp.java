package eg.edu.alexu.csd.oop.db.cs33;


import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Database;

public class DatabaseImp implements Database {

	private List<File> databases = new ArrayList<File>();
	private ArrayList<MyTable> database = new ArrayList<MyTable>();
	private String currentTable;
	private String currentDB;
	private XML xml = new XML();
		
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		
		// Create directory file with path of databaseName
		File dir = new File("tests" + System.getProperty("file.separator") + databaseName);
		currentDB = databaseName;
		if(dropIfExists) {
			try {
				
				// Drop database first then create it again 
				executeStructureQuery("DROP DATABASE " + databaseName);
				dir.delete();
				executeStructureQuery("CREATE DATABASE " + databaseName);
				dir.mkdirs();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				databases.add(dir);
				executeStructureQuery("CREATE DATABASE " + databaseName);
				dir.mkdirs();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dir.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		
		int operation = getOperationIndex1(query);
		if(operation==-1)
			return false;
		
		switch (operation) {
		
		//*CREATE DATABASE CASE
		case 0:
			break;
			
		//*DROP DATABASE CASE
		case 1: 
			break;
			
		//*CREATE TABLE CASE
		case 2:
			CreateTableParser parser = new CreateTableParser();
			Map<String,String> columns = parser.createValidMap(query);
			ArrayList<String> columnsOrder = parser.createArrayList(query);
			String name = parser.nameGetter(query);
			String path = "tests" + System.getProperty("file.separator") + currentDB + System.getProperty("file.separator") + name + ".xml";
			xml.create(path);
			
			MyTable table = new MyTable(columns);
			table.setName(name);
			table.setOrder(columnsOrder);
			currentTable = name;
			database.add(table);
			return true;
			
		//*DROP TABLE CASE
		case 3:
			CreateTableParser parserDrop = new CreateTableParser();
			String wantedTable = parserDrop.nameGetterDrop(query);
			
			for(int i=0 ; i<this.database.size() ; i++)
			{
				MyTable t = this.database.get(i);
				if(t.getName().equals(wantedTable))
				{
					this.database.remove(i);
					return true;
				}
			}
						
			break;
		}
		
		
		
		return false;
	}


	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		SelectParser parser = new SelectParser();
		currentTable = parser.getName(query);
		String[] Columns = parser.getColumns(query);
		String Condition = parser.getCondition(query);
		
		int tableIndex=-1;
		for(MyTable table : database)
			{
				Pattern pattern = Pattern.compile(currentTable,Pattern.CASE_INSENSITIVE);
				Matcher m = pattern.matcher(table.getName());
				if(m.find())
					tableIndex = database.indexOf(table);
			}
		
		if(tableIndex ==-1)
			return null;
		
		MyTable table = database.get(tableIndex);
		ArrayList<Map<String,String>> result = table.select(Columns, Condition);
		if(result ==null)
			return null;
		
		int rows = 0,cols=0;
		rows = result.size();
		if(rows>0)
			cols = result.get(0).size();
		
		Object[][] finalResult = new Object[rows][cols];
		for(int i=0;i<rows;i++)
		{
			Map<String,String> element = result.get(i);
			int j=0;
			for(String s :element.keySet())
			{
				finalResult[i][j] = element.get(s);
				j++;
			}
		}
		
 		return finalResult;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		int operation = getOperationIndex2(query);
		if(operation ==-1)
			return 0;
		if (database.isEmpty()) {
		   System.out.println("No table exists , please create table");
		   return 0 ;
		}
		switch(operation) {
		
		//*INSERT case
		case 0:
			InsertParser parser = new InsertParser(query,"INSERT");
			String name = parser.getName();
			boolean found = false ;
			for (int i=0 ; i<database.size() ; i++) {
				String n = database.get(i).getName();
				if (n == name) {
					database.get(i).addRow(parser.getMap());
					found  = true ;
					break;
				}
			}
			if (!found) {
				System.out.println("table not found");
			}
			
			break;
			
		//*UPDATE case
		case 1:
			InsertParser parse = new InsertParser(query,"UPDATE");
			String nameU = parse.getName();
			boolean foundU = false;
			for (int i=0 ; i< database.size();i++) {
				String n = database.get(i).getName();
				if (n == nameU) {
					String[] arr= database.get(i).parseCondition(parse.getCondition());
					database.get(i).Update(arr[1],arr[2],Integer.parseInt(arr[0]), parse.getMap());
					foundU = true;
					break;
				}
			}
			if (!foundU) {
				System.out.println("table not found");
			}
			break;
			
		//*DELETE case
		case 2:
			InsertParser pars = new InsertParser(query,"DELETE");
			String nameD = pars.getName();
			boolean foundD = false ;
			for (int i=0 ; i<database.size();i++) {
				String n = database.get(i).getName();
				if (n == nameD) {
					String[] arr = database.get(i).parseCondition(pars.getCondition());
					database.get(i).remove(arr[1], arr[2], Integer.parseInt(arr[0]));
					foundD = true;
					break;
				}
			}
			if (!foundD) {
				System.out.println("table not found");
			}
			break;
		}
		
		return 0;
	}
		
	private int getOperationIndex1(String query) {
		Pattern pattern =  Pattern.compile("create database", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(query);
		if (m.find())
			return 0;
		
		pattern = Pattern.compile("drop database", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 1;
		
		pattern = Pattern.compile("create table", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 2;
		
		pattern = Pattern.compile("drop table", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 3;
		
		return -1;
		
	}

	private int getOperationIndex2(String query)
	{
		Pattern pattern =  Pattern.compile("insert into", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(query);
		if (m.find())
			return 0;
		
		pattern =  Pattern.compile("update", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 1;
		pattern =  Pattern.compile("delete", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 2;
		
		return -1;
	}
	public List<String> getDatabasesNames() {
		List<String> names = new ArrayList<String>();
		for(File f : databases) {
			names.add(f.getName());
		}
		return names;
	}
}
