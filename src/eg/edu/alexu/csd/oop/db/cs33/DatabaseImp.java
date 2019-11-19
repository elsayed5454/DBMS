package eg.edu.alexu.csd.oop.db.cs33;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DatabaseImp implements Database {

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		
		return null;
		
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

}
