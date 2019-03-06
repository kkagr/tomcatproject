package com.kkagr.httpserver.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerParser {
    public  static int getPort(){
        int port = 8080;
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("server.xml");
            Element element = (Element) document.selectSingleNode("//connector");
            port = Integer.parseInt(element.attributeValue("port")) ;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return port;
    }
}
