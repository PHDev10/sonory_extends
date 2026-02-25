package br.edu.ifpb.sonoryextends.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage stage;
    private static Scene scene;

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 700;

    public static void init(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent root = FXMLLoader.load(
                SceneManager.class.getResource("/view/user-select-view.fxml")
        );

        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(SceneManager.class.getResource("/style/style.css").toExternalForm());

        stage.setTitle("Sonory Extends");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static <T> T switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

            Parent root = loader.load();
            scene.setRoot(root);

            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}