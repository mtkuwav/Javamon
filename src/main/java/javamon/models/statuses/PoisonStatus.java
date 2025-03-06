package javamon.models.statuses;

import javamon.models.PokemonModel;
import javamon.models.Status;

public class PoisonStatus extends Status {
  public PoisonStatus() {
    super("poison");
  }

  @Override
  public boolean canAttack() {
    return true;
  }
  
  @Override
  public void applyStartEffect(PokemonModel pokemon) {
    // no immediate effect
  }
  
  @Override
  public void applyEndEffect(PokemonModel pokemon) {
    int poisonDamage = pokemon.getMaxHp() / 8;
    pokemon.takeDamage(poisonDamage);
    System.out.println(pokemon.getName() + " is hurt by poison!");
  }
}
