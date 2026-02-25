package br.edu.ifpb.sonoryextends.app;

import br.edu.ifpb.sonoryextends.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}