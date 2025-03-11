package javamon.core.pokemon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javamon.core.Type;
import javamon.core.attacks.Attack;
import javamon.core.statuses.Status;

/**
 * Represents a Pokemon with all its attributes and battle capabilities.
 * This class manages a Pokemon's stats, types, attacks, status conditions, and battle mechanics.
 */
public class Pokemon {
  // ┌───────────────────────────────────┐
  // | -------- BASE ATTRIBUTES -------- |
  // └───────────────────────────────────┘
  
  /** Integer property to enable JavaFX UI updates when HP changes */
  private final IntegerProperty hp = new SimpleIntegerProperty();
  
  /** Core Pokemon attributes */
  private final String name;
  private final int maxHP;
  private final int attack;
  private final int defense;
  private final int specialAttack;
  private final int specialDefense;
  private final int speed;
  
  /** Pokemon type and attacks */
  private final List<Type> types;
  private final List<Attack> attacks;

  // private final HeldObject heldObject;
  
  /** Current status condition */
  private Status status;
  
  /** Pokemon sprite images */
  private String frontImage;
  private String backImage;

  // ┌──────────────────────────────────┐
  // | -------- STAT MODIFIERS -------- |
  // └──────────────────────────────────┘
  
  /** Stat modification factors */
  private double attackFactor = 1.0;
  private double defenseFactor = 1.0;
  private double specialAttackFactor = 1.0;
  private double specialDefenseFactor = 1.0;
  private double speedFactor = 1.0;

  // ┌─────────────────────────────────────┐
  // | -------- STATUS CONDITIONS -------- |
  // └─────────────────────────────────────┘
  
  /** Confusion status tracking */
  private boolean confused = false;
  private int confusionCounter = 0;

  /**
   * Creates a new Pokemon with the specified attributes.
   *
   * @param name The Pokemon's name
   * @param maxHP The maximum hit points
   * @param attack The base attack stat
   * @param defense The base defense stat
   * @param specialAttack The base special attack stat
   * @param specialDefense The base special defense stat
   * @param speed The base speed stat
   * @param types List of types this Pokemon belongs to
   */
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

  // ┌──────────────────────────────────┐
  // | -------- GETTER METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Gets the name of this Pokemon.
   *
   * @return The Pokemon's name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the current HP of this Pokemon.
   *
   * @return The current HP value
   */
  public int getHp() {
    return hp.get();
  }

  /**
   * Gets the HP property for JavaFX binding.
   *
   * @return The HP property
   */
  public IntegerProperty hpProperty() {
    return hp;
  }

  /**
   * Gets the maximum HP of this Pokemon.
   *
   * @return The maximum HP value
   */
  public int getMaxHp() {
    return maxHP;
  }

  /**
   * Gets the current attack stat, including any modifiers.
   *
   * @return The modified attack stat
   */
  public int getAttack() {
    return (int) (attack * attackFactor);
  }

  /**
   * Gets the current defense stat, including any modifiers.
   *
   * @return The modified defense stat
   */
  public int getDefense() {
    return (int) (defense * defenseFactor);
  }

  /**
   * Gets the current special attack stat, including any modifiers.
   *
   * @return The modified special attack stat
   */
  public int getSpecialAttack() {
    return (int) (specialAttack * specialAttackFactor);
  }

  /**
   * Gets the current special defense stat, including any modifiers.
   *
   * @return The modified special defense stat
   */
  public int getSpecialDefense() {
    return (int) (specialDefense * specialDefenseFactor);
  }

  /**
   * Gets the current speed stat, including any modifiers.
   *
   * @return The modified speed stat
   */
  public int getSpeed() {
    return (int) (speed * speedFactor);
  }

  /**
   * Gets a copy of this Pokemon's types.
   *
   * @return A list containing all types of this Pokemon
   */
  public List<Type> getTypes() {
    return new ArrayList<>(types);
  }

  /**
   * Gets a copy of this Pokemon's attacks.
   *
   * @return A list containing all attacks of this Pokemon
   */
  public List<Attack> getAttacks() {
    return new ArrayList<>(attacks);
  }

  /**
   * Gets the current status condition affecting this Pokemon.
   *
   * @return The current status condition, or null if none
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Gets the front sprite image path for this Pokemon.
   *
   * @return The front image file path
   */
  public String getFrontImage() {
    return frontImage;
  }

  /**
   * Gets the back sprite image path for this Pokemon.
   *
   * @return The back image file path
   */
  public String getBackImage() {
    return backImage;
  }

  /**
   * Checks if this Pokemon is currently confused.
   *
   * @return true if confused, false otherwise
   */
  public boolean isConfused() {
    return confused;
  }

  /**
   * Gets the number of turns remaining for confusion.
   *
   * @return The confusion counter value
   */
  public int getConfusionCounter() {
    return confusionCounter;
  }

  // ┌──────────────────────────────────┐
  // | -------- SETTER METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Sets a status condition on this Pokemon.
   * Also applies the immediate effects of the status.
   *
   * @param status The status to apply
   */
  public void setStatus(Status status) {
    this.status = status;
    if (status != null) {
      status.applyStartEffect(this);
    }
  }

  /**
   * Sets the front sprite image path for this Pokemon.
   *
   * @param frontImage The front image file path
   */
  public void setFrontImage(String frontImage) {
    this.frontImage = frontImage;
  }

