package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLSave {

	private String path;
	private ArrayList<Map<String,String>> table;
	private String tempPath;
	
	public XMLSave (String path, ArrayList<Map<String,String>> table) {
		this.path = path;
		this.table = table;
		this.tempPath = path.substring(0 , path.length() - 4 ) + "temp.xml" ; 
	}
	
	public void Save() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			//validate the schema
			dbFactory.setValidating(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			doc.setXmlStandalone(true);
			//root of the tree
			Node root = doc.createElement("table");
			doc.appendChild(root);
			for (Map<String,String> m : table) {
				Node row = doc.createElement("row");
				root.appendChild(row);
				for (String key : m.keySet()) {
					Node column = doc.createElement(key);
					column.appendChild(doc.createTextNode(m.get(key)));
					row.appendChild(column);
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			DOMImplementation domImp  = doc.getImplementation();
			DocumentType docType = domImp.createDocumentType("doctype", "SYSTEM", new File(path).getName().substring(0, new File(path).getName().length() - 4) + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
			DOMSource domSource = new DOMSource(doc);
			FileOutputStream fos = new FileOutputStream(new File(tempPath));
			transformer.transform(domSource, new StreamResult(fos));
			validate();
			fos.close();
			new File(tempPath).delete();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean validate() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			//validate the schema
			dbFactory.setValidating(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			dBuilder.setErrorHandler(new ErrorHandler() {

				@Override
				public void error(SAXParseException arg0) throws SAXException {
					throw new SAXException();
				}

				@Override
				public void fatalError(SAXParseException arg0) throws SAXException {
					throw new SAXException();
				}

				@Override
				public void warning(SAXParseException arg0) throws SAXException {
					throw new SAXException();
				}
				
			});
			Document doc = dBuilder.parse(new File(tempPath));
			doc.setXmlStandalone(true);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			DOMImplementation domImp  = doc.getImplementation();
			DocumentType docType = domImp.createDocumentType("doctype", "SYSTEM", new File(path).getName().substring(0, new File(path).getName().length() - 4) + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
			DOMSource domSource = new DOMSource(doc);
			FileOutputStream fos = new FileOutputStream(new File(path));
			transformer.transform(domSource, new StreamResult(fos));
			fos.close();
			return true;
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Doesn't match the Schema file");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
