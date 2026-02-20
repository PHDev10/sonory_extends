package br.edu.ifpb.sonoryextends.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    public static void switchScene(Stage stage, String fxml) {
        System.out.println(SceneManager.class.getResource(fxml));
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar FXML: " + fxml);
            e.printStackTrace();
        }
    }
}
