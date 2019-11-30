package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class XML {

	public void create(String path,  ArrayList<String> cols) {
		XMLCreate xml = new XMLCreate(path + ".xml");
		xml.Create();
		DTDCreate dtd = new DTDCreate(path + ".dtd", cols);
		dtd.Create();
	}
	
	public void drop(String path) {
		XMLDrop xml = new XMLDrop(new File(path + ".xml"));
		xml.Drop();
		DTDDrop dtd = new DTDDrop(new File(path + ".dtd"));
		dtd.Drop();
	}

	public void save(String path, ArrayList<Map<String,String>> table) {
		File file = new File(path);
		XMLSave temp = new XMLSave(file,table);
		temp.Save();
	}
	
	public ArrayList<Map<String,String>> load(String path) {
		XMLLoad temp = new XMLLoad(path);
		return temp.Load();
	}
}
