package javamon.modele;

import java.util.HashMap;
import java.util.Map;

public class Type {
    private String nom;
    private Map<Type, Double> efficacite;

    public Type(String nom) {
        this.nom = nom;
        this.efficacite = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterEfficacite(Type type, double multiplicateur) {
        efficacite.put(type, multiplicateur);
    }

    public double getMultiplicateurContre(Type type) {
        return efficacite.getOrDefault(type, 1.0);
    }

    public double getMultiplicateurContre(Iterable<Type> types) {
        double multiplicateur = 1.0;
        for (Type type : types) {
            multiplicateur *= getMultiplicateurContre(type);
        }
        return multiplicateur;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return nom.equals(((Type) obj).nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
