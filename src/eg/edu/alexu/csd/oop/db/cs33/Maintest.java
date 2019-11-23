package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;

public class Maintest {

	public static void main(String[] args) {
		String[] columns = {"a","s","d"};
		XMLCreate create = new XMLCreate("E:\\Career\\CSED\\Year2\\Term1\\test.xml",columns);
		File file = create.Create();
		String[] values = {"as","ss","ds"};
		XMLInsert insert = new XMLInsert(file,columns,values);
		insert.Insert();
	}

}
