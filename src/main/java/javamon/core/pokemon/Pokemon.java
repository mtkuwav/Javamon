package javamon.core.pokemon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javamon.core.Type;
import javamon.core.attacks.Attack;
import javamon.core.statuses.Status;

// SOME LINES ARE COMMENTED FOR TESTING AND AVOIDING COMPILAITON ERRORS

public class Pokemon {
  // integer to refresh the hp value in javafx
  private final IntegerProperty hp = new SimpleIntegerProperty();
  // pokemfinal on properties
  private final String name;
  private final int maxHP;
  private final int attack;
  private final int defense;
  private final int specialAttack;
  private final int specialDefense;
  private final int speed;
  // list of types for selection option
  private final List<Type> types;
  private final List<Attack> attacks;


  // private final HeldObject heldObject;
  
  private Status status;
  // pokemon image background and font for javafx
  private String frontImage;
  private String backImage;

  // damage factor for attacks and healing
  private double attackFactor = 1.0;
  private double defenseFactor = 1.0;
  private double specialAttackFactor = 1.0;
  private double specialDefenseFactor = 1.0;
  private double speedFactor = 1.0;

  // constructor with pokemon properties
  public Pokemon(String name, int maxHP, int attack, int defense, int specialAttack, int specialDefense, int speed,
      List<Type> types) {
    this.name = name;
    this.maxHP = maxHP;
    this.hp.set(maxHP);
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
    return maxHP;
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

  public List<Attack> getAttacks() {
    return new ArrayList<>(attacks);
  }

  // public HeldObject getHeldObject() {
  //   return heldObject;
  // }

  // public void setHeldObject(HeldObject heldObject) {
  //   this.heldObject = heldObject;
  // }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
    if (status != null) {
      status.applyStartEffect(this);
    }
  }

  public String getFrontImage() {
    return frontImage;
  }

  public void setFrontImage(String frontImage) {
    this.frontImage = frontImage;
  }

  public String getBackImage() {
    return backImage;
  }

  public void setBackImage(String backImage) {
    this.backImage = backImage;
  }

  // method for attacking
  public void addAttack(Attack attack) {
    if (attacks.size() < 4) {
      attacks.add(attack);
    }
  }

  public void removeAttack(Attack attack) {
    attacks.remove(attack);
  }

  // method to check if pokemon is of type
  public boolean isOfType(Type type) {
    return types.contains(type);
  }

  // methods for battles
  public void takeDamage(int damage) {
    int newHp = Math.max(0, hp.get() - damage);
    hp.set(newHp);
  }

  public void heal(int amount) {
    int newHp = Math.min(maxHP, hp.get() + amount);
    hp.set(newHp);
  }

  public boolean isFainted() {
    return hp.get() <= 0;
  }

  // method for status
  public boolean canAttack() {
    if (status != null) {
      return status.canAttack();
    }
    return true;
  }

  // methods for stat modifications
  public void modifyAttack(int levels) {
    attackFactor = getFactorByLevel(attackFactor, levels);
  }

  public void modifyDefense(int levels) {
    defenseFactor = getFactorByLevel(defenseFactor, levels);
  }

  public void modifySpecialAttack(int levels) {
    specialAttackFactor = getFactorByLevel(specialAttackFactor, levels);
  }

  public void modifySpecialDefense(int levels) {
    specialDefenseFactor = getFactorByLevel(specialDefenseFactor, levels);
  }

  public void modifySpeed(int levels) {
    speedFactor = getFactorByLevel(speedFactor, levels);
  }

  // method for stat factors
  private double getFactorByLevel(double currentFactor, int levels) {
    if (levels > 0) {
      // Level increase (max +6)
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
      status.applyEndEffect(this);
    }

    // if (heldObject != null) {
    //   heldObject.endTurn(this);
    // }
  }

  // method for toString
  @Override
  public String toString() {
    return name;
  }
}
