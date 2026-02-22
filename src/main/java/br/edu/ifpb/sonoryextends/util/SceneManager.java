package br.edu.ifpb.sonoryextends.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    public static Object switchScene(Stage stage, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneManager.class.getResource("/style/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            return loader.getController();
        } catch (Exception e) {
            System.err.println("Erro ao carregar FXML: " + fxml);
            e.printStackTrace();
            return null;
        }
    }
}