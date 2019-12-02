package eg.edu.alexu.csd.oop.db.cs33;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class DTDCreate {

	private String path;
	private ArrayList<String> cols;
	
	public DTDCreate(String path, ArrayList<String> cols) {
		this.path = path;	
		this.cols = cols;
	}

	public void Create() {
		//standard
		String content = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" ;
		content += "\n";
		//root name and its children
		content += "<!ELEMENT table (row*)>";
		content += "\n";
		//children of each row aka columns
		content += "<!ELEMENT row (";
		for (int i = 0 ; i < cols.size() ; i++) {
			content += cols.get(i) ;
			if (i != cols.size() -1) {
				content+= ", ";
			}
		}
		content += ")>";
		content += "\n";
		//each column can contain text value
		for (int i = 0 ; i < cols.size() ; i++) {
			content += "<!ELEMENT " + cols.get(i) +" (#PCDATA)>";
			if (i != cols.size() -1) {
				content+= "\n";
			}
		}
		//write to the file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
