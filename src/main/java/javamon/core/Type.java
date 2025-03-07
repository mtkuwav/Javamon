package javamon.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Pokémon type and manages type effectiveness calculations.
 * Each type has a name and effectiveness multipliers against other types.
 */
public class Type {
  private final String name;
  private final Map<Type, Double> effectiveness;

  /**
   * Creates a new type with the specified name.
   *
   * @param name The name of the type
   */
  public Type(String name) {
    this.name = name;
    this.effectiveness = new HashMap<>();
  }

  // ┌─────────────────────────────────────────┐
  // | -------- EFFECTIVENESS METHODS -------- |
  // └─────────────────────────────────────────┘

  /**
   * Adds an effectiveness multiplier against another type.
   * This defines how effective this type is when attacking the specified type.
   *
   * @param type The defending type
   * @param multiplier The effectiveness multiplier (e.g., 0.5 for not very effective, 2.0 for super effective)
   */
  public void addEffectiveness(Type type, double multiplier) {
    effectiveness.put(type, multiplier);
  }

  /**
   * Gets the effectiveness multiplier of this type against another single type.
   *
   * @param type The defending type
   * @return The effectiveness multiplier (defaults to 1.0 if not specifically defined)
   */
  public double getMultiplierAgainst(Type type) {
      return effectiveness.getOrDefault(type, 1.0);
  }

  /**
   * Gets the combined effectiveness multiplier of this type against multiple types.
   * The multipliers are combined by multiplication.
   *
   * @param types An iterable collection of defending types
   * @return The combined effectiveness multiplier
   */
  public double getMultiplierAgainst(Iterable<Type> types) {
    double multiplier = 1.0;
    for (Type type : types) {
      multiplier *= getMultiplierAgainst(type);
    }
    return multiplier;
  }

  // ┌──────────────────────────────────┐
  // | -------- GETTER METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Gets the name of this type.
   *
   * @return The name of the type
   */
  public String getName() {
    return name;
  }

  // ┌────────────────────────────────┐
  // | -------- MISC METHODS -------- |
  // └────────────────────────────────┘

  /**
   * Returns the string representation of this type, which is its name.
   *
   * @return The name of the type
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * Checks if this type is equal to another object.
   * Two types are considered equal if they have the same name.
   *
   * @param obj The object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    return name.equals(((Type) obj).name);
  }

  /**
   * Returns the hash code for this type, based on its name.
   *
   * @return The hash code value
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
