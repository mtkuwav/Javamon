package javamon.core.effects;

import javamon.core.ISecondaryEffect;
import javamon.core.pokemon.Pokemon;

/**
 * Represents a drain effect that heals the attacker based on damage dealt.
 * Used by attacks like Giga Drain where the user recovers a fraction of damage dealt.
 */
public class DrainEffect implements ISecondaryEffect {
  private final int fraction;
  private final int chance;
  
  /**
   * Creates a new drain effect.
   * 
   * @param fraction The denominator of the fraction to heal (e.g., 2 means heal 1/2 of damage dealt)
   * @param chance Percentage chance of the effect being applied
   */
  public DrainEffect(int fraction, int chance) {
    this.fraction = fraction;
    this.chance = chance;
  }
  
  @Override
  public void apply(Pokemon source, Pokemon target) {
    // In a real implementation, we would need to know the damage dealt
    // This is a simplified approach that estimates damage based on attack stats
    int estimatedDamage;
    
    if (source.getSpecialAttack() > source.getAttack()) {
      estimatedDamage = Math.max(10, source.getSpecialAttack() / 2);
    } else {
      estimatedDamage = Math.max(10, source.getAttack() / 2);
    }

    int healAmount = estimatedDamage / fraction;
    source.heal(healAmount);

    System.out.println(source.getName() + " had its energy drained!");
  }

  @Override
  public void apply(Pokemon source, Pokemon target, int damageDealt) {
    int healAmount = damageDealt / fraction;
    source.heal(healAmount);
    System.out.println(source.getName() + " absorbed energy!");
  }
  
  @Override
  public boolean triggers() {
    return Math.random() * 100 < chance;
  }
  
  @Override
  public String getDescription() {
    return "Drains 1/" + fraction + " of the damage dealt";
  }
}