package javamon.models.attacks;

import java.util.ArrayList;

import javamon.models.PokemonModel;
import javamon.models.SecondaryEffectModel;
import javamon.models.TypeModel;

/**
 * Represents a physical attack in the game.
 * Physical attacks use the attacker's Attack stat and the target's Defense stat
 * to calculate damage.
 */
public class PhysicalAttack extends BaseAttack {

  /**
   * Creates a new physical attack with the specified parameters.
   *
   * @param name The name of the attack
   * @param type The type of the attack
   * @param power The base power of the attack
   * @param secondaryEffects List of secondary effects that may be applied on hit
   */
  public PhysicalAttack(String name, TypeModel type, int power, 
            ArrayList<SecondaryEffectModel> secondaryEffects) {
    super(name, type, power, secondaryEffects);
  }

  @Override
  public boolean isSpecial() {
    return false;
  }

  @Override
  protected double getOffensiveStat(PokemonModel pokemon) {
    return pokemon.getAttack();
  }

  @Override
  protected double getDefensiveStat(PokemonModel pokemon) {
    return pokemon.getDefense();
  }
}