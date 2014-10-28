/**
 * Name : Akshay Mattoo (100099551)
 */

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XpathParserXML { 

    static void print ( Node e ) {
	if (e instanceof Text)
	    System.out.print(((Text) e).getData());
	else {
	    NodeList c = e.getChildNodes();
	    System.out.print("<"+e.getNodeName());
	    NamedNodeMap attributes = e.getAttributes();
	    for (int i = 0; i < attributes.getLength(); i++)
		System.out.print(" "+attributes.item(i).getNodeName()
				 +"=\""+attributes.item(i).getNodeValue()+"\"");
	    System.out.print(">");
	    for (int k = 0; k < c.getLength(); k++)
		print(c.item(k));
	    System.out.print("</"+e.getNodeName()+">");
	    System.out.print("\n");
	}
    }

    static void eval ( String query, String document ) throws Exception {
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	InputSource inputSource = new InputSource(document);
	NodeList result = (NodeList) xpath.evaluate(query,inputSource,XPathConstants.NODESET);
	System.out.println("XPath query: "+query);
	for (int i = 0; i < result.getLength(); i++)
	    print(result.item(i));
	System.out.println();
	System.out.println("*********************************************************************");
    }

    public static void main ( String[] args ) throws Exception {
	eval("//issue[volume=13 and number=4 ]/articles/article[authors/author='David Maier']/title","SigmodRecord.xml");
	
	eval("//SigmodRecord/issue/articles/article[contains(child::title,'Database') or contains(child::title,'database')]/authors/author","SigmodRecord.xml");
	
	eval("//issue/articles/article[contains(./title,'Research in Knowledge Base Management Systems.')]/initPage | //issue/articles/article[contains(./title,'Research in Knowledge Base Management Systems.')]/endPage | //SigmodRecord/issue[articles/article[contains(./title,'Research in Knowledge Base Management Systems.')]]/volume | //SigmodRecord/issue[articles/article[contains(./title,'Research in Knowledge Base Management Systems.')]]/number","SigmodRecord.xml");
	//eval("//SigmodRecord/issue/articles/article/tile['Research in Knowledge Base Management Systems.']","SigmodRecord.xml");
    
    }
}
