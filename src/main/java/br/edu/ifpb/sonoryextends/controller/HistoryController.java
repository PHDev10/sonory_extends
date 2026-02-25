package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.dao.ConversionHistoryDAO;
import br.edu.ifpb.sonoryextends.model.ConversionHistory;
import br.edu.ifpb.sonoryextends.model.User;
import br.edu.ifpb.sonoryextends.util.SceneManager;
import br.edu.ifpb.sonoryextends.util.Session;
import br.edu.ifpb.sonoryextends.util.Connection;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        User usuario = Session.getUsuarioAtual();

        if (usuario == null) {
            showAlert("Sessão expirada. Faça login novamente.");

            Platform.runLater(() -> {
                SceneManager.switchScene("/view/user-select-view.fxml");
            });

            return;
        }

        colNomeOriginal.setCellValueFactory(new PropertyValueFactory<>("nomeOriginal"));
        colFormatoOriginal.setCellValueFactory(new PropertyValueFactory<>("formatoOriginal"));
        colFormatoConversao.setCellValueFactory(new PropertyValueFactory<>("formatoConversao"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataConversao"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ConversionHistoryDAO conversionHistoryDAO = new ConversionHistoryDAO();

        List<ConversionHistory> history =
                conversionHistoryDAO.findLast7DaysByUser(usuario.getId());

        table.getItems().setAll(history);
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

    @FXML
    private void handleApagarHistorico() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar exclusão");
        alert.setHeaderText("Apagar todo o histórico?");
        alert.setContentText("Essa ação não poderá ser desfeita depois");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.getItems().clear();
            limparArquivoHistorico();
        }
    }

    @FXML
    private void handleVoltar() {
        SceneManager.switchScene("/view/convert-view.fxml");
    }

    private void limparArquivoHistorico() {
        String sql = "TRUNCATE TABLE conversion_history";

        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sonory Extends");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
