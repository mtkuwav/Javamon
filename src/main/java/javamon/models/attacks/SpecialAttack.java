package javamon.models.attacks;

import java.util.ArrayList;

import javamon.models.PokemonModel;
import javamon.models.SecondaryEffectModel;
import javamon.models.TypeModel;

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
  public SpecialAttack(String name, TypeModel type, int power, 
            ArrayList<SecondaryEffectModel> secondaryEffects) {
    super(name, type, power, secondaryEffects);
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  protected double getOffensiveStat(PokemonModel pokemon) {
    return pokemon.getSpecialAttack();
  }

  @Override
  protected double getDefensiveStat(PokemonModel pokemon) {
    return pokemon.getSpecialDefense();
  }
}