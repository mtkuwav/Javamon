package javamon.models.attacks;

import java.util.ArrayList;

import javamon.models.AttackModel;
import javamon.models.PokemonModel;
import javamon.models.SecondaryEffectModel;
import javamon.models.TypeModel;

/**
 * Base abstract class for attacks, handling common functionality between physical and special attacks.
 */
public abstract class BaseAttack extends AttackModel {

  // ┌───────────────────────────────┐
  // | -------- CONSTRUCTOR -------- |
  // └───────────────────────────────┘

  /**
   * Creates a new attack with the specified parameters.
   *
   * @param name The name of the attack
   * @param type The type of the attack
   * @param power The base power of the attack
   * @param secondaryEffects List of secondary effects that may be applied on hit
   */
  public BaseAttack(String name, TypeModel type, int power, 
                    ArrayList<SecondaryEffectModel> secondaryEffects) {
    super(name, type, power, secondaryEffects);
  }


  // ┌─────────────────────────────────────┐
  // | -------- EXECUTION METHODS -------- |
  // └─────────────────────────────────────┘

  /**
   * Executes this attack from the attacker to the target.
   * Calculates damage based on the attacker's Attack stat and target's Defense 
   * stat,
   * adjusted by type effectiveness, STAB bonus, and a random factor.
   * This is a method for executing an attack, with common logic shared between 
   * physical and special attacks.
   * 
   * @param attacker The Pokémon using the attack
   * @param target The Pokémon receiving the attack
   */
  @Override
  public final void execute(PokemonModel attacker, PokemonModel target) {
    double offensiveStat = getOffensiveStat(attacker);
    double defensiveStat = getDefensiveStat(target);
    
    double statFactor = offensiveStat / defensiveStat;
    double baseDamage = statFactor * getPower();

    double typeModifier = calculateTypeModifier(attacker, target);

    double randomFactor = 0.85 + Math.random() * 0.15;
    
    int damage = (int) (baseDamage * typeModifier * randomFactor);

    target.takeDamage(damage);

    applySecondaryEffects(attacker, target);
  }


  // ┌───────────────────────────────────────┐
  // | -------- CALCULATION METHODS -------- |
  // └───────────────────────────────────────┘

  /**
   * Calculates the type modifier for damage calculation.
   * Includes STAB (Same-Type Attack Bonus) and type effectiveness.
   *
   * @param attacker The Pokémon using the attack
   * @param target The Pokémon receiving the attack
   * @return The combined type modifier to be applied to damage
   */
  protected double calculateTypeModifier(PokemonModel attacker, PokemonModel target) {
    double stab = attacker.isOfType(getType()) ? 1.5 : 1.0;
    double typeEffectiveness = getType().getMultiplierAgainst(target.getTypes());
    return stab * typeEffectiveness;
  }
  
  /**
   * Gets the appropriate offensive stat for this attack type
   * (either Special or not).
   * 
   * @param pokemon The Pokémon using the attack
   * @return The offensive stat value to use in damage calculation
   */
  protected abstract double getOffensiveStat(PokemonModel pokemon);
  
  /**
   * Gets the appropriate defensive stat for this attack type
   * (either Special or not).
   * 
   * @param pokemon The Pokémon receiving the attack
   * @return The defensive stat value to use in damage calculation
   */
  protected abstract double getDefensiveStat(PokemonModel pokemon);
}