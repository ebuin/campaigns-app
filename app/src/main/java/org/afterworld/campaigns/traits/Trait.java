package org.afterworld.campaigns.traits;

public class Trait {

  private String name;

  public Trait(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean increasesMaxHp() {
    return name.toLowerCase().startsWith("max hp +");
  }
  
}
