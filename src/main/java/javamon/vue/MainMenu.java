package javamon.vue;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private Scene scene;

    public MainMenu() {
        VBox root = new VBox();
        Button fightButton = new Button("Fight");
        Button pokedexButton = new Button("Pokedex");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");

    }
}
