package javamon.core.statuses;

import javamon.core.pokemon.Pokemon;

public class ParalysisStatus extends Status {
  public ParalysisStatus() {
    super("paralysis");
  }
  
  @Override
  public boolean canAttack() {
    return Math.random() >= 0.25;
  }
  
  @Override
  public void applyStartEffect(Pokemon pokemon) {
    pokemon.modifySpeed(-2); // Assuming this applies a 0.5x modifier
  }
  
  @Override
  public void applyEndEffect(Pokemon pokemon) {
    // No end-turn effect for paralysis
  }
}