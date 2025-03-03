package javamon.models;

public interface SecondaryEffectModel {
  void apply(PokemonModel source, PokemonModel target);

  boolean triggers();
  
  String getDescription();
}
