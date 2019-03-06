package com.kkagr.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebParser {
    public static Map<String,Map<String,String>> servletMaps = new HashMap<>();

    public  static void parser(String[] webAppNames) throws DocumentException {
        for(String webAppName:webAppNames){
            Map<String,String> servletMap = parser(webAppName);
            servletMaps.put(webAppName,servletMap);
        }
    }

    private static Map<String,String> parser(String webAppName) throws DocumentException {
        String webPath = webAppName + "/WEB-INF/web.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(webPath));
        List<Element> servletNodes = document.selectNodes("/web-app/servlet");
        Map<String,String> servletInfoMap = new HashMap<>();
        for(Element servletNode:servletNodes){
            Element servletNameElt = (Element) servletNode.selectSingleNode("servlet-name");
            String servletName = servletNameElt.getStringValue();
            Element servletClassElt = (Element) servletNode.selectSingleNode("servlet-class");
            String servletClass = servletClassElt.getStringValue();
            servletInfoMap.put(servletName,servletClass);
        }
        List<Element> servletMappingNodes = document.selectNodes("/web-app/servlet-mapping");
        Map<String,String> servletMappingInfoMap = new HashMap<>();
        for(Element servletMappingNode:servletMappingNodes){
            Element servletMappingNameElt = (Element) servletMappingNode.selectSingleNode("servlet-name");
            String servletMappingName = servletMappingNameElt.getStringValue();
            Element urlPatternElt = (Element) servletMappingNode.selectSingleNode("url-pattern");
            String urlPatternName = urlPatternElt.getStringValue();
            servletMappingInfoMap.put(servletMappingName,urlPatternName);
        }
        Set<String> servletNames = servletInfoMap.keySet();
        Map<String,String> servletMap = new HashMap<>();
        for(String servletName:servletNames){
            String urlPattern = servletMappingInfoMap.get(servletName);
            String className = servletInfoMap.get(servletName);
            servletMap.put(urlPattern,className);

        }
        return servletMap;
    }
}
