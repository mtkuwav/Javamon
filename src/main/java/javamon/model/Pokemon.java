package javamon.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Pokemon {
  // integer to refresh the hp value in javafx
  private IntegerProperty hp = new SimpleIntegerProperty();
  // pokemon properties
  private String name;
  private int maxHp;
  private int attack;
  private int defense;
  private int specialAttack;
  private int specialDefense;
  private int speed;
  // list of types for selection option
  private List<Type> types;
  private List<Attaque> attacks;
  private ObjetTenu heldItem;
  private Statut status;
  // pokemon image background and front for javafx
  private String imageFront;
  private String imageBack;

  // damage factors for attacks and heal
  private double attackFactor = 1.0;
  private double defenseFactor = 1.0;
  private double specialAttackFactor = 1.0;
  private double specialDefenseFactor = 1.0;
  private double speedFactor = 1.0;

  // constructor with pokemon properties
  public Pokemon(String name, int maxHp, int attack, int defense, int specialAttack, int specialDefense, int speed,
      List<Type> types) {
    this.name = name;
    this.maxHp = maxHp;
    this.hp.set(maxHp);
    this.attack = attack;
    this.defense = defense;
    this.specialAttack = specialAttack;
    this.specialDefense = specialDefense;
    this.speed = speed;
    this.types = new ArrayList<>(types);
    this.attacks = new ArrayList<>();
  }

  // Getters and setters
  public String getName() {
    return name;
  }

  public int getHp() {
    return hp.get();
  }

  public IntegerProperty hpProperty() {
    return hp;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public int getAttack() {
    return (int) (attack * attackFactor);
  }

  public int getDefense() {
    return (int) (defense * defenseFactor);
  }

  public int getSpecialAttack() {
    return (int) (specialAttack * specialAttackFactor);
  }

  public int getSpecialDefense() {
    return (int) (specialDefense * specialDefenseFactor);
  }

  public int getSpeed() {
    return (int) (speed * speedFactor);
  }

  public List<Type> getTypes() {
    return new ArrayList<>(types);
  }

  public List<Attaque> getAttacks() {
    return new ArrayList<>(attacks);
  }

  public ObjetTenu getHeldItem() {
    return heldItem;
  }

  public void setHeldItem(ObjetTenu heldItem) {
    this.heldItem = heldItem;
  }

  public Statut getStatus() {
    return status;
  }

  public void setStatus(Statut status) {
    this.status = status;
    if (status != null) {
      status.applyStartEffect(this);
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

  // method to attack
  public void addAttack(Attaque attack) {
    if (attacks.size() < 4) {
      attacks.add(attack);
    }
  }

  public void removeAttack(Attaque attack) {
    attacks.remove(attack);
  }

  // method to know if the pokemon is of type
  public boolean isOfType(Type type) {
    return types.contains(type);
  }

  // method for combats
  public void takeDamage(int damage) {
    int newHp = Math.max(0, hp.get() - damage);
    hp.set(newHp);
  }

  public void heal(int amount) {
    int newHp = Math.min(maxHp, hp.get() + amount);
  }

  public boolean isKo() {
    return hp.get() <= 0;
  }

  // method for status
  public boolean canAttack() {
    if (status != null) {
      return status.canAttack();
    }
    return true;
  }

  // method for stats modifications
  public void modifyAttack(int levels) {
    attackFactor = getFactorFromLevel(attackFactor, levels);
  }

  public void modifyDefense(int levels) {
    defenseFactor = getFactorFromLevel(defenseFactor, levels);
  }

  public void modifySpecialAttack(int levels) {
    specialAttackFactor = getFactorFromLevel(specialAttackFactor, levels);
  }

  public void modifySpecialDefense(int levels) {

  }

  public void modifySpeed(int levels) {
    speedFactor = getFactorFromLevel(speedFactor, levels);
  }

  // method for stats factors
  private double getFactorFromLevel(double currentFactor, int levels) {
    if (levels > 0) {
      // Level up (max +6)
      for (int i = 0; i < levels && i < 6; i++) {
        switch (i) {
          case 0:
            currentFactor = 1.5;
            break;
          case 1:
            currentFactor = 2.0;
            break;
          case 2:
            currentFactor = 2.5;
            break;
          case 3:
            currentFactor = 3.0;
            break;
          case 4:
            currentFactor = 3.5;
            break;
          case 5:
            currentFactor = 4.0;
            break;
        }
      }
    } else if (levels < 0) {
      for (int i = 0; i > levels && i > -6; i--) {
        switch (i) {
          case -1:
            currentFactor = 0.67;
            break;
          case -2:
            currentFactor = 0.5;
            break;
          case -3:
            currentFactor = 0.4;
            break;
          case -4:
            currentFactor = 0.33;
            break;
          case -5:
            currentFactor = 0.29;
            break;
          case -6:
            currentFactor = 0.25;
            break;
        }
      }
    }
    return currentFactor;
  }

  // method for end of turn effects
  public void applyEndTurnEffects() {
    if (status != null) {
      status.applyEndTurnEffect(this);
    }

    if (heldItem != null) {
      heldItem.endTurn(this);
    }
  }

  // method for toString
  @Override
  public String toString() {
    return name;
  }
}
