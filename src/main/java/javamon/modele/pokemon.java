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
  private Statut statut;
  // image du pokemon arriere plan et font pour javafx
  private String imageFront;
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

  // Determination des getters et setters
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

  // methode pour attaquer

  public void ajouterAttaque(Attaque attaque) {
    if (attaques.size() < 4) {
      attaques.add(attaque);
    }
  }

  public void retirerAttaque(Attaque attaque) {
    attaques.remove(attaque);
  }

  // methode pour savoir si le pokemon est de type
  public boolean estDeType(Type type) {
    return types.contains(type);
  }

  // methode pour les combats
  public void subirDegats(int degats) {
    int nouveauHp = Math.max(0, hp.get() - degats);
    hp.set(nouveauHp);
  }

  public void soigner(int montant) {
    int nouveauHp = Math.min(hpMax, hp.get() + montant);
  }

  public boolean estKo() {
    return hp.get() <= 0;
  }

  // methode pour status
  public boolean peutAttaquer() {
    if (statut != null) {
      return statut.peutAttaquer();
    }
    return true;
  }

  // methode pour les modifications de stats
  public void modifierAttaque(int niveaux) {
    facteurAttaque = getFacteurSelonNiveau(facteurAttaque, niveaux);
  }

  public void modifierDefense(int niveaux) {
    facteurDefense = getFacteurSelonNiveau(facteurDefense, niveaux);
  }

  public void modifierAttaqueSpeciale(int niveaux) {
    facteurAttaqueSpecial = getFacteurSelonNiveau(facteurAttaqueSpecial, niveaux);
  }

  public void modifierDefenseSpeciale(int niveaux) {

  }

  public void modifierVitesse(int niveaux) {
    facteurVitesse = getFacteurSelonNiveau(facteurVitesse, niveaux);
  }

  // methode pour les facteurs de stats
  private double getFacteurSelonNiveau(double facteurActuel, int niveaux) {
    if (niveaux > 0) {
      // Augmentation de niveau (max +6)
      for (int i = 0; i < niveaux && i < 6; i++) {
        switch (i) {
          case 0:
            facteurActuel = 1.5;
            break;
          case 1:
            facteurActuel = 2.0;
            break;
          case 2:
            facteurActuel = 2.5;
            break;
          case 3:
            facteurActuel = 3.0;
            break;
          case 4:
            facteurActuel = 3.5;
            break;
          case 5:
            facteurActuel = 4.0;
            break;
        }
      }
    } else if (niveaux < 0) {
      for (int i = 0; i > niveaux && i > -6; i--) {
        switch (i) {
          case -1:
            facteurActuel = 0.67;
            break;
          case -2:
            facteurActuel = 0.5;
            break;
          case -3:
            facteurActuel = 0.4;
            break;
          case -4:
            facteurActuel = 0.33;
            break;
          case -5:
            facteurActuel = 0.29;
            break;
          case -6:
            facteurActuel = 0.25;
            break;
        }
      }
    }
    return facteurActuel;
  }

  // methode pour les effets fin de tour
  public void appliquerEffetsFinTour() {
    if (statut != null) {
      statut.appliquerEffetFin(this);
    }

    if (objetTenu != null) {
      objetTenu.finTour(this);
    }
  }

  // methode pour les toString
  @Override
  public String toString() {
    return nom;
  }
}
