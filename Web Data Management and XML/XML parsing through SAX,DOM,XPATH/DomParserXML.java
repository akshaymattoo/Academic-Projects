/**
 * Name: Akshay Mattoo (1000995551)
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class DomParserXML {

	public static void main(String argv[]) {
	 
	    try 
		{
			File fXmlFile = new File("SigmodRecord.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
				getTitle(doc);
			System.out.println();
			System.out.println("************************************************************************");
			System.out.println();
				getAuthor(doc);
			System.out.println();
			System.out.println("*************************************************************************");
			System.out.println();
				getVolumeInitPage(doc);
			
		}
	    catch (Exception e) 
		{
			e.printStackTrace();
	    }
	  }
	
	
	public static void getVolumeInitPage(Document doc){
		try{
		doc.getDocumentElement().normalize();
		 
		NodeList nList = doc.getElementsByTagName("issue");
	 
		for (int temp = 0; temp < nList.getLength(); temp++) 
		{
	 
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element eElement = (Element) nNode;
	 
					NodeList articleList= eElement.getElementsByTagName("article");
					
					for (int i = 0; i < articleList.getLength(); i++) 
					{
						Node articleNode = articleList.item(i);
						if (articleNode.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element articleElement = (Element) articleNode;
							String title =articleElement.getElementsByTagName("title").item(0).getTextContent();
							if(title.equals("Research in Knowledge Base Management Systems."))
							{
								System.out.println("<initPage>"+articleElement.getElementsByTagName("initPage").item(0).getTextContent()+"</initPage>");
								System.out.println("<endPage>"+articleElement.getElementsByTagName("endPage").item(0).getTextContent()+"</endPage>");
								
								System.out.println("<volume>"+eElement.getElementsByTagName("volume").item(0).getTextContent()+"</volume>");
								System.out.println("<number>"+eElement.getElementsByTagName("number").item(0).getTextContent()+"</number>");
							}
							
						 
						}
					}
				
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
	
	
	public static void getAuthor(Document doc){
		try{
		doc.getDocumentElement().normalize();
		 
		NodeList nList = doc.getElementsByTagName("article");
		int len= nList.getLength();
		for (int temp = 0; temp < nList.getLength(); temp++) 
		{
	 
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element eElement = (Element) nNode;
	 
				String titleBook =eElement.getElementsByTagName("title").item(0).getTextContent();
				if(titleBook.contains("database") || titleBook.contains("Database"))
				{
					
					NodeList authorList = eElement.getElementsByTagName("author");
					
					for (int j = 0; j < authorList.getLength(); j++) 
					{
						Node authorNode = authorList.item(j);
						String authorName =authorNode.getTextContent();
						System.out.println("<author>"+authorName+"</author>");
					}
				}
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
	
	public static void getTitle(Document doc){
				try{
				doc.getDocumentElement().normalize();
				 
				NodeList nList = doc.getElementsByTagName("issue");
			 
				for (int temp = 0; temp < nList.getLength(); temp++) 
				{
			 
					Node nNode = nList.item(temp);
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{
						Element eElement = (Element) nNode;
			 
						if(eElement.getElementsByTagName("volume").item(0).getTextContent().equals("13") && eElement.getElementsByTagName("number").item(0).getTextContent().equals("4"))
						{
							NodeList articleList= eElement.getElementsByTagName("article");
							
							for (int i = 0; i < articleList.getLength(); i++) 
							{
								Node articleNode = articleList.item(i);
								if (articleNode.getNodeType() == Node.ELEMENT_NODE) 
								{
									Element articleElement = (Element) articleNode;
									String title =articleElement.getElementsByTagName("title").item(0).getTextContent();
									NodeList authorList = articleElement.getElementsByTagName("author");
									
									for (int j = 0; j < authorList.getLength(); j++) 
									{
										Node authorNode = authorList.item(j);
										String authorName =authorNode.getTextContent();
										if(authorName.trim().equals("David Maier"))
										{
											 System.out.println("<title>"+title+"</title>");
										}
									}
								}
							}
						}
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
}
