package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLInsert {

	private File file;
	private String[] columnNames;
	private String[] values;
	
	public XMLInsert(File file, String[] columnNames, String[] values) {
		this.file = file;
		this.columnNames = columnNames ;
		this.values = values;
	}
		
	public void Insert() {
			
		try {
			FileWriter fileWriter = new FileWriter(file);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}
