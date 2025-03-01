package javamon.modele;

import java.util.ArrayList;
import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class pokemon {
  // integer pour rafraichir la valeur de hp dans javafx
  private IntegerProperty hp = new SimpleIntegerProperty();
  // propriétés du pokemon
  private String nom;
  private int hpMax;
  private int attaque;
  private int defense;
  private int attaqueSpecial;
  private int defenseSpecial;
  private int vitesse;
  // liste des types pour option de séléction
  private List<Type> types;
  private List<Attaque> attaques;
  private ObjetTenu objetTenu;
  private Status status;
  // image du pokemon arriere plan et font pour javafx
  private String imageFont;
  private String imageBack;

  // facteur de dégats pour les attaques et heal 
  private double facteurAttaque = 1.0;
  private double facteurDefense = 1.0;
  private double facteurAttaqueSpecial = 1.0;
  private double facteurDefenseSpecial = 1.0;
  private double facteurVitesse = 1.0;

  // constructeur avec les propriétés du pokemon
  public pokemon(String nom, int hpMax, int attaque, int defense, int attaqueSpecial, int defenseSpecial, int vitesse, List<Type> types, List<Attaque> attaques, ObjetTenu objetTenu, Status status, String imageFont, String imageBack) {
    this.nom = nom;
    this.hpMax = hpMax;
    this.hp.set(hpMax);
    this.attaque = attaque;
    this.defense = defense;
    this.attaqueSpecial = attaqueSpecial;
    this.defenseSpecial = defenseSpecial;
    this.vitesse = vitesse;
    this.types = new ArrayList<>(types);
    this.attaaques = new ArrayList<>();
  }
}
