package javamon.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import javamon.core.ISecondaryEffect;
import javamon.core.Type;
import javamon.core.attacks.Attack;
import javamon.core.attacks.PhysicalAttack;
import javamon.core.attacks.SpecialAttack;
import javamon.core.effects.ConfusionEffect;
import javamon.core.effects.DrainEffect;
import javamon.core.effects.RecoilEffect;
import javamon.core.effects.StatReduceEffect;
import javamon.core.statuses.StatusEffect;

/**
 * Utility class for loading Attack data from JSON files
 * and creating Attack instances.
 */
public class AttackLoader {
  private static final Logger LOGGER = Logger.getLogger(AttackLoader.class.getName());
  private static final String ATTACK_DATA_PATH = "/data/pokemon_data.json"; // Attacks are in pokemon_data.json
  
  /**
   * Loads all attack data from the JSON file.
   *
   * @param typesMap The map of type names to Type instances
   * @return A map of attack names to Attack instances
   */
  public static Map<String, Attack> loadAttacks(Map<String, Type> typesMap) {
    Map<String, Attack> attacksMap = new HashMap<>();
    
    try {
      // Collect all unique attacks from the pokemon JSON file
      collectAttacksFromPokemonData(attacksMap, typesMap);
      
      LOGGER.log(Level.INFO, "Successfully loaded {0} attacks", attacksMap.size());
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error loading attack data", e);
    }

    return attacksMap;
  }

  /**
   * Collects all unique attacks from the pokemon data file.
   *
   * @param attacksMap The map to populate with Attack instances
   * @param typesMap The map of type names to Type instances
   */
  private static void collectAttacksFromPokemonData(
      Map<String, Attack> attacksMap, 
      Map<String, Type> typesMap) {
    
    JsonArray pokemonArray = loadPokemonJsonArray();
    if (pokemonArray == null) {
      return;
    }

    // Iterate through all pokemon
    for (JsonValue pokemonValue : pokemonArray) {
      JsonObject pokemonObj = pokemonValue.asJsonObject();
      
      if (pokemonObj.containsKey("attacks")) {
        extractAttacksFromArray(pokemonObj.getJsonArray("attacks"), attacksMap, typesMap);
      }
    }
  }

  /**
   * Loads the JSON array containing pokemon data.
   *
   * @return A JsonArray containing pokemon data or null if loading fails
   */
  private static JsonArray loadPokemonJsonArray() {
    InputStream is = AttackLoader.class.getResourceAsStream(ATTACK_DATA_PATH);
    if (is == null) {
      LOGGER.log(Level.SEVERE, "Could not find pokemon_data.json file");
      return null;
    }

    try (JsonReader reader = Json.createReader(is)) {
      return reader.readArray();
    }
  }

  /**
   * Extracts attack data from an array of attack objects.
   */
  private static void extractAttacksFromArray(
      JsonArray attacksArray, 
      Map<String, Attack> attacksMap,
      Map<String, Type> typesMap) {
    
    for (JsonValue attackValue : attacksArray) {
      JsonObject attackObj = attackValue.asJsonObject();
      
      // Skip if attack doesn't have a name
      if (!attackObj.containsKey("name")) {
        continue;
      }
      
      String name = attackObj.getString("name");
      
      // Skip if we've already processed this attack
      if (attacksMap.containsKey(name)) {
        continue;
      }
      
      Attack attack = createAttackFromJson(attackObj, typesMap);
      if (attack != null) {
        attacksMap.put(name, attack);
      }
    }
  }

  /**
   * Creates an Attack instance from a JSON object.
   */
  private static Attack createAttackFromJson(
      JsonObject attackObj, 
      Map<String, Type> typesMap) {
    
    if (!hasRequiredAttackFields(attackObj)) {
      LOGGER.log(Level.WARNING, "Attack is missing required fields: {0}",
          attackObj.containsKey("name") ? attackObj.getString("name") : "unnamed");
      return null;
    }
    
    String name = attackObj.getString("name");
    String typeName = attackObj.getString("type");
    int power = attackObj.getInt("power");
    boolean isSpecial = attackObj.getBoolean("isSpecial");
    String description = attackObj.containsKey("description") ? 
        attackObj.getString("description") : "";
    
    Type type = typesMap.get(typeName);
    if (type == null) {
      LOGGER.log(Level.WARNING, "Type not found for attack: {0}", name);
      return null;
    }
    
    ArrayList<ISecondaryEffect> secondaryEffects = createSecondaryEffects(attackObj);
    
    // Create the appropriate attack subtype
    if (isSpecial) {
      return new SpecialAttack(name, type, power, secondaryEffects, description);
    } else {
      return new PhysicalAttack(name, type, power, secondaryEffects, description);
    }
  }

  /**
   * Checks if an attack JSON object has all required fields.
   */
  private static boolean hasRequiredAttackFields(JsonObject attackObj) {
    return attackObj.containsKey("name") &&
           attackObj.containsKey("type") &&
           attackObj.containsKey("power") &&
           attackObj.containsKey("isSpecial");
  }

  /**
   * Creates secondary effects from JSON data.
   */
  private static ArrayList<ISecondaryEffect> createSecondaryEffects(JsonObject attackObj) {
    ArrayList<ISecondaryEffect> effects = new ArrayList<>();
    
    if (!attackObj.containsKey("secondaryEffects")) {
      return effects;
    }
    
    JsonArray effectsArray = attackObj.getJsonArray("secondaryEffects");
    for (JsonValue effectValue : effectsArray) {
      JsonObject effectObj = effectValue.asJsonObject();
      
      if (!effectObj.containsKey("type")) {
        continue;
      }
      
      ISecondaryEffect effect = createEffectFromJson(effectObj);
      if (effect != null) {
        effects.add(effect);
      }
    }
    
    return effects;
  }

  /**
   * Creates a single secondary effect from JSON.
   */
  private static ISecondaryEffect createEffectFromJson(JsonObject effectObj) {
    String type = effectObj.getString("type");
    int chance = effectObj.containsKey("chance") ? effectObj.getInt("chance") : 100;
    
    switch (type) {
      case "StatusEffect":
        return new StatusEffect(effectObj.getString("status"), chance);
        
      case "RecoilEffect":
        return new RecoilEffect(effectObj.getInt("fraction"), chance);
        
      case "DrainEffect":
        return new DrainEffect(effectObj.getInt("fraction"), chance);
        
      case "StatReduceEffect":
        return new StatReduceEffect(
            effectObj.getString("stat"),
            effectObj.getInt("stages"),
            chance);
        
      case "ConfusionEffect":
        return new ConfusionEffect(
            chance,
            effectObj.getInt("minTurns", 2),
            effectObj.getInt("maxTurns", 5));
        
      default:
        LOGGER.log(Level.WARNING, "Unknown effect type: {0}", type);
        return null;
    }
  }

  /**
   * Gets an Attack by its name from the provided map.
   *
   * @param attacks The map of attack names to Attack instances
   * @param name The name of the attack to find
   * @return The Attack with the specified name, or null if not found
   */
  public static Attack getAttackByName(Map<String, Attack> attacks, String name) {
    return attacks.get(name);
  }
}