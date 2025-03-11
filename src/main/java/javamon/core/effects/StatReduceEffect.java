package javamon.core.effects;

import javamon.core.ISecondaryEffect;
import javamon.core.pokemon.Pokemon;

/**
 * Represents an effect that reduces a target's specific stat.
 * Used by attacks like Psychic which can lower the opponent's Special Defense.
 */
public class StatReduceEffect implements ISecondaryEffect {
  private final String stat;
  private final int stages;
  private final int chance;
  
  /**
   * Creates a new StatReduceEffect.
   * 
   * @param stat The stat to modify ("attack", "defense", "special_attack", "special_defense", "speed")
   * @param stages How many stages to reduce the stat by (positive number)
   * @param chance Percentage chance of the effect being applied (0-100)
   */
  public StatReduceEffect(String stat, int stages, int chance) {
    this.stat = stat;
    this.stages = stages;
    this.chance = chance;
  }
  
  @Override
  public void apply(Pokemon source, Pokemon target) {
    switch (stat.toLowerCase()) {
      case "attack":
        target.modifyAttack(-stages);
        break;
      case "defense":
        target.modifyDefense(-stages);
        break;
      case "special_attack":
        target.modifySpecialAttack(-stages);
        break;
      case "special_defense":
        target.modifySpecialDefense(-stages);
        break;
      case "speed":
        target.modifySpeed(-stages);
        break;
    }
    System.out.println(target.getName() + "'s " + formatStatName(stat) + " fell!");
  }
  
  @Override
  public boolean triggers() {
    return Math.random() * 100 < chance;
  }
  
  @Override
  public String getDescription() {
    return "May lower target's " + formatStatName(stat) + " (" + chance + "% chance)";
  }
  
  /**
   * Formats a stat name to be more readable.
   * Converts "special_defense" to "Special Defense", etc.
   * 
   * @param statName The raw stat name from JSON
   * @return A formatted, display-friendly stat name
   */
  private String formatStatName(String statName) {
    // Replace underscores with spaces and capitalize each word
    String[] words = statName.split("_");
    StringBuilder result = new StringBuilder();
    
    for (String word : words) {
      if (!word.isEmpty()) {
        result.append(Character.toUpperCase(word.charAt(0)))
          .append(word.substring(1))
          .append(" ");
      }
    }
    
    return result.toString().trim();
  }
}