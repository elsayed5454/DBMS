package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLCreate {
	
	private String path; //XML file path in order to be created into
	private String[] columnNames; 
	
	//Initialization constructor
	public XMLCreate(String path, String[] columnNames) {
		this.path = path;
		this.columnNames = columnNames;
	}
	
	//Creates the XML file and writes header with column names
	public void Create() {
		
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement("Header");
			// Node for every column with its name insider it for the first row in the table
			for (int i = 0 ; i < columnNames.length ; i++) {
				xmlStreamWriter.writeStartElement(columnNames[i]);
				xmlStreamWriter.writeCharacters(columnNames[i]);
				xmlStreamWriter.writeEndElement();
			}
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.flush();
			xmlStreamWriter.close();
			fileWriter.close();
		} 
		catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
