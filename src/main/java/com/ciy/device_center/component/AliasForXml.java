package com.ciy.device_center.component;

import org.springframework.boot.system.ApplicationHome;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AliasForXml implements IAlias {

    public static String ELEMENT_ALIAS_LIST = "aliasList";
    public static String ELEMENT_ALIAS = "alias";

    public static String DEVICE_CODE_KEY = "deviceCode";
    public static String ALIAS_VALUE = "alias";

    /**
     * 保存别名
     *
     * @param alias key: 设备id，value: 别名
     */
    @Override
    public void saveAlias(Map<String, String> alias) {
        File aliasFile = getAliasFile();
        if (aliasFile != null) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = documentBuilderFactory.newDocumentBuilder();
                Document document = builder.newDocument();
                Element aliasListElement = document.createElement(ELEMENT_ALIAS_LIST);
                alias.forEach((key, value) -> {
                    Element aliasElement = document.createElement(ELEMENT_ALIAS);
                    aliasElement.setAttribute(DEVICE_CODE_KEY, key);
                    aliasElement.setAttribute(ALIAS_VALUE, value);
                    aliasListElement.appendChild(aliasElement);
                });
                document.appendChild(aliasListElement);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(document), new StreamResult(aliasFile));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载别名
     *
     * @return
     */
    @Override
    public Map<String, String> loadAlias() {
        HashMap<String, String> aliasGroup = new HashMap<>();
        File aliasFile = getAliasFile();
        if (aliasFile != null) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = documentBuilderFactory.newDocumentBuilder();
                Document document = builder.parse(aliasFile);
                NodeList aliasListNodeList = document.getElementsByTagName(ELEMENT_ALIAS_LIST);
                for (int i = 0; i < aliasListNodeList.getLength(); i++) {
                    Node item = aliasListNodeList.item(i);
                    if (item instanceof Element) {
                        NodeList aliasNodeList = ((Element) item).getElementsByTagName(ELEMENT_ALIAS);
                        for (int j = 0; j < aliasNodeList.getLength(); j++) {
                            Element element = (Element) aliasNodeList.item(j);
                            String key = element.getAttribute(DEVICE_CODE_KEY);
                            String value = element.getAttribute(ALIAS_VALUE);
                            aliasGroup.put(key, value);
                        }
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return aliasGroup;
    }

    /**
     * 获取 Alias.xml
     *
     * @return
     */
    public File getAliasFile() {
        try {
            String path = new ApplicationHome(getClass()).getSource().getParentFile().getPath();
            File xmlFile = new File(path, "xml/");
            if (!xmlFile.exists()) {
                xmlFile.mkdirs();
            }
            xmlFile = new File(xmlFile, "alias.xml");
            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
            }
            if (xmlFile.exists()) {
                return xmlFile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
