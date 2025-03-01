package javamon.modele;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Pokemon {
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
  private statut statut;
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
  public Pokemon(String nom, int hpMax, int attaque, int defense, int attaqueSpeciale, int defenseSpeciale, int vitesse,
      List<Type> types) {
    this.nom = nom;
    this.hpMax = hpMax;
    this.hp.set(hpMax);
    this.attaque = attaque;
    this.defense = defense;
    this.attaqueSpecial = attaqueSpeciale;
    this.defenseSpecial = defenseSpeciale;
    this.vitesse = vitesse;
    this.types = new ArrayList<>(types);
    this.attaques = new ArrayList<>();
  }

  public String getNom() {
    return nom;
  }

  public int getHp() {
    return hp.get();
  }

  public IntegerProperty hpProperty() {
    return hp;
  }

  public int getHpMax() {
    return hpMax;
  }

  public int getAttaque() {
    return (int) (attaque * facteurAttaque);
  }

  public int getDefense() {
    return (int) (defense * facteurDefense);
  }

  public int getAttaqueSpeciale() {
    return (int) (attaqueSpecial * facteurAttaqueSpecial);
  }

  public int getDefenseSpeciale() {
    return (int) (defenseSpecial * facteurDefenseSpecial);
  }

  public int getVitesse() {
    return (int) (vitesse * facteurVitesse);
  }

  public List<Type> getTypes() {
    return new ArrayList<>(types);
  }

  public List<Attaque> getAttaques() {
    return new ArrayList<>(attaques);
  }

  public ObjetTenu getObjetTenu() {
    return objetTenu;
  }

  public void setObjetTenu(ObjetTenu objetTenu) {
    this.objetTenu = objetTenu;
  }

  public Statut getStatut() {
    return statut;
  }

  public void setStatut(Statut statut) {
    this.statut = statut;
    if (statut != null) {
      statut.appliquerEffetDebut(this);
    }
  }

  public String getImageFront() {
    return imageFront;
  }

  public void setImageFront(String imageFront) {
    this.imageFront = imageFront;
  }

  public String getImageBack() {
    return imageBack;
  }

  public void setImageBack(String imageBack) {
    this.imageBack = imageBack;
  }

}
