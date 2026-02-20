package br.edu.ifpb.sonoryextends.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    public static void switchScene(Stage stage, String fxml) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(fxml));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
