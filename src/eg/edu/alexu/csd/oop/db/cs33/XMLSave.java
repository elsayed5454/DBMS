package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLSave {

	private File file;
	private ArrayList<Map<String,String>> table;
	
	public XMLSave (File file, ArrayList<Map<String,String>> table) {
		this.file = file;
		this.table = table;
	}
	
	public void Save() {
		String path = file.getAbsolutePath();
		file.delete();
		File file = new File(path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			//root of the tree
			Element root = doc.createElement("table");
			doc.appendChild(root);
			for (Map<String,String> m : table) {
				Element row = doc.createElement("row");
				root.appendChild(row);
				for (String key : m.keySet()) {
					Element column = doc.createElement(key);
					column.appendChild(doc.createTextNode(m.get(key)));
					row.appendChild(column);
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			DOMSource domSource = new DOMSource(doc);
			StreamResult sr = new StreamResult(file);
			transformer.transform(domSource, sr);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
