package javamon.modele;

import java.util.HashMap;
import java.util.Map;

public class Type {
    private String nom;
    private Map<Type, Double> efficacite;

    public Type(Sring nom) {
        this.nom = nom;
        this.efficacite = new HashMap<>();
    }
}