  /**
   * Sets the back sprite image path for this Pokemon.
   *
   * @param backImage The back image file path
   */
  public void setBackImage(String backImage) {
    this.backImage = backImage;
  }

  /**
   * Sets whether this Pokemon is confused.
   *
   * @param confused true to make confused, false to remove confusion
   */
  public void setConfused(boolean confused) {
    this.confused = confused;
  }

  /**
   * Sets the number of turns this Pokemon will remain confused.
   *
   * @param confusionCounter The number of turns of confusion
   */
  public void setConfusionCounter(int confusionCounter) {
    this.confusionCounter = confusionCounter;
  }

  // ┌──────────────────────────────────┐
  // | -------- ATTACK METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Adds an attack to this Pokemon's moveset.
   * A Pokemon can have a maximum of 4 attacks.
   *
   * @param attack The attack to add
   */
  public void addAttack(Attack attack) {
    if (attacks.size() < 4) {
      attacks.add(attack);
    }
  }

  /**
   * Removes an attack from this Pokemon's moveset.
   *
   * @param attack The attack to remove
   */
  public void removeAttack(Attack attack) {
    attacks.remove(attack);
  }

  /**
   * Checks if this Pokemon is of the specified type.
   *
   * @param type The type to check
   * @return true if this Pokemon has the specified type, false otherwise
   */
  public boolean isOfType(Type type) {
    return types.contains(type);
  }

  // ┌──────────────────────────────────┐
  // | -------- BATTLE METHODS -------- |
  // └──────────────────────────────────┘

  /**
   * Reduces this Pokemon's HP by the specified damage amount.
   * HP will not go below 0.
   *
   * @param damage The amount of damage to inflict
   */
  public void takeDamage(int damage) {
    int newHp = Math.max(0, hp.get() - damage);
    hp.set(newHp);
  }

  /**
   * Restores this Pokemon's HP by the specified amount.
   * HP will not exceed maximum HP.
   *
   * @param amount The amount of HP to restore
   */
  public void heal(int amount) {
    int newHp = Math.min(maxHP, hp.get() + amount);
    hp.set(newHp);
  }

  /**
   * Checks if this Pokemon has fainted (0 HP).
   *
   * @return true if fainted, false otherwise
   */
  public boolean isFainted() {
    return hp.get() <= 0;
  }

  /**
   * Determines if this Pokemon can attack this turn.
   * Takes into account status conditions and confusion.
   *
   * @return true if the Pokemon can attack, false otherwise
   */
  public boolean canAttack() {
    if (status != null && !status.canAttack()) {
        return false;
    }

    if (confused) {
        // 50% chance to hurt itself
        if (Math.random() < 0.5) {
            int confusionDamage = Math.max(1, attack / 2);
            takeDamage(confusionDamage);
            System.out.println(name + " hurt itself in confusion!");
            return false;
        }
    }
    
    return true;
  }

  // ┌─────────────────────────────────────────────┐
  // | -------- STAT MODIFICATION METHODS -------- |
  // └─────────────────────────────────────────────┘

  /**
   * Modifies this Pokemon's attack stat by the specified number of levels.
   *
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   */
  public void modifyAttack(int levels) {
    attackFactor = getFactorByLevel(attackFactor, levels);
  }

  /**
   * Modifies this Pokemon's defense stat by the specified number of levels.
   *
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   */
  public void modifyDefense(int levels) {
    defenseFactor = getFactorByLevel(defenseFactor, levels);
  }

  /**
   * Modifies this Pokemon's special attack stat by the specified number of levels.
   *
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   */
  public void modifySpecialAttack(int levels) {
    specialAttackFactor = getFactorByLevel(specialAttackFactor, levels);
  }

  /**
   * Modifies this Pokemon's special defense stat by the specified number of levels.
   *
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   */
  public void modifySpecialDefense(int levels) {
    specialDefenseFactor = getFactorByLevel(specialDefenseFactor, levels);
  }

  /**
   * Modifies this Pokemon's speed stat by the specified number of levels.
   *
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   */
  public void modifySpeed(int levels) {
    speedFactor = getFactorByLevel(speedFactor, levels);
  }

  /**
   * Calculates the stat modifier factor based on the number of levels.
   * Implements the standard Pokemon stat modification formula.
   *
   * @param currentFactor The current stat modification factor
   * @param levels The number of levels to modify (positive to increase, negative to decrease)
   * @return The new stat modification factor
   */
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

  // ┌──────────────────────────────────────────────┐
  // | -------- END-OF-TURN EFFECT METHODS -------- |
  // └──────────────────────────────────────────────┘

  /**
   * Applies all end-of-turn effects, including status conditions and confusion.
   * Updates status durations and may remove expired conditions.
   */
  public void applyEndTurnEffects() {
    if (status != null) {
        status.applyEndEffect(this);
        
        if (status.shouldRemoveAfterTurn()) {
            status = null;
            System.out.println(name + " recovered from its status condition!");
        }
    }
    
    if (confused) {
        confusionCounter--;
        if (confusionCounter <= 0) {
            confused = false;
            System.out.println(name + " snapped out of confusion!");
        }
    }
  }

  // ┌────────────────────────────────┐
  // | -------- MISC METHODS -------- |
  // └────────────────────────────────┘

  /**
   * Returns the string representation of this Pokemon, which is its name.
   *
   * @return The Pokemon's name
   */
  @Override
  public String toString() {
    return name;
  }
}
