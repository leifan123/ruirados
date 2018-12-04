package com.ruirados.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static Element getRootElementFromXML(InputStream is) {
        Element rootElement = null;

        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            rootElement = doc.getDocumentElement();

        } catch (Exception ex) {
            return null;
        }

        return rootElement;

    }

    public static Map<String, String> getSingleValuesFromXMLByPath(String is, String[] tagNames) {

        Map<String, String> returnValues = new HashMap<String, String>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(is.getBytes());
            Document doc = docBuilder.parse(stream);
            Element rootElement = doc.getDocumentElement();
            for (int i = 0; i < tagNames.length; i++) {
                Node node = selectSingleNode(tagNames[i], rootElement);
                if (node == null) {
                } else {
                    returnValues.put(tagNames[i], node.getTextContent());
                }
            }
        } catch (Exception ex) {
            return returnValues;
        }
        return returnValues;
    }

    public static Map<String, String> formatKeyMap(String nodePath, String[] keys) {

        Map<String, String> keyMap = new HashMap<String, String>();

        for (int i = 0; i < keys.length; i++) {
            String pathKey = nodePath + keys[i];
            keyMap.put(keys[i], pathKey);
        }

        return keyMap;
    }

    public static String[] formatKeyString(String nodePath, String[] keys) {

        for (int i = 0; i < keys.length; i++) {
            keys[i] = nodePath + keys[i];

        }

        return keys;
    }


    public static Map<String, String> getSingleValueFromXML(String is, String[] tagNames) {
        Map<String, String> returnValues = new HashMap<String, String>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(is.getBytes());
            Document doc = docBuilder.parse(stream);
            Element rootElement = doc.getDocumentElement();
            //printCommendResult(rootElement);
            for (int i = 0; i < tagNames.length; i++) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagNames[i]);
                if (targetNodes.getLength() <= 0) {
                } else {
                    returnValues.put(tagNames[i], targetNodes.item(0).getTextContent());
                }
            }
        } catch (Exception ex) {
            return returnValues;
        }
        return returnValues;
    }

    public static Map<String, Object> getValueFromXML(String is, String[] tagNames) {
        Map<String, Object> returnValues = new HashMap<String, Object>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(is.getBytes());
            Document doc = docBuilder.parse(stream);
            Element rootElement = doc.getDocumentElement();
            //printCommendResult(rootElement);
            for (int i = 0; i < tagNames.length; i++) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagNames[i]);
                if (targetNodes.getLength() <= 0) {
                } else {
                    returnValues.put(tagNames[i], targetNodes.item(0).getTextContent());
                }
            }
        } catch (Exception ex) {
            return returnValues;
        }
        return returnValues;
    }

    public static Map<String, String> getSingleValueFromXML(InputStream is, String[] tagNames) {
        Map<String, String> returnValues = new HashMap<String, String>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            NodeList nodeList = doc.getFirstChild().getChildNodes();
            Node resultNode = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (!node.getNodeName().equals("count")) {
                    resultNode = node;
                }
            }
            if (resultNode == null) {
                throw new Exception("No result...");
            }

            for (String tagName : tagNames) {
                if (tagName.equals(resultNode.getNodeName())) {
                    returnValues.put(tagName, resultNode.getTextContent());
                } else {
                    NodeList targetNodes = resultNode.getChildNodes();
                    if (targetNodes.getLength() <= 0) {
                    } else {
                        for (int i = 0; i < targetNodes.getLength(); i++) {
                            Node node = targetNodes.item(i);
                            if (tagName.equals(node.getNodeName())) {
                                returnValues.put(tagName, node.getTextContent());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return returnValues;

        }
        return returnValues;
    }

    public static List<Map<String, String>> getResultListFromXML(InputStream is) {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList rootNodes = rootElement.getChildNodes();
            if (rootNodes.getLength() <= 0) {
            } else {
                for (int i = 0; i < rootNodes.getLength(); i++) {
                    Map<String, String> returnValues = new HashMap<String, String>();
                    if (!rootNodes.item(i).getNodeName().equals("count")) {
                        NodeList childNodes = rootNodes.item(i).getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            String nodeName =childNodes.item(j).getNodeName();
                            String nodeText = returnValues.get(nodeName);
                            if (nodeText != null) {
                                nodeText += childNodes.item(j).getTextContent();
                            } else {
                                nodeText = childNodes.item(j).getTextContent();
                            }
                            returnValues.put(nodeName, nodeText);
                        }
                        results.add(returnValues);
                    }
                }
            }
        } catch (Exception ex) {
            return results;
        }
        return results;
    }

    public static Map<String, String> getSingleValueFromXML(Element rootElement, String[] tagNames) {
        Map<String, String> returnValues = new HashMap<String, String>();
        if (rootElement == null) {
            return null;
        }
        try {
            for (int i = 0; i < tagNames.length; i++) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagNames[i]);
                if (targetNodes.getLength() <= 0) {
                } else {
                    returnValues.put(tagNames[i], targetNodes.item(0).getTextContent());
                }
            }
        } catch (Exception ex) {
            return returnValues;
        }
        return returnValues;
    }

    //  查找节点，并返回第一个符合条件节点
    private static Node selectSingleNode(String express, Object source) {
        Node result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            return result;
        }

        return result;
    }

    //  查找节点，返回符合条件的节点集。
    public static NodeList selectNodes(String express, Object source) {
        NodeList result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            result = (NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            return result;
        }

        return result;
    }

    public static Map<String, List<String>> getMultipleValuesFromXML(InputStream is, String[] tagNames) {
        Map<String, List<String>> returnValues = new HashMap<String, List<String>>();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();
            for (String tagName : tagNames) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagName);
                if (targetNodes.getLength() <= 0) {
                } else {
                    List<String> valueList = new ArrayList<String>();
                    for (int j = 0; j < targetNodes.getLength(); j++) {
                        Node node = targetNodes.item(j);
                        valueList.add(node.getTextContent());
                    }
                    returnValues.put(tagName, valueList);
                }
            }
        } catch (Exception ex) {
            return returnValues;
        }
        return returnValues;
    }

    public static void printCommendResult(Element root) {
        if (root != null) {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
            }
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            try {
                transformer.transform(new DOMSource(root), new StreamResult(buffer));
            } catch (TransformerException e) {
            }
        }
    }

    public static String getNode(String root, String nodeName, int index) {
        String nodeString = null;
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(root.getBytes());
            Document doc = docBuilder.parse(stream);
            Element rootElement = doc.getDocumentElement();
            NodeList vms = rootElement.getElementsByTagName(nodeName);
            Node node = vms.item(index);
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
            }
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            try {
                transformer.transform(new DOMSource(node), new StreamResult(buffer));
                nodeString = buffer.toString();
            } catch (TransformerException e) {
            }
        } catch (Exception ex) {
        }
        return nodeString;
    }

    public static NodeList getNodeListByNodeName(InputStream is, String nodeName) {
        NodeList nls = null;
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();
            printCommendResult(rootElement);
            nls = selectNodes(nodeName, rootElement);
        } catch (Exception ex) {
            return null;
        }
        return nls;
    }
    
    
    
    
    public static void main(String[] args) {
		String s="{\"listvirtualmachinesresponse\":{\"count\":10,\"virtualmachine\":[{\"id\":\"c2b01c2e-4586-4561-8b7a-ffee441d9e12\",\"name\":\"QA-c2b01c2e-4586-4561-8b7a-ffee441d9e12\",\"displayname\":\"QA-c2b01c2e-4586-4561-8b7a-ffee441d9e12\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-07T10:31:37+0800\",\"state\":\"Stopped\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"d6dc9678-9c54-4350-864e-cacd62f35e0f\",\"serviceofferingname\":\"1核+1Ghz+1GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":1024,\"cpuused\":\"10%\",\"networkkbsread\":98304,\"networkkbswrite\":49152,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"5237b3aa-4e33-4c5e-b7bb-07da23a11c44\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.54\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:03:b1:00:4b\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-528-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"884b9d4f-87a4-4587-b057-d8e0fa08fe36\",\"name\":\"QA-884b9d4f-87a4-4587-b057-d8e0fa08fe36\",\"displayname\":\"QA-884b9d4f-87a4-4587-b057-d8e0fa08fe36\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-20T14:53:51+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"3d403ce7-d9af-4aad-927c-58e41448f107\",\"hostname\":\"SimulatedAgent.a1fb081c-dba5-48a5-a56a-4cbeb65d5a29\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":51118080,\"networkkbswrite\":25559040,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"5e97c434-82ea-4c86-bb78-1144874e0bed\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.3\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:69:64:00:5e\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-551-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"8bb5f0b4-d2f3-4867-9233-14304ae624d4\",\"name\":\"QA-8bb5f0b4-d2f3-4867-9233-14304ae624d4\",\"displayname\":\"QA-8bb5f0b4-d2f3-4867-9233-14304ae624d4\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-19T14:56:52+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"5cd833e3-4c33-48e6-be52-d814dbd632f7\",\"hostname\":\"SimulatedAgent.b07b5acb-5f2b-459d-b96c-1dc8bdc76af8\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":97976320,\"networkkbswrite\":48988160,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"aa2cf065-b990-4ea8-973d-6e5410927966\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.164\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:2a:a0:00:5b\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-550-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"5174791f-4311-4136-b46d-8e049765c346\",\"name\":\"QA-5174791f-4311-4136-b46d-8e049765c346\",\"displayname\":\"QA-5174791f-4311-4136-b46d-8e049765c346\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-19T14:54:08+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"7d207bf8-7d20-4521-a043-d3b0606e0241\",\"hostname\":\"SimulatedAgent.0dc28f2f-376f-4b2a-8b9a-fd7d04005aac\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":98074624,\"networkkbswrite\":49037312,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"3f606c5a-066f-445b-bd5e-92c53fa6c519\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.89\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:62:f0:00:5a\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-549-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"98455c4d-d6f0-4a3c-8188-529fe0848e01\",\"name\":\"QA-98455c4d-d6f0-4a3c-8188-529fe0848e01\",\"displayname\":\"QA-98455c4d-d6f0-4a3c-8188-529fe0848e01\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-19T14:41:50+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"3d403ce7-d9af-4aad-927c-58e41448f107\",\"hostname\":\"SimulatedAgent.a1fb081c-dba5-48a5-a56a-4cbeb65d5a29\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":98467840,\"networkkbswrite\":49233920,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"e14c2baa-ad69-40a3-88de-3aa287341153\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.17\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:51:e3:00:58\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-548-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"c6daaf3a-401b-49d2-b1c2-47a0fbc1b232\",\"name\":\"QA-c6daaf3a-401b-49d2-b1c2-47a0fbc1b232\",\"displayname\":\"QA-c6daaf3a-401b-49d2-b1c2-47a0fbc1b232\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-19T10:36:14+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"5cd833e3-4c33-48e6-be52-d814dbd632f7\",\"hostname\":\"SimulatedAgent.b07b5acb-5f2b-459d-b96c-1dc8bdc76af8\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":106496000,\"networkkbswrite\":53248000,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"c156752c-3776-4bdf-912a-10d2e898c8e0\",\"networkid\":\"b5000947-9e85-464d-b5be-9c1f1635ee23\",\"networkname\":\"14818583841220170619111917\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.110\",\"isolationuri\":\"vlan://197\",\"broadcasturi\":\"vlan://197\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:74:24:00:01\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-547-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"5f23e049-7b9f-417d-b52c-6bd51fa413c8\",\"name\":\"QA-5f23e049-7b9f-417d-b52c-6bd51fa413c8\",\"displayname\":\"QA-5f23e049-7b9f-417d-b52c-6bd51fa413c8\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-21T09:46:16+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"7d207bf8-7d20-4521-a043-d3b0606e0241\",\"hostname\":\"SimulatedAgent.0dc28f2f-376f-4b2a-8b9a-fd7d04005aac\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":14123008,\"networkkbswrite\":7061504,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"4f96b6b8-f6ac-4be9-83ec-774f3d10a1fa\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.132\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:60:88:00:62\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-555-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"65698c18-9fb3-48ce-84b0-fa9ed2f848d6\",\"name\":\"QA-65698c18-9fb3-48ce-84b0-fa9ed2f848d6\",\"displayname\":\"QA-65698c18-9fb3-48ce-84b0-fa9ed2f848d6\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-21T09:43:08+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"7d207bf8-7d20-4521-a043-d3b0606e0241\",\"hostname\":\"SimulatedAgent.0dc28f2f-376f-4b2a-8b9a-fd7d04005aac\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":14221312,\"networkkbswrite\":7110656,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"9d6c9d6c-b583-4daf-bd0b-3b40765d0b0e\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.154\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:77:f3:00:61\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-554-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"d735d13a-efd5-43eb-a621-9a4d3d46621c\",\"name\":\"QA-d735d13a-efd5-43eb-a621-9a4d3d46621c\",\"displayname\":\"QA-d735d13a-efd5-43eb-a621-9a4d3d46621c\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-20T15:52:28+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"5cd833e3-4c33-48e6-be52-d814dbd632f7\",\"hostname\":\"SimulatedAgent.b07b5acb-5f2b-459d-b96c-1dc8bdc76af8\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":49217536,\"networkkbswrite\":24608768,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"2d4247cb-2a82-4b27-a1a0-96d786ef45fd\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.88\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:22:d1:00:60\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-553-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]},{\"id\":\"f21c07a3-842f-49b1-9bf4-fe881f2e808c\",\"name\":\"QA-f21c07a3-842f-49b1-9bf4-fe881f2e808c\",\"displayname\":\"QA-f21c07a3-842f-49b1-9bf4-fe881f2e808c\",\"account\":\"admin\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"username\":\"admin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"created\":\"2017-06-20T15:25:40+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"北京1区\",\"hostid\":\"3d403ce7-d9af-4aad-927c-58e41448f107\",\"hostname\":\"SimulatedAgent.a1fb081c-dba5-48a5-a56a-4cbeb65d5a29\",\"templateid\":\"be62d248-19c1-11e7-aac7-005056ac4aa8\",\"templatename\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"templatedisplaytext\":\"CentOS 5.6 (64-bit) no GUI (Simulator)\",\"passwordenabled\":true,\"serviceofferingid\":\"bbe2b47b-11cb-45fc-b1c0-ac002366f961\",\"serviceofferingname\":\"1核+1Ghz+2GMemory\",\"diskofferingid\":\"fcafa58a-573c-4606-b729-c65bf041b004\",\"diskofferingname\":\"Custom\",\"cpunumber\":1,\"cpuspeed\":1024,\"memory\":2048,\"cpuused\":\"10%\",\"networkkbsread\":50102272,\"networkkbswrite\":25051136,\"diskkbsread\":0,\"diskkbswrite\":0,\"memorykbs\":0,\"memoryintfreekbs\":0,\"memorytargetkbs\":0,\"diskioread\":0,\"diskiowrite\":0,\"guestosid\":\"6cf5aeb2-19c1-11e7-aac7-005056ac4aa8\",\"rootdeviceid\":0,\"rootdevicetype\":\"ROOT\",\"securitygroup\":[],\"nic\":[{\"id\":\"85d2ef46-cd29-42a2-ad53-757087ddbda9\",\"networkid\":\"8477a58d-8942-4f10-a6e4-6eb950c7c8dc\",\"networkname\":\"南方大区\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.1.1\",\"ipaddress\":\"192.168.1.116\",\"isolationuri\":\"vlan://165\",\"broadcasturi\":\"vlan://165\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:6c:f5:00:5f\",\"secondaryip\":[]}],\"hypervisor\":\"Simulator\",\"instancename\":\"i-2-552-QA\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":142,\"tags\":[]}]}}";
		InputStream is = new ByteArrayInputStream(s.getBytes());
		List<Map<String, String>>  list_map=XmlUtil.getResultListFromXML(is);
		/*for (Map<String, String> map : list_map) {
			System.out.println(list_map);
		}
		*/
    }
}
