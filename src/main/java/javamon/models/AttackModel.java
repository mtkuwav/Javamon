package javamon.models;

import java.util.ArrayList;
import java.util.List;

public abstract class AttackModel {
  private final String name;
  private final TypeModel type;
  private final int power;
  private final List<SecondaryEffectModel> secondaryEffects;

  public AttackModel(String name, TypeModel type, int power, List<SecondaryEffectModel> secondaryEffects) {
    this.name = name;
    this.type = type;
    this.power = power;
    // prevents NullPointerException exceptions
    this.secondaryEffects = secondaryEffects != null ?
                            new ArrayList<>(secondaryEffects) : 
                            new ArrayList<>();
    
  }

  public abstract void execute(PokemonModel attacker, PokemonModel target);
  public abstract boolean isPhysical();

  protected void applySecondaryEffects(PokemonModel attacker, PokemonModel target) {
    for (SecondaryEffectModel effect : secondaryEffects) {
      effect.apply(attacker, target);
    }
  }

  public String getName() {
    return name;
  }

  public TypeModel getType() {
    return type;
  }

  public int getPower() {
    return power;
  }

  @Override
  public String toString() {
      return name;
  }
}