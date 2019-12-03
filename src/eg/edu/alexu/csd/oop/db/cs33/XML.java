package eg.edu.alexu.csd.oop.db.cs33;

import java.util.ArrayList;
import java.util.Map;

public class XML {

	//xml design to choose which operation to do
	public void create(String path,  ArrayList<String> cols) {
		XMLCreate xml = new XMLCreate(path + ".xml");
		xml.Create();
		DTDCreate dtd = new DTDCreate(path + ".dtd", cols);
		dtd.Create();
	}
	
	public void drop(String path) {
		XMLDrop xml = new XMLDrop(path + ".xml");
		xml.Drop();
		DTDDrop dtd = new DTDDrop(path + ".dtd");
		dtd.Drop();
	}

	public void save(String path, ArrayList<Map<String,String>> table) {
		XMLSave temp = new XMLSave(path,table);
		temp.Save();
	}
	
	public MyTable load(String path) {
		XMLLoad temp = new XMLLoad(path);
		return temp.Load();
	}
}
