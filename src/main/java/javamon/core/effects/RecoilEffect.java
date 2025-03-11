package javamon.core.effects;

import javamon.core.ISecondaryEffect;
import javamon.core.pokemon.Pokemon;

/**
 * Represents a recoil effect that damages the attacker based on damage dealt.
 * Used by attacks like Double-Edge where the user takes a fraction of damage dealt.
 */
public class RecoilEffect implements ISecondaryEffect {
  private final int fraction;
  private final int chance;
  
  /**
   * Creates a new recoil effect.
   * 
   * @param fraction The denominator of the fraction of damage to take as recoil
   *        (e.g., 3 means 1/3 of the damage is taken as recoil)
   * @param chance Percentage chance of the effect being applied
   */
  public RecoilEffect(int fraction, int chance) {
    this.fraction = fraction;
    this.chance = chance;
  }
  
  @Override
  public void apply(Pokemon source, Pokemon target, int damageDealt) {
    int recoilDamage = damageDealt / fraction;
    source.takeDamage(recoilDamage);
    System.out.println(source.getName() + " is hurt by recoil!");
  }

  // Fallback to estimaton if damage dealt isn't found 
  @Override
  public void apply(Pokemon source, Pokemon target) {
    int estimatedDamage = Math.max(10, source.getAttack() / 2);
    int recoilDamage = estimatedDamage / fraction;
    source.takeDamage(recoilDamage);
    System.out.println(source.getName() + " is hurt by recoil!");
  }
  
  @Override
  public boolean triggers() {
    return Math.random() * 100 < chance;
  }
  
  @Override
  public String getDescription() {
    return "User takes 1/" + fraction + " of damage dealt as recoil";
  }
}