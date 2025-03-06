package javamon.core.statuses;

import javamon.core.pokemon.Pokemon;

/**
 * Represents the Freeze status condition.
 * A frozen Pokémon cannot attack unless it thaws.
 * Each turn, there is a 20% chance that the Pokémon will thaw.
 */
public class FreezeStatus extends Status {
  public FreezeStatus() {
    super("freeze");
  }

  @Override
  public boolean canAttack() {
    // Check if the Pokémon thaws this turn (20% chance)
    boolean thawedThisTurn = Math.random() < 0.2;
    
    if (thawedThisTurn) {
      // Thawing means the status is removed
      System.out.println("The ice melted!");
      return true; // Can attack this turn after thawing
    }
    
    System.out.println("The Pokémon is frozen solid!");
    return false; // Cannot attack while frozen
  }
  
  @Override
  public void applyStartEffect(Pokemon pokemon) {
    // No immediate effect when frozen is applied
  }
  
  @Override
  public void applyEndEffect(Pokemon pokemon) {
    // No end-of-turn damage or other effects
  }
  
  /**
   * Override this method to handle thawing properly.
   * This will be called by the battle system when canAttack returns true.
   */
  @Override
  public boolean shouldRemoveAfterTurn() {
    return Math.random() < 0.2; // 20% chance to thaw each turn
  }
}