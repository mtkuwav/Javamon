package javamon.core.attacks;

import java.util.ArrayList;

import javamon.core.ISecondaryEffect;
import javamon.core.Type;
import javamon.core.pokemon.Pokemon;

/**
 * Represents a special attack in the game.
 * Special attacks use the attacker's Special Attack stat and the target's Special Defense stat
 * to calculate damage.
 */
public class SpecialAttack extends BaseAttack {

  /**
   * Creates a new special attack with the specified parameters.
   *
   * @param name The name of the attack
   * @param type The type of the attack
   * @param power The base power of the special attack
   * @param secondaryEffects List of secondary effects that may be applied on hit
   */
  public SpecialAttack(String name, Type type, int power, 
            ArrayList<ISecondaryEffect> secondaryEffects) {
    super(name, type, power, secondaryEffects);
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  protected double getOffensiveStat(Pokemon pokemon) {
    return pokemon.getSpecialAttack();
  }

  @Override
  protected double getDefensiveStat(Pokemon pokemon) {
    return pokemon.getSpecialDefense();
  }
}