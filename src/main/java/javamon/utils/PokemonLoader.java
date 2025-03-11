package javamon.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import javamon.core.Type;
import javamon.core.attacks.Attack;
import javamon.core.pokemon.Pokemon;

/**
 * Utility class for loading Pokemon data from JSON files
 * and creating Pokemon instances.
 */
public class PokemonLoader {
  private static final Logger LOGGER = Logger.getLogger(PokemonLoader.class.getName());
  private static final String POKEMON_DATA_PATH = "/data/pokemon_data.json";
  
  /**
   * Loads all Pokemon data from the JSON file.
   *
   * @param typesMap The map of type names to Type instances
   * @param attacksMap The map of attack names to Attack instances
   * @return A map of Pokemon names to Pokemon instances
   */
  public static Map<String, Pokemon> loadPokemons(
      Map<String, Type> typesMap, 
      Map<String, Attack> attacksMap) {
    Map<String, Pokemon> pokemonMap = new HashMap<>();
    
    try {
      JsonArray pokemonArray = loadPokemonJsonArray();
      if (pokemonArray == null) {
        return pokemonMap;
      }

      createPokemonInstances(pokemonArray, typesMap, attacksMap, pokemonMap);
      
      LOGGER.log(Level.INFO, "Successfully loaded {0} Pokemon", pokemonMap.size());
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error loading Pokemon data", e);
    }

    return pokemonMap;
  }

  /**
   * Loads the JSON array containing Pokemon data.
   *
   * @return A JsonArray containing Pokemon data or null if loading fails
   */
  private static JsonArray loadPokemonJsonArray() {
    InputStream is = PokemonLoader.class.getResourceAsStream(POKEMON_DATA_PATH);
    if (is == null) {
      LOGGER.log(Level.SEVERE, "Could not find pokemon_data.json file");
      return null;
    }

    try (JsonReader reader = Json.createReader(is)) {
      return reader.readArray();
    }
  }

  /**
   * Creates Pokemon instances from JSON data and adds them to the map.
   */
  private static void createPokemonInstances(
      JsonArray pokemonArray, 
      Map<String, Type> typesMap,
      Map<String, Attack> attacksMap,
      Map<String, Pokemon> pokemonMap) {
    for (JsonValue pokemonValue : pokemonArray) {
      JsonObject pokemonObj = pokemonValue.asJsonObject();
      if (!pokemonObj.containsKey("name")) continue;
      
      String name = pokemonObj.getString("name");
      Pokemon pokemon = createPokemonFromJson(pokemonObj, typesMap);
      
      if (pokemon != null) {
        addAttacksToPokemon(pokemon, pokemonObj, attacksMap);
        setImagesForPokemon(pokemon, pokemonObj);
        pokemonMap.put(name, pokemon);
      }
    }
  }

  /**
   * Creates a Pokemon instance from a JSON object.
   */
  private static Pokemon createPokemonFromJson(
      JsonObject pokemonObj, 
      Map<String, Type> typesMap) {
    if (!hasRequiredStats(pokemonObj)) {
      LOGGER.log(Level.WARNING, "Pokemon is missing required stats");
      return null;
    }
    
    String name = pokemonObj.getString("name");
    int hp = pokemonObj.getInt("hp");
    int attack = pokemonObj.getInt("attack");
    int defense = pokemonObj.getInt("defense");
    int specialAttack = pokemonObj.getInt("special_attack");
    int specialDefense = pokemonObj.getInt("special_defense");
    int speed = pokemonObj.getInt("speed");
    
    List<Type> types = getPokemonTypes(pokemonObj, typesMap);
    
    return new Pokemon(name, hp, attack, defense, specialAttack, specialDefense, speed, types);
  }

  /**
   * Checks if the Pokemon JSON object has all the required stats.
   */
  private static boolean hasRequiredStats(JsonObject pokemonObj) {
    return pokemonObj.containsKey("hp") &&
           pokemonObj.containsKey("attack") &&
           pokemonObj.containsKey("defense") &&
           pokemonObj.containsKey("special_attack") &&
           pokemonObj.containsKey("special_defense") &&
           pokemonObj.containsKey("speed");
  }

  /**
   * Gets the list of Types for a Pokemon from its JSON object.
   */
  private static List<Type> getPokemonTypes(
      JsonObject pokemonObj, 
      Map<String, Type> typesMap) {
    List<Type> types = new ArrayList<>();
    
    if (pokemonObj.containsKey("types")) {
      JsonArray typesArray = pokemonObj.getJsonArray("types");
      for (JsonValue typeValue : typesArray) {
        String typeName = typeValue.toString().replaceAll("\"", "");
        Type type = typesMap.get(typeName);
        if (type != null) {
          types.add(type);
        }
      }
    }
    
    return types;
  }

  /**
   * Adds attacks to a Pokemon from its JSON object.
   */
  private static void addAttacksToPokemon(
      Pokemon pokemon, 
      JsonObject pokemonObj,
      Map<String, Attack> attacksMap) {
    if (!pokemonObj.containsKey("attacks")) {
      return;
    }
    
    JsonArray attacksArray = pokemonObj.getJsonArray("attacks");
    for (JsonValue attackValue : attacksArray) {
      JsonObject attackObj = attackValue.asJsonObject();
      if (attackObj.containsKey("name")) {
        String attackName = attackObj.getString("name");
        Attack attack = attacksMap.get(attackName);
        if (attack != null) {
          pokemon.addAttack(attack);
        }
      }
    }
  }

  /**
   * Sets the image paths for a Pokemon from its JSON object.
   */
  private static void setImagesForPokemon(Pokemon pokemon, JsonObject pokemonObj) {
    if (!pokemonObj.containsKey("images")) {
      return;
    }
    
    JsonObject imagesObj = pokemonObj.getJsonObject("images");
    if (imagesObj.containsKey("front")) {
      pokemon.setFrontImage(imagesObj.getString("front"));
    }
    
    if (imagesObj.containsKey("back")) {
      pokemon.setBackImage(imagesObj.getString("back"));
    }
  }

  /**
   * Gets a Pokemon by its name from the provided map.
   *
   * @param pokemons The map of Pokemon names to Pokemon instances
   * @param name The name of the Pokemon to find
   * @return The Pokemon with the specified name, or null if not found
   */
  public static Pokemon getPokemonByName(Map<String, Pokemon> pokemons, String name) {
    return pokemons.get(name);
  }
}