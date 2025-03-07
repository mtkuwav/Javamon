package javamon.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import javamon.core.Type;

/**
 * Utility class for loading Pokemon type data from JSON files
 * and creating TypeModel instances with their effectiveness relationships.
 */
public class TypeLoader {
  private static final Logger LOGGER = Logger.getLogger(TypeLoader.class.getName());
  private static final String TYPE_DATA_PATH = "/data/types_data.json";
  
  /**
   * Loads all type data from the JSON file and creates a map of type models.
   *
   * @return A map of type names to TypeModel instances
   */
  public static Map<String, Type> loadTypes() {
    Map<String, Type> typeModels = new HashMap<>();
    
    try {
      JsonArray typesArray = loadTypesJsonArray();
      if (typesArray == null) {
        return typeModels;
      }

      createTypeInstances(typesArray, typeModels);
      setupTypeEffectiveness(typesArray, typeModels);
      
      LOGGER.log(Level.INFO, "Successfully loaded {0} types", typeModels.size());
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error loading type data", e);
    }

    return typeModels;
  }

  /**
   * Loads the JSON array containing type data.
   *
   * @return A JsonArray containing type data or null if loading fails
   */
  private static JsonArray loadTypesJsonArray() {
    InputStream is = TypeLoader.class.getResourceAsStream(TYPE_DATA_PATH);
    if (is == null) {
      LOGGER.log(Level.SEVERE, "Could not find types_data.json file");
      return null;
    }

    try (JsonReader reader = Json.createReader(is)) {
      return reader.readArray();
    }
  }

  /**
   * Creates Type instances from JSON data and adds them to the map.
   *
   * @param typesArray The JSON array containing type data
   * @param typeModels The map to populate with Type instances
   */
  private static void createTypeInstances(
      JsonArray typesArray, Map<String, Type> typeModels) {
    for (JsonValue typeValue : typesArray) {
      JsonObject typeObj = typeValue.asJsonObject();
      String typeName = typeObj.getString("name");
      typeModels.put(typeName, new Type(typeName));
    }
  }

  /**
   * Sets up effectiveness relationships between types.
   *
   * @param typesArray The JSON array containing type data
   * @param typeModels The map containing Type instances
   */
  private static void setupTypeEffectiveness(
      JsonArray typesArray, Map<String, Type> typeModels) {
    for (JsonValue typeValue : typesArray) {
      JsonObject typeObj = typeValue.asJsonObject();
      String typeName = typeObj.getString("name");
      Type typeModel = typeModels.get(typeName);

      if (typeObj.containsKey("effectiveness")) {
        addEffectivenessRelationships(
            typeObj.getJsonObject("effectiveness"), 
            typeModel, 
            typeModels
        );
      }
    }
  }

  /**
   * Adds effectiveness relationships for a specific type.
   *
   * @param effectivenessObj The JSON object containing effectiveness data
   * @param typeModel The Type to add relationships to
   * @param typeModels The map containing all Type instances
   */
  private static void addEffectivenessRelationships(
      JsonObject effectivenessObj, 
      Type typeModel, 
      Map<String, Type> typeModels) {
    for (String targetTypeName : effectivenessObj.keySet()) {
      double multiplier = effectivenessObj.getJsonNumber(targetTypeName).doubleValue();
      Type targetType = typeModels.get(targetTypeName);
      
      if (targetType != null) {
        typeModel.addEffectiveness(targetType, multiplier);
      }
    }
  }

  /**
   * Gets a TypeModel by its name from the provided map.
   *
   * @param types The map of type names to TypeModel instances
   * @param name The name of the type to find
   * @return The TypeModel with the specified name, or null if not found
   */
  public static Type getTypeByName(Map<String, Type> types, String name) {
    return types.get(name);
  }
}