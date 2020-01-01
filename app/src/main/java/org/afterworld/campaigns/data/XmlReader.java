package org.afterworld.campaigns.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {

  private Element element;

  private XmlReader(Element element) {
    this.element = element;
  }

  public XmlReader(InputStream inputStream) {
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = documentBuilder.parse(inputStream);
      this.element = document.getDocumentElement();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public void assertTag(String tagName) {
    if (!element.getTagName().equals(tagName)) {
      throw new AssertionError("Expected xml with tag '" + tagName + "', but got '" + element.getTagName() + "'");
    }
  }

  public String getValue(String tag) {
    NodeList list = element.getElementsByTagName(tag);
    if (list.getLength() == 0) {
      throw new IllegalStateException("No child tag '" + tag + "' in '" + element.getTagName() + "'");
    }
    Node firstItem = list.item(0);
    return firstItem.getFirstChild().getNodeValue();
  }

  public int getIntValue(String tag) {
    String value = getValue(tag);
    return Integer.parseInt(value);
  }

  public List<XmlReader> getChildren(String elementName) {
    NodeList list = element.getElementsByTagName(elementName);

    ArrayList<XmlReader> result = new ArrayList<>();
    for (int i = 0; i < list.getLength(); i++) {
      Element item = (Element) list.item(i);
      result.add(new XmlReader(item));
    }
    return result;
  }

}
