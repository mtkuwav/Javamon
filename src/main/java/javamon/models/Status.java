package javamon.models;

import javamon.models.statuses.BurnStatus;
import javamon.models.statuses.FreezeStatus;
import javamon.models.statuses.ParalysisStatus;
import javamon.models.statuses.PoisonStatus;

/**
 * Represents a status condition that can affect a Pokémon.
 * Uses the Strategy pattern to handle different status behaviors.
 */
public abstract class Status {
  private final String name;

  protected Status(String name) {
    this.name = name;
  }

  /**
   * Gets the name of this status.
   */
  public String getName() {
    return name;
  }

  /**
   * Determines if the Pokémon can attack with this status.
   * @return true if the Pokémon can attack, false otherwise
   */
  public abstract boolean canAttack();

  /**
   * Applies effects at the start when status is first applied.
   * @param pokemon The Pokémon affected by the status
   */
  public abstract void applyStartEffect(PokemonModel pokemon);
  
  /**
   * Applies end-of-turn effects like damage or stat changes.
   * @param pokemon The Pokémon affected by the status
   */
  public abstract void applyEndEffect(PokemonModel pokemon);

  /**
   * Determines if this status should be removed after the current turn.
   * Used for temporary statuses or those with a chance to fade each turn.
   * 
   * @return true if the status should be removed, false otherwise
   */
  public boolean shouldRemoveAfterTurn() {
    return false;
  }

  /**
   * Creates a Status instance based on the status name.
   * 
   * @param statusName Name of the status to create
   * @return A Status instance or null if not recognized
   */
  public static Status createFromName(String statusName) {
    switch (statusName.toLowerCase()) {
      case "paralysis": return new ParalysisStatus();
      case "poison": return new PoisonStatus();
      case "burn": return new BurnStatus();
      case "freeze": return new FreezeStatus();
      default: return null;
    }
  }
}