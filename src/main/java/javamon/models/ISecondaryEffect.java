package javamon.models;


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
   * Applies the secondary effect to the targeted Pokemon
   *
   * @param source The Pokemon model that is performing the attack
   * @param target The Pokemon model that is receiving the attack and secondary effects
   */
  void apply(PokemonModel source, PokemonModel target);

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
