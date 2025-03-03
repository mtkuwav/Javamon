package javamon.modele;

import java.util.HashMap;
import java.util.Map;

public class Type {
    private String name;
    private Map<Type, Double> effectiveness;

    public Type(String name) {
        this.name = name;
        this.effectiveness = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addEffectiveness(Type type, double multiplier) {
        effectiveness.put(type, multiplier);
    }

    public double getMultiplierAgainst(Type type) {
        return effectiveness.getOrDefault(type, 1.0);
    }

    public double getMultiplierAgainst(Iterable<Type> types) {
        double multiplier = 1.0;
        for (Type type : types) {
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
        return name.equals(((Type) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
