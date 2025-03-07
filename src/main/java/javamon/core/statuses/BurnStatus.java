package javamon.core.statuses;

import javamon.core.pokemon.Pokemon;

public class BurnStatus extends Status {
  public BurnStatus() {
    super("burn");
  }

  @Override
  public boolean canAttack() {
    return true;
  }
  
  @Override
  public void applyStartEffect(Pokemon pokemon) {
    pokemon.modifyAttack(-2);
  }
  
  @Override
  public void applyEndEffect(Pokemon pokemon) {
    int poisonDamage = pokemon.getMaxHp() / 16;
    pokemon.takeDamage(poisonDamage);
    System.out.println(pokemon.getName() + " is hurt by burn!");
  }
}
