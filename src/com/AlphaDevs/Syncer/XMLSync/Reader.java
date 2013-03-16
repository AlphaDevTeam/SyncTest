
package com.AlphaDevs.Syncer.XMLSync;

import java.io.File;
import java.io.StringReader;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Mihindu Gajaba Karunarathne
 */
public class Reader {

    public void ReadXML(String xml)
    {
        try {
 
		String url = "http://www.alphadevs.com/php/getCustomer.php?format=xml";
                url = xml;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		//Document doc = dBuilder.parse(new URL(url).openStream());
                Document doc = loadXMLFromString(xml);
		doc.getDocumentElement().normalize();
 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("Result");
		System.out.println("-----------------------");
 
		for (int temp = 0; temp < nList.getLength(); temp++) {
 
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
		      Element eElement = (Element) nNode;
 
		      System.out.println("Status : " + getTagValue("Status", eElement));
		      System.out.println("Reason : " + getTagValue("Reason", eElement));
	              
		   }
		}
	  } catch (Exception e) {
		e.printStackTrace();
	  }
    }
    
    public static boolean isSuccessful(String xml)
    {
        try 
        {
            boolean tmpSucess=false;
            String url = "";
            url = xml;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		//Document doc = dBuilder.parse(new URL(url).openStream());
                Document doc = loadXMLFromString(xml);
		doc.getDocumentElement().normalize();
 
		NodeList nList = doc.getElementsByTagName("Result");
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
 
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
		      Element eElement = (Element) nNode;
 
		      if(getTagValue("Status", eElement).equalsIgnoreCase("BAD"))
                      {
                          tmpSucess=false;                      
                      }
                      else if(getTagValue("Status", eElement).equalsIgnoreCase("GOOD"))
                      {
                          tmpSucess=true;
                      }
                             
		   }
		}
                
               return  tmpSucess;
	  } catch (Exception e) {
		e.printStackTrace();
                return false;
	  }
    }
        
    
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    
    private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
  }
    
}
