package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLCreate {
	
	private String path; //XML file path in order to be created into
	private String[] columnNames; 
	
	//Initialization constructor
	public XMLCreate(String path, String[] columnNames) {
		this.path = path;
		this.columnNames = columnNames;
	}
	
	//Creates the XML file and writes header with column names
	public File Create() {
		File file = new File(path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			//root of the tree
			Element root = doc.createElement("table");
			doc.appendChild(root);
			//header indicating the columns of the table
			Element header = doc.createElement("header");
			root.appendChild(header);
			for (int i = 0 ; i < columnNames.length ; i++) {
				Element column = doc.createElement(columnNames[i]);
				column.appendChild(doc.createTextNode(columnNames[i]));
				header.appendChild(column);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult sr = new StreamResult(file);
			transformer.transform(domSource, sr);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return file;
	
	}
}
