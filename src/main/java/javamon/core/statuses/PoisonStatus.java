package javamon.core.statuses;

import javamon.core.pokemon.Pokemon;

public class PoisonStatus extends Status {
  public PoisonStatus() {
    super("poison");
  }

  @Override
  public boolean canAttack() {
    return true;
  }
  
  @Override
  public void applyStartEffect(Pokemon pokemon) {
    // no immediate effect
  }
  
  @Override
  public void applyEndEffect(Pokemon pokemon) {
    int poisonDamage = pokemon.getMaxHp() / 8;
    pokemon.takeDamage(poisonDamage);
    System.out.println(pokemon.getName() + " is hurt by poison!");
  }
}
