package org.afterworld.campaigns.characters;

import android.content.Context;
import org.afterworld.campaigns.data.XmlReader;
import org.afterworld.campaigns.data.XmlWriter;
import org.afterworld.campaigns.traits.Trait;
import org.afterworld.campaigns.traits.TraitStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampaignCharacterStore {

  private static CampaignCharacterStore store;

  public static CampaignCharacterStore getInstance(Context context) {
    if (store == null) {
      store = new CampaignCharacterStore(context);
      store.loadData();
    }
    return store;
  }

  private final Context context;
  private List<CampaignCharacter> characters = new ArrayList<>();
  private Map<String, CampaignCharacter> characterMap = new HashMap<>();

  public CampaignCharacterStore(Context context) {
    this.context = context;
  }

  public void addCharacter(CampaignCharacter campaignCharacter) {
    characters.add(campaignCharacter);
    characterMap.put(campaignCharacter.id, campaignCharacter);
    storeData();
  }

  public void updateCharacter(CampaignCharacter campaignCharacter) {
    storeData();
  }

  public List<CampaignCharacter> getCharacters() {
    return characters;
  }

  public CampaignCharacter getCharacter(String id) {
    return characterMap.get(id);
  }

  private void loadData() {
    FileInputStream inputStream = null;
    try {
      inputStream = context.openFileInput("characters.xml");
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
      outputStream = context.openFileOutput("characters.xml", Context.MODE_PRIVATE);
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
    TraitStore traitStore = TraitStore.getInstance(context);
    XmlReader xmlReader = new XmlReader(inputStream);

    xmlReader.assertTag("characters");
    List<XmlReader> characterReaders = xmlReader.getChildren("character");
    for (XmlReader characterReader : characterReaders) {
      CampaignCharacter character = new CampaignCharacter(characterReader.getValue("name"));
      character.setHp(characterReader.getIntValue("hp"));
      character.setMaxHp(characterReader.getIntValue("maxHp"));
      characters.add(character);
      characterMap.put(character.id, character);

      List<XmlReader> traitsReaders = characterReader.getChildren("traits");
      if (!traitsReaders.isEmpty()) {
        XmlReader traitsReader = traitsReaders.get(0);
        List<XmlReader> traitReaders = traitsReader.getChildren("trait");
        for (XmlReader traitReader : traitReaders) {
          String name = traitReader.getValue("name");
          Trait trait = traitStore.getTrait(name);
          if (trait != null) {
            character.getTraits().add(trait);
          }
        }
      }
    }
  }

  private void writeXml(FileOutputStream outputStream) {
    XmlWriter writer = new XmlWriter("characters");

    for (CampaignCharacter character : characters) {
      XmlWriter characterWriter = writer.addChild("character");
      characterWriter.addValue("name", character.getName());
      characterWriter.addValue("hp", character.getHp());
      characterWriter.addValue("maxHp", character.getMaxHp());

      XmlWriter traitsWriter = characterWriter.addChild("traits");
      for (Trait trait : character.getTraits()) {
        XmlWriter traitWriter = traitsWriter.addChild("trait");
        traitWriter.addValue("name", trait.getName());
      }
    }

    writer.write(outputStream);
  }
}
