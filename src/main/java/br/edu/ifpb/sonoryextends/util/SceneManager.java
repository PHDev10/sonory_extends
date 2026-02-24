package br.edu.ifpb.sonoryextends.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Scene scene;

    public static void init(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource("/view/user-select-view.fxml")
        );

        Parent root = loader.load();
        scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(
                SceneManager.class.getResource("/style/style.css").toExternalForm()
        );

        stage.setTitle("Sonory Extends");
        stage.setScene(scene);
        stage.show();
    }

    public static Object switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

            Parent root = loader.load();
            scene.setRoot(root);

            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o FXML: " + fxmlPath);
        }
    }
}