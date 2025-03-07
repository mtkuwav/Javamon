package javamon.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javamon.tests.TypeTest;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        // Run type tests first
        System.out.println("Running type system tests...");
        TypeTest.main(args);
        System.out.println("\nStarting JavaMon application...");
        
        // Launch the JavaFX application
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("Hello, JavaFX!");
        StackPane root = new StackPane();
        root.getChildren().add(label);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("JavaMon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}