package javamon.core.effects;

import javamon.core.ISecondaryEffect;
import javamon.core.pokemon.Pokemon;

/**
 * Represents a confusion effect that can cause a Pokémon to hurt itself.
 */
public class ConfusionEffect implements ISecondaryEffect {
  private final int chance;
  private final int minTurns;
  private final int maxTurns;
  
  /**
   * Creates a new confusion effect.
   * 
   * @param chance Percentage chance of the effect being applied
   * @param minTurns Minimum number of turns the confusion will last
   * @param maxTurns Maximum number of turns the confusion will last
   */
  public ConfusionEffect(int chance, int minTurns, int maxTurns) {
    this.chance = chance;
    this.minTurns = minTurns;
    this.maxTurns = maxTurns;
  }
  
  @Override
  public void apply(Pokemon source, Pokemon target) {
    int duration = minTurns + (int)(Math.random() * (maxTurns - minTurns + 1));
    
    target.setConfused(true);
    target.setConfusionCounter(duration);
    
    System.out.println(target.getName() + " became confused!");
  }
  
  @Override
  public boolean triggers() {
    return Math.random() * 100 < chance;
  }
  
  @Override
  public String getDescription() {
    return "May confuse the target (" + chance + "% chance)";
  }
}