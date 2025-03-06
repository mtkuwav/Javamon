package javamon.models;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract class representing a Pokemon attack model.
 * 
 * This class defines the base structure for all attack types in the game.
 * Each attack has a name, type, power, and optional secondary effects.
 * The implementation of how the attack is executed is defined by subclasses
 * through the abstract methods.
 * 
 * Attacks can be either physical or special, which affects damage calculation.
 * Secondary effects (such as status conditions or stat changes) can be applied
 * when an attack is used.
 * 
 * @see PokemonModel
 * @see ISecondaryEffect
 * @see TypeModel
 */
public abstract class AttackModel {
  private final String name;
  private final TypeModel type;
  private final int power;
  private final ArrayList<ISecondaryEffect> secondaryEffects;

  /**
   * Creates a new AttackModel with the specified properties
   * 
   * @param name The attack name
   * @param type The attack type
   * @param power The attack power
   * @param secondaryEffects List of secondary effects that can be applied when 
   * the attack is used (can be empty)
   */
  public AttackModel( String name,
                      TypeModel type,
                      int power,
                      ArrayList<ISecondaryEffect> secondaryEffects) {

    this.name = name;
    this.type = type;
    this.power = power;

    // prevents NullPointerException exceptions
    this.secondaryEffects = secondaryEffects != null ?
                            new ArrayList<>(secondaryEffects) : 
                            new ArrayList<>();
    
  }

  /**
   * Applies an attack to the targetted Pokemon.
   * 
   * @param attacker The attacker Pokemon
   * @param target The Targetted Pokemon
   */
  public abstract void execute(PokemonModel attacker, PokemonModel target);

  /**
   * Determines if an attack is a special attack or not
   * @return true if the attack is a special attack, false otherwise.
   */
  public abstract boolean isSpecial();



  /**
   * Applies all secondary effects associated with this attack to the target Pokemon.
   * Iterates through each secondary effect in the collection and applies it to the specified
   * attacker and target Pokemon.
   *
   * @param attacker The Pokemon model that is performing the attack
   * @param target The Pokemon model that is receiving the attack and secondary effects
   */
  protected void applySecondaryEffects(PokemonModel attacker, PokemonModel target) {
    for (ISecondaryEffect effect : secondaryEffects) {
      if (effect.triggers()) {
        effect.apply(attacker, target);
      }
    }
  }


  // ┌──────────────────────────────────┐
  // | -------- GETTER METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Gets the name of this attack.
   *
   * @return The attack name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the type of this attack.
   *
   * @return The attack type
   */
  public TypeModel getType() {
    return this.type;
  }

  /**
   * Gets the power of this attack.
   *
   * @return The attack power
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Gets the list of secondary effects associated with this attack.
   *
   * @return A list of secondary effects
   */
  public List<ISecondaryEffect> getSecondaryEffects() {
    return this.secondaryEffects;
  }


  // ┌────────────────────────────────┐
  // | -------- MISC METHODS -------- |
  // └────────────────────────────────┘

  /**
   * Returns a string representation of this attack.
   * The string contains the attack name, type, category (Physical or Special),
   * power, and secondary effects (if any).
   *
   * @return A string representation of this attack
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append(" (")
      .append(type).append(", ")
      .append(isSpecial() ? "Special" : "Physical")
      .append(", Power: ").append(power);
    
    if (!secondaryEffects.isEmpty()) {
      sb.append(", Effects: ");
      for (int i = 0; i < secondaryEffects.size(); i++) {
        if (i > 0) sb.append(", ");
        sb.append(secondaryEffects.get(i).getClass().getSimpleName());
      }
    }
    
    sb.append(")");
    return sb.toString();
  }
}