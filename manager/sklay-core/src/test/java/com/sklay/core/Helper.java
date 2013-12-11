package com.sklay.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Helper {
	public String readXMLString4TagName(String xmlStr,String tagName) throws Exception {  

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  

        DocumentBuilder builder = dbf.newDocumentBuilder();  

        InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());  

        Document doc = builder.parse(inputStream); //  

        // 下面开始读取  

        Element root = doc.getDocumentElement(); // 获取根元素  

        NodeList nodes = root.getElementsByTagName(tagName); 
        Element e = (Element) nodes.item(0);  

        Node t = e.getFirstChild();  

        return t.getNodeValue(); 
    }
}
