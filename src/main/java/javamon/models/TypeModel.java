package javamon.models;

import java.util.HashMap;
import java.util.Map;

public class TypeModel {
    private final String name;
    private final Map<TypeModel, Double> effectiveness;

    public TypeModel(String name) {
        this.name = name;
        this.effectiveness = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addEffectiveness(TypeModel type, double multiplier) {
        effectiveness.put(type, multiplier);
    }

    public double getMultiplierAgainst(TypeModel type) {
        return effectiveness.getOrDefault(type, 1.0);
    }

    public double getMultiplierAgainst(Iterable<TypeModel> types) {
        double multiplier = 1.0;
        for (TypeModel type : types) {
            multiplier *= getMultiplierAgainst(type);
        }
        return multiplier;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return name.equals(((TypeModel) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
