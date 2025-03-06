package javamon.models.statuses;

import javamon.models.PokemonModel;
import javamon.models.Status;

public class BurnStatus extends Status {
  public BurnStatus() {
    super("burn");
  }

  @Override
  public boolean canAttack() {
    return true;
  }
  
  @Override
  public void applyStartEffect(PokemonModel pokemon) {
    pokemon.modifyAttack(-2);
  }
  
  @Override
  public void applyEndEffect(PokemonModel pokemon) {
    int poisonDamage = pokemon.getMaxHp() / 16;
    pokemon.takeDamage(poisonDamage);
    System.out.println(pokemon.getName() + " is hurt by burn!");
  }
}
