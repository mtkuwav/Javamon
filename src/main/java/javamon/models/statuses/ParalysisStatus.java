package javamon.models.statuses;

import javamon.models.PokemonModel;
import javamon.models.Status;

public class ParalysisStatus extends Status {
  public ParalysisStatus() {
    super("paralysis");
  }
  
  @Override
  public boolean canAttack() {
    return Math.random() >= 0.25;
  }
  
  @Override
  public void applyStartEffect(PokemonModel pokemon) {
    pokemon.modifySpeed(-2); // Assuming this applies a 0.5x modifier
  }
  
  @Override
  public void applyEndEffect(PokemonModel pokemon) {
    // No end-turn effect for paralysis
  }
}