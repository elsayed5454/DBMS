package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;

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

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class XMLCreate {
	
	private String path; //XML file path in order to be created into
	
	//Initialization constructor
	public XMLCreate(String path) {
		this.path = path;
	}
	
	//Creates the XML file and writes header with column names
	public void Create() {
		File file = new File(path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			//root of the tree
			Element root = doc.createElement("table");
			doc.appendChild(root);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			//indentation
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//encoding
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			//linking with the dtd schema file
			DOMImplementation domImp  = doc.getImplementation();
			DocumentType docType = domImp.createDocumentType("doctype", "SYSTEM", file.getName().substring(0, file.getName().length() - 4) + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
			//transforming from tree to file
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
	}
}
