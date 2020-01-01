package org.afterworld.campaigns.characters;

import org.afterworld.campaigns.traits.Trait;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampaignCharacter {

  public final String id = UUID.randomUUID().toString();
  private String name;
  private int hp = 5;
  private int maxHp = 5;

  private List<Trait> traits = new ArrayList<>();

  public CampaignCharacter(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  public List<Trait> getTraits() {
    return traits;
  }
}
