package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eg.edu.alexu.csd.oop.db.Database;

public class DatabaseImp implements Database {
	
	// Create a file from path tests in the project directory
	// then list all folders in it in databases list
	private File tests = new File("tests");
	private List<File> databases = new ArrayList<>(Arrays.asList(tests.listFiles()));
	
	// Default constructor
	public DatabaseImp() {}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		
		// Create directory file with path of databaseName
		File dir = new File("tests" + System.getProperty("file.separator") + databaseName);
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

		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {

		return 0;
	}

	public List<String> getDatabasesNames() {
		List<String> names = new ArrayList<String>();
		for(File f : databases) {
			names.add(f.getName());
		}
		return names;
	}
}
