package org.afterworld.campaigns.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

public class XmlWriter {

  private Element element;
  private Document document;

  private XmlWriter(Element element, Document document) {
    this.element = element;
    this.document = document;
  }

  public XmlWriter(String elementName) {
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = documentBuilder.newDocument();
      element = document.createElement(elementName);
      document.appendChild(element);
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException(e);
    }
  }

  public void addValue(String tag, String value) {
    Element valueElement = document.createElement(tag);
    valueElement.appendChild(document.createTextNode(value));
    element.appendChild(valueElement);
  }

  public void addValue(String tag, int value) {
    addValue(tag, Integer.toString(value));
  }

  public XmlWriter addChild(String tag) {
    Element childElement = document.createElement(tag);
    element.appendChild(childElement);
    return new XmlWriter(childElement, document);
  }

  public void write(OutputStream outputStream) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(document);

      transformer.transform(source, new StreamResult(outputStream));
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
