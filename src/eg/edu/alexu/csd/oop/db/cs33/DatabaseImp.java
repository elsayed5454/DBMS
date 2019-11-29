package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Database;

public class DatabaseImp implements Database {

	private ArrayList<MyTable> database;
	private String currentTable;
	private String currentDB;
	private XML xml = new XML();

	@Override
	public String createDatabase(String databasePath, boolean dropIfExists) {

		// Create folder with database path
		File dir = new File("tests" + System.getProperty("file.separator") + databasePath);
		
		// Get parent directory and check if it database exists before (case insensitive)
		File parent = dir.getParentFile();
		File[] databasesFolder = parent.listFiles();
		
		// Check if databases folder is not empty and get similar folder
		File similar = null;
		if (databasesFolder != null) {
			for (File s : databasesFolder) {
				if (dir.getName().equalsIgnoreCase(s.getName())) {
					similar = s;
					break;
				}
			}
		}
		
		currentDB = databasePath;
		if (dropIfExists) {
			try {

				// Drop database first then create it again
				executeStructureQuery("DROP DATABASE " + databasePath);
				if (similar != null) {
					removeFolder(similar);
				}
				executeStructureQuery("CREATE DATABASE " + databasePath);
				dir.mkdirs();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				executeStructureQuery("CREATE DATABASE " + databasePath);
				if (similar != null) {
					File tmp = new File(dir.getParentFile() + System.getProperty("file.separator") + "tmp");
					cutDirectory(similar, tmp);
					cutDirectory(tmp, dir);
					dir.mkdirs();
				}
				else {
					dir.mkdirs();
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return dir.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {

		int operation = getOperationStructure(query);
		if (operation == -1) {
			return false;
		}

		switch (operation) {

		// *CREATE DATABASE CASE
		case 0:
			database = new ArrayList<MyTable>();
			break;

		// *DROP DATABASE CASE
		case 1:
			database = null;
			break;

		// *CREATE TABLE CASE
		case 2:
			CreateTableParser parser = new CreateTableParser(query);
			String path = "tests" + System.getProperty("file.separator") + currentDB
					+ System.getProperty("file.separator") + parser.getName() + ".xml";

			// Return false if table columns is empty (for tests)
			if (parser.isMapEmpty()) {
				throw new SQLException("Not allowed");
			}
			
			// Return false if table already exists
			for (MyTable t : database) {
				if (t.getName().equalsIgnoreCase(parser.getName())) {
					return false;
				}
			}
			
			MyTable table = new MyTable(parser.getColumnsMap());
			table.setName(parser.getName());
			table.setColumnsCasePreserved(parser.getColumnsCasePreserved());
			currentTable = parser.getName();
			if (database != null) {
				database.add(table);
			} else {
				throw new SQLException("Database not found");
			}
			xml.create(path);
			return true;

		// *DROP TABLE CASE
		case 3:
			CreateTableParser parserDrop = new CreateTableParser(query);
			String wantedTable = parserDrop.getName();

			for (int i = 0; i < this.database.size(); i++) {
				MyTable t = this.database.get(i);
				if (t.getName().equals(wantedTable)) {
					this.database.remove(i);
					String pathh = "tests" + System.getProperty("file.separator") + currentDB
							+ System.getProperty("file.separator") + t.getName() + ".xml";
					File file = new File(pathh);
					xml.drop(file);
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

		int tableIndex = -1;
		for (MyTable table : database) {
			Pattern pattern = Pattern.compile(currentTable, Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(table.getName());
			if (m.find())
				tableIndex = database.indexOf(table);
		}

		if (tableIndex == -1)
			return null;

		MyTable table = database.get(tableIndex);
		ArrayList<Map<String, String>> result = table.select(Columns, Condition);
		if (result == null)
			return null;

		int rows = 0, cols = 0;
		rows = result.size();
		if (rows > 0)
			cols = result.get(0).size();

		Object[][] finalResult = new Object[rows][cols];
		for (int i = 0; i < rows; i++) {
			Map<String, String> element = result.get(i);
			int j = 0;
			for (String s : element.keySet()) {
				finalResult[i][j] = element.get(s);
				j++;
			}
		}

		return finalResult;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {

		// The updated rows count after any operation
		int updatedRows = 0;
		int operation = getOperationUpdate(query);
		if (operation == -1)
			return 0;
		if (database.isEmpty()) {
			throw new SQLException("Table not found");
		}
		switch (operation) {

		// *INSERT case
		case 0:
			InsertParser parser = new InsertParser(query, "INSERT");
			String name = parser.getName();
			boolean found = false;
			for (int i = 0; i < database.size(); i++) {

				String n = database.get(i).getName();
				if (n.equals(name)) {

					parser.setColumns(database.get(i).getValidColumns());
					database.get(i).addRow(parser.getMap());
					updatedRows++;
					found = true;
					String path = "tests" + System.getProperty("file.separator") + currentDB
							+ System.getProperty("file.separator") + n + ".xml";
					File file = new File(path);
					xml.save(file, database.get(i).getTable());
					break;
				}
			}
			if (!found) {
				throw new SQLException("Table not found");
			}
			break;

		// *UPDATE case
		case 1:
			InsertParser parse = new InsertParser(query, "UPDATE");
			String nameU = parse.getName();
			boolean foundU = false;
			for (int i = 0; i < database.size(); i++) {
				String n = database.get(i).getName();
				if (n.equals(nameU)) {
					String[] arr = database.get(i).parseCondition(parse.getCondition());
					updatedRows = database.get(i).Update(arr[1], arr[2], Integer.parseInt(arr[0]), parse.getMap());
					foundU = true;
					String path = "tests" + System.getProperty("file.separator") + currentDB
							+ System.getProperty("file.separator") + n + ".xml";
					File file = new File(path);
					xml.save(file, database.get(i).getTable());
					break;
				}
			}
			if (!foundU) {
				throw new SQLException("Table not found");
			}
			break;

		// *DELETE case
		case 2:
			InsertParser pars = new InsertParser(query, "DELETE");
			String nameD = pars.getName();
			boolean foundD = false;
			for (int i = 0; i < database.size(); i++) {
				String n = database.get(i).getName();
				if (n.equals(nameD)) {
					String[] arr = database.get(i).parseCondition(pars.getCondition());
					updatedRows = database.get(i).remove(arr[1], arr[2], Integer.parseInt(arr[0]));
					foundD = true;
					String path = "tests" + System.getProperty("file.separator") + currentDB
							+ System.getProperty("file.separator") + n + ".xml";
					File file = new File(path);
					xml.save(file, database.get(i).getTable());
					break;
				}
			}
			if (!foundD) {
				throw new SQLException("Table not found");
			}
			break;
		}

		return updatedRows;
	}

	// Helper function to select which operation to be performed
	// on database structure
	private int getOperationStructure(String query) {
		Pattern pattern = Pattern.compile("create database", Pattern.CASE_INSENSITIVE);
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

	// Helper function to select which operation to be performed
	// on database as an update to it
	private int getOperationUpdate(String query) {
		Pattern pattern = Pattern.compile("insert into", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(query);
		if (m.find())
			return 0;

		pattern = Pattern.compile("update", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 1;
		pattern = Pattern.compile("delete", Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(query);
		if (m.find())
			return 2;

		return -1;
	}
	
	// Recursive method to remove all files in a folder
	private void removeFolder(File folder) {
		
		// Check if folder is empty
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				removeFolder(f);
			}	
		}
		folder.delete();
	}
	
	// Method to cut directory into another directory
	private void cutDirectory(File sourceDir, File targetDir) throws IOException {
        if (sourceDir.isDirectory()) {
            copyDirectoryRecursively(sourceDir, targetDir);
        } else {
            Files.copy(sourceDir.toPath(), targetDir.toPath());
        }
        removeFolder(sourceDir);
    }
	
	// Recursive method to copy directory and sub-directory
	private void copyDirectoryRecursively(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String child : source.list()) {
            cutDirectory(new File(source, child), new File(target, child));
        }
    }

	// Getters methods
	public ArrayList<MyTable> getTables() {
		return (ArrayList<MyTable>) this.database;
	}
}
