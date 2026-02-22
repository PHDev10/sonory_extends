package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.dao.ConversionHistoryDAO;
import br.edu.ifpb.sonoryextends.model.ConversionHistory;
import br.edu.ifpb.sonoryextends.util.Session;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class HistoryController {
    @FXML
    private TableView<ConversionHistory> table;
    @FXML
    private TableColumn<ConversionHistory, String> colNomeOriginal;
    @FXML
    private TableColumn<ConversionHistory, String> colFormatoOriginal;
    @FXML
    private TableColumn<ConversionHistory, String> colFormatoConversao;
    @FXML
    private TableColumn<ConversionHistory, LocalDateTime> colData;
    @FXML
    private TableColumn<ConversionHistory, String> colStatus;

    @FXML
    public void initialize() {
        colNomeOriginal.setCellValueFactory(new PropertyValueFactory<>("nomeOriginal"));
        colFormatoOriginal.setCellValueFactory(new PropertyValueFactory<>("formatoOriginal"));
        colFormatoConversao.setCellValueFactory(new PropertyValueFactory<>("formatoConversao"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataConversao"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ConversionHistoryDAO conversionHistoryDAO = new ConversionHistoryDAO();
        int userId = Session.getUsuarioAtual().getId();

        List<ConversionHistory> history = conversionHistoryDAO.findLast7DaysByUser(userId);
        table.getItems().addAll(history);
    }

    @FXML
    private void abrirPastaBackup() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String nomeSeguro = Session.getUsuarioAtual().getNome().toLowerCase().replaceAll("[^a-z0-9]", "_");

                Path pasta = Paths.get(System.getProperty("user.home"),"SonoryExtends", "audio_backups", "user_" + nomeSeguro);

                if (!Files.exists(pasta)) {
                    Platform.runLater(() ->
                            showAlert("Pasta não encontrada."));
                    return null;
                }
                new ProcessBuilder("xdg-open", pasta.toString()).start();

                return null;
            }
        };

        new Thread(task).start();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sonory Extends");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
