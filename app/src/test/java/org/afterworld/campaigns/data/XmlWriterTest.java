package org.afterworld.campaigns.data;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.Assert.assertThat;

public class XmlWriterTest {


  @Test
  public void testWriteXml() throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    XmlWriter xmlWriter = new XmlWriter("traits");
    xmlWriter.write(outputStream);

    assertThat(outputStream.toString("UTF-8"), matchesXml("<traits/>"));
  }

  @Test
  public void testAddValue() throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    XmlWriter xmlWriter = new XmlWriter("trait");
    xmlWriter.addValue("name", "Max Hp + 5");
    xmlWriter.write(outputStream);

    assertThat(outputStream.toString("UTF-8"), matchesXml("<trait><name>Max Hp + 5</name></trait>"));
  }

  @Test
  public void testAddChild() throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    XmlWriter xmlWriter = new XmlWriter("traits");

    XmlWriter trait1 = xmlWriter.addChild("trait");
    trait1.addValue("name", "Max Hp + 5");

    XmlWriter trait2 = xmlWriter.addChild("trait");
    trait2.addValue("name", "Strength + 1");

    xmlWriter.write(outputStream);

    assertThat(outputStream.toString("UTF-8"),
        matchesXml("" +
            "<traits>" +
            "<trait><name>Max Hp + 5</name></trait>" +
            "<trait><name>Strength + 1</name></trait>" +
            "</traits>"));
  }

  private Matcher<String> matchesXml(String content) {
    return equalToCompressingWhiteSpace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + content);
  }
}