package javamon.core.statuses;

import javamon.core.ISecondaryEffect;
import javamon.core.pokemon.Pokemon;

/**
 * Represents a status condition that can be applied to a Pokémon as a secondary effect.
 * Handles conditions like paralysis, poison, burn, etc.
 */
public class StatusEffect implements ISecondaryEffect {
  private final String statusName;
  private final int chance; // percentage chance (0-100)
  
  /**
   * Creates a new status effect.
   * 
   * @param status The status to apply ("paralysis", "poison", "burn", "trap", "freeze")
   * @param chance Percentage chance of the effect being applied (0-100)
   */
  public StatusEffect(String statusName, int chance) {
    this.statusName = statusName;
    this.chance = chance;
  }
  
  @Override
  public void apply(Pokemon source, Pokemon target) {
    if (target.getStatus() == null) {
      Status newStatus = Status.createFromName(statusName);
      if (newStatus != null){
        target.setStatus(newStatus);
        System.out.println(target.getName() + " was " + getStatusVerb(statusName) + "!");
      }
    } else {
      System.out.println(target.getName() + " already has a status condition!");
    }
  }
  
  @Override
  public boolean triggers() {
    // Determine if the effect triggers based on its chance
    return Math.random() * 100 < chance;
  }
  
  @Override
  public String getDescription() {
    return "May cause " + statusName + " (" + chance + "% chance)";
  }
  
  /**
   * Gets the appropriate verb for the status message.
   */
  private String getStatusVerb(String statusName) {
    switch (statusName) {
      case "paralysis": return "paralyzed";
      case "poison": return "poisoned";
      case "burn": return "burned";
      case "freeze": return "frozen";
      case "trap": return "trapped";
      default: return "affected";
    }
  }
}