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
}
