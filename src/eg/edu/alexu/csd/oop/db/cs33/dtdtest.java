package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;

public class dtdtest {

	public static void main(String[] args) {
		String path = "E:\\Career\\CSED\\Year2\\Term1\\Programming\\Projects\\16_28_33_74_dbms\\test.xml";
		ArrayList<String> cols = new ArrayList<String>();
		cols.add("a");
		cols.add("as");
		cols.add("asd");
		cols.add("asdf");
//		DTDCreate s = new DTDCreate(path , cols);
		XMLCreate s = new XMLCreate(path);
		s.Create();
	}

}
