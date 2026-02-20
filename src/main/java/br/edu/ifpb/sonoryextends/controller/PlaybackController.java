package br.edu.ifpb.sonoryextends.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PlaybackController {
    @FXML
    private ListView<String> listaDeVisualizacaoArquivos;
    private MediaPlayer mediaPlayer;

    @FXML
    private void handlePlay() {
        String selectedFile = listaDeVisualizacaoArquivos.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            Media media = new Media(new File(selectedFile).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }
}
