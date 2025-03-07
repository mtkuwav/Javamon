package javamon.core;

import javamon.core.pokemon.Pokemon;

/**
 * Represents a secondary effect that can be applied by attacks in the game.
 * Secondary effects are additional consequences that may occur when a Pokemon uses an attack,
 * such as status conditions, stat changes, or other effects.
 * 
 * Implementations of this interface define specific secondary effects
 * like burning, paralyzing, lowering stats, etc.
 * 
 * These effects may have a chance-based trigger and apply different
 * consequences to the target Pokemon when activated.
 */
public interface ISecondaryEffect {

  /**
   * Applies this effect without damage information
   */
  void apply(Pokemon source, Pokemon target);
  
  /**
   * Applies this effect with damage information
   * @param source The Pokémon using the attack
   * @param target The Pokémon receiving the attack
   * @param damageDealt The actual damage dealt by the attack
   */
  default void apply(Pokemon source, Pokemon target, int damageDealt) {
      // Par défaut, on ignore les dégâts et on utilise l'implémentation simple
      apply(source, target);
  }

  /**
   * Determines if the secondary effect triggers in the current situation.
   * Some secondary effects have a chance to occur (e.g.: 30% chance to burn).
   * 
   * @return true if the effect triggers, false otherwise
   */
  boolean triggers();
  
  /**
   * Returns a textual description of the secondary effect.
   * This description can be used in the user interface to
   * inform the player of the applied effect.
   * 
   * @return The description of the secondary effect
   */
  String getDescription();
}
