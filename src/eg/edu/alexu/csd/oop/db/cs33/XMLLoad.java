package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class XMLLoad {

	private String path;
	private ArrayList<Map<String,String>> table;
	
	public XMLLoad(String path) {
		this.path = path;
	}
	
	public ArrayList<Map<String,String>> Load(){
		File file = new File(path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			//root of the tree
			Element root = doc.getDocumentElement();
			NodeList rows = root.getElementsByTagName("row");
			for (int i = 0 ; i < rows.getLength() ; i++) {
				NodeList columns = rows.item(i).getChildNodes();
				Map<String,String> map = new HashMap<String,String>();
				for (int j = 0 ; j < columns.getLength() ; j++) {
					map.put(columns.item(i).getNodeName(), columns.item(i).getTextContent());
				}
			}
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return table;
	}
}


