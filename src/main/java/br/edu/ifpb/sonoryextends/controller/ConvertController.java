package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.service.AudioConversionService;

import br.edu.ifpb.sonoryextends.util.SceneManager;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ConvertController {
    @FXML
    private Label fileLabel;
    @FXML
    private ComboBox<String> formatComboBox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label folderLabel;
    @FXML
    private VBox rootPane;

    private File selectedFolder;
    private File selectFile;

    @FXML
    private void initialize() {
        formatComboBox.getItems().addAll("mp3", "wav", "ogg", "flac");
    }

    @FXML
    private void handleSelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo de Áudio");

        selectFile = fileChooser.showOpenDialog(null);
        if (selectFile != null) {
            fileLabel.setText(selectFile.getName());
        }
    }
    @FXML
    private void handleConvert() {
        if (selectFile == null) {
            showAlert("Selecione um arquivo de áudio.");
            return;
        }

        String format = formatComboBox.getValue();
        if (format == null) {
            showAlert("Selecione um formato para conversão.");
            return;
        }

        if (selectedFolder == null) {
            showAlert("Selecione uma pasta de destino.");
            return;
        }

        String outputPath = selectedFolder.getAbsolutePath() + "/" + selectFile.getName().replaceAll("\\.[^.]+$", "") + "." + format;
        File outputFile = new File(outputPath);

        AudioConversionService service = new AudioConversionService();
        Task<Boolean> task = service.createConversionTask(selectFile, outputFile);
        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);

            if (task.getValue()) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                SceneManager.switchScene(stage, "src/main/resources/view/playback-view.fxml");
            } else {
                showAlert("Erro na conversão.");
            }
        });

        task.setOnFailed(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);

            Throwable erro = task.getException();
            erro.printStackTrace();
            showAlert("Falha inesperada: " + erro.getMessage());
        });
        new Thread(task).start();
    }

    @FXML
    private void handleSelectFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecionar pasta de destino.");
        selectedFolder = chooser.showDialog(null);

        if (selectedFolder != null) {
            folderLabel.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    private void handleGoToPlayback() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "src/main/resources/view/playback-view.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sonory Extends");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
