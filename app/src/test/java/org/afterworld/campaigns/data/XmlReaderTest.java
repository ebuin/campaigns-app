package org.afterworld.campaigns.data;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class XmlReaderTest {

  @Test
  public void testAssertTag() throws Exception {
    String xml = "<trait/>";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

    XmlReader xmlReader = new XmlReader(inputStream);

    xmlReader.assertTag("trait");
  }

  @Test(expected = AssertionError.class)
  public void testAssertTag_error() throws Exception {
    String xml = "<trait/>";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

    XmlReader xmlReader = new XmlReader(inputStream);

    xmlReader.assertTag("character");
  }


  @Test
  public void testGetValue() throws Exception {
    String xml = "" +
        "<trait>\n" +
        "  <name>Max Hp + 5</name>\n" +
        "</trait>\n";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

    XmlReader xmlReader = new XmlReader(inputStream);

    assertThat(xmlReader.getValue("name"), equalTo("Max Hp + 5"));
  }

  @Test
  public void testElements() throws Exception {
    String xml = "" +
        "<traits>\n" +
        "  <trait>\n" +
        "    <name>Max Hp + 5</name>\n" +
        "  </trait>\n" +
        "  <trait>\n" +
        "    <name>Strength + 1</name>\n" +
        "  </trait>\n" +
        "</traits>";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

    XmlReader xmlReader = new XmlReader(inputStream);

    List<XmlReader> children = xmlReader.getChildren("trait");
    assertThat(children, hasSize(2));

    assertThat(children.get(0).getValue("name"), equalTo("Max Hp + 5"));
    assertThat(children.get(1).getValue("name"), equalTo("Strength + 1"));
  }
}