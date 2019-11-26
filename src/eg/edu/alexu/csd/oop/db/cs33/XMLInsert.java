package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.IOException;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLInsert {

	private File file;
	private String[] columnNames = null;
	private String[] values;
	
	//for specific columns
	public XMLInsert(File file, String[] columnNames, String[] values) {
		this.file = file;
		this.columnNames = columnNames ;
		this.values = values;
	}
		
	//for every column
	public XMLInsert(File file, String[] values) {
		this.file = file;
		this.values = values;
	}
	
	public void Insert() {
			
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			Element root = doc.getDocumentElement();
			//getting every column name for second constructor
			if (columnNames == null) {
				//header is always the first child of the root from creation instance
				Node header = root.getFirstChild();
				//getting every column from header
				NodeList columnNodes = header.getChildNodes();
				String[] columns = new String[columnNodes.getLength()];
				//getting their string names
				for (int i = 0 ; i < columnNodes.getLength() ; i++) {
					columns[i] = columnNodes.item(i).getNodeName();
				}
				columnNames = columns;
			}
			//inserting the new row
			Element insert = doc.createElement("row");
			root.appendChild(insert);
			//adding every column with its value
			for (int i = 0 ; i < columnNames.length ; i++) {
				Element column = doc.createElement(columnNames[i]);
				column.appendChild(doc.createTextNode(values[i]));
				insert.appendChild(column);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult sr = new StreamResult(file);
			transformer.transform(domSource, sr);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}		
	}
}
