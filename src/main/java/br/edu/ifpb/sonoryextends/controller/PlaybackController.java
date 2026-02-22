package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.util.SceneManager;
import br.edu.ifpb.sonoryextends.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class PlaybackController {
    @FXML
    private ListView<File> listaDeVisualizacaoArquivos;
    @FXML
    private Slider volumeSlider;

    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        listaDeVisualizacaoArquivos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        volumeSlider.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100);
            }
        }));
    }

    @FXML
    private void handlePlay() {
        File selectedFile = listaDeVisualizacaoArquivos.getSelectionModel().getSelectedItem();

        if (selectedFile == null) {
            showAlert("Selecione um arquivo para reproduzir.");
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = new Media(selectedFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volumeSlider.getValue() / 100);
        mediaPlayer.play();
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer == null) {
            return;
        }
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else if (status == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void voltarParaConversao() {
        Stage stage = (Stage) listaDeVisualizacaoArquivos.getScene().getWindow();
        ConvertController controller = (ConvertController) SceneManager.switchScene(stage, "/view/convert-view.fxml");
        controller.setUsuarioLogado(Session.getUsuarioAtual());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sonory Extends");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setArquivosConvertidos(List<File> arquivosConvertidos) {
        listaDeVisualizacaoArquivos.getItems().clear();
        listaDeVisualizacaoArquivos.getItems().addAll(arquivosConvertidos);
    }

    public ListView<File> getListaDeVisualizacaoArquivos() {
        return listaDeVisualizacaoArquivos;
    }

    public void setListaDeVisualizacaoArquivos(ListView<File> listaDeVisualizacaoArquivos) {
        this.listaDeVisualizacaoArquivos = listaDeVisualizacaoArquivos;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
