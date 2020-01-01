package org.afterworld.campaigns.traits;

import android.content.Context;
import org.afterworld.campaigns.data.XmlReader;
import org.afterworld.campaigns.data.XmlWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TraitStore {

  private static TraitStore traitStore;

  public static TraitStore getInstance(Context context) {
    if (traitStore == null) {
      traitStore = new TraitStore(context);
      traitStore.loadData();
    }
    return traitStore;
  }

  private final Context context;
  private List<Trait> traits = new ArrayList<>();

  private TraitStore(Context context) {
    this.context = context;
  }

  public List<Trait> getTraits() {
    return traits;
  }

  // TODO: use map
  public Trait getTrait(String name) {
    for (Trait trait : traits) {
      if (trait.getName().equals(name)) {
        return trait;
      }
    }
    return null; // TODO create new trait?
  }

  public void addTrait(Trait trait) {
    traits.add(trait);
    storeData();
  }

  public void removeTrait(Trait trait) {
    traits.remove(trait);
    storeData();
  }

  private void loadData() {
    FileInputStream inputStream = null;
    try {
      inputStream = context.openFileInput("traits.xml");
      loadXml(inputStream);
    } catch (FileNotFoundException e) {
      // File does not exist, skipping...
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void storeData() {
    FileOutputStream outputStream = null;
    try {
      outputStream = context.openFileOutput("traits.xml", Context.MODE_PRIVATE);
      writeXml(outputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void loadXml(FileInputStream inputStream) {
    XmlReader xmlReader = new XmlReader(inputStream);

    xmlReader.assertTag("traits");
    List<XmlReader> traitReaders = xmlReader.getChildren("trait");
    for (XmlReader traitReader : traitReaders) {
      String name = traitReader.getValue("name");
      Trait trait = new Trait(name);
      traits.add(trait);
    }
  }

  private void writeXml(FileOutputStream outputStream) {
    XmlWriter writer = new XmlWriter("traits");

    for (Trait trait : traits) {
      XmlWriter traitWriter = writer.addChild("trait");
      traitWriter.addValue("name", trait.getName());
    }

    writer.write(outputStream);
  }

}
