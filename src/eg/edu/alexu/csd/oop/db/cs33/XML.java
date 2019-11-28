package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class XML {

	public File create(String path) {
		XMLCreate temp = new XMLCreate(path);
		return temp.Create();
	}
	
	public void drop(File file) {
		XMLDrop temp = new XMLDrop(file);
		temp.Drop();
	}

	public void save(File file, ArrayList<Map<String,String>> table) {
		XMLSave temp = new XMLSave(file,table);
		temp.Save();
	}
	
	public ArrayList<Map<String,String>> load(String path) {
		XMLLoad temp = new XMLLoad(path);
		return temp.Load();
	}
}
