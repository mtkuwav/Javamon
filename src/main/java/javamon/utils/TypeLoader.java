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
  
  /**
   * Loads all type data from the JSON file and creates a map of type models.
   *
   * @return A map of type names to TypeModel instances
   */
  public static Map<String, Type> loadTypes() {
    Map<String, Type> typeModels = new HashMap<>();
    
    try {
      InputStream is = TypeLoader.class.getResourceAsStream("/data/types_data.json");
      if (is == null) {
        LOGGER.log(Level.SEVERE, "Could not find types_data.json file");
        return typeModels;
      }

      JsonArray typesArray;
      try (JsonReader reader = Json.createReader(is)) {
        typesArray = reader.readArray();
      }

      for (JsonValue typeValue : typesArray) {
        JsonObject typeObj = typeValue.asJsonObject();
        String typeName = typeObj.getString("name");
        typeModels.put(typeName, new Type(typeName));
      }

      for (JsonValue typeValue : typesArray) {
        JsonObject typeObj = typeValue.asJsonObject();
        String typeName = typeObj.getString("name");
        Type typeModel = typeModels.get(typeName);

        if (typeObj.containsKey("effectiveness")) {
          JsonObject effectivenessObj = typeObj.getJsonObject("effectiveness");

          for (String targetTypeName : effectivenessObj.keySet()) {
            double multiplier = effectivenessObj.getJsonNumber(targetTypeName).doubleValue();
            Type targetType = typeModels.get(targetTypeName);
            
            if (targetType != null) {
              typeModel.addEffectiveness(targetType, multiplier);
            }
          }
        }
      }

      LOGGER.log(Level.INFO, "Successfully loaded {0} types", typeModels.size());

    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error loading type data", e);
    }

    return typeModels;
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