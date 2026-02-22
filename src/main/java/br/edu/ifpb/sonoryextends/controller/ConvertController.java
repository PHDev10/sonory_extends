package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.model.ConversionHistory;
import br.edu.ifpb.sonoryextends.model.User;
import br.edu.ifpb.sonoryextends.util.SceneManager;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
    private List<File> selectedFiles;
    private User usuarioLogado;

    @FXML
    private void initialize() {
        formatComboBox.getItems().addAll("mp3", "wav");
    }

    @FXML
    private void handleSelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivos de Áudio");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Áudios", "*.mp3", "*.wav"));
        selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            fileLabel.setText(selectedFiles.size() + "arquivo(s) selecionado(s)");
        }
    }

    @FXML
    private void handleConvert() {
        if (usuarioLogado == null) {
            showAlert("Erro: Usuário não encontrado.");
            return;
        }

        if (selectedFiles == null || selectedFiles.isEmpty()) {
            showAlert("Selecione pelo menos um arquivo de áudio.");
            return;
        }

        if (selectedFolder == null) {
            showAlert("Selecione uma pasta de destino.");
            return;
        }

        String format = formatComboBox.getValue();
        if (format == null) {
            showAlert("Selecione um formato para conversão.");
            return;
        }

        Task<List<File>> mainTask = new Task<>() {

            @Override
            protected List<File> call() throws Exception {

            List<File> arquivosConvertidos = new ArrayList<>();

            int total = selectedFiles.size();
            int count = 0;

            for (File inputFile : selectedFiles) {
                String outputPath = selectedFolder.getAbsolutePath() + "/" + inputFile.getName().replaceAll("\\.[^.]+$", "") + "." + format;

                File outputFile = new File(outputPath);
                List<String> command = List.of("ffmpeg", "-y", "-i", inputFile.getAbsolutePath(), outputFile.getAbsolutePath());

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                process.waitFor();

                if (process.exitValue() == 0) {
                    arquivosConvertidos.add(outputFile);

                    File backupFile = salvarBackup(outputFile);

                    ConversionHistory history = new ConversionHistory(
                            inputFile.getName(), getExtension(inputFile.getName()), format, backupFile.getAbsolutePath(), java.time.LocalDateTime.now(), "SUCESSO");

                    history.setUserId(usuarioLogado.getId());
                    new br.edu.ifpb.sonoryextends.dao.ConversionHistoryDAO().save(history);
                }

                count++;
                updateProgress(count, total);
            }

            return arquivosConvertidos;
            }
        };

        progressBar.progressProperty().bind(mainTask.progressProperty());

        mainTask.setOnSucceeded(event -> {

            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);

            List<File> arquivosConvertidos = mainTask.getValue();

            Stage stage = (Stage) rootPane.getScene().getWindow();

            PlaybackController controller =
                    (PlaybackController) SceneManager.switchScene(stage, "/view/playback-view.fxml");

            controller.setArquivosConvertidos(arquivosConvertidos);
        });

        mainTask.setOnFailed(event -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
            showAlert("Erro durante a conversão.");
            mainTask.getException().printStackTrace();
        });

        new Thread(mainTask).start();
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
        SceneManager.switchScene(stage, "/view/playback-view.fxml");
    }

    @FXML
    private void abrirHistorico() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/history-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Histórico de Conversões");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File salvarBackup(File arquivoConvertido) throws IOException {
        String home = System.getProperty("user.home");

        Path backupBase = Paths.get(home, "SonoryExtends", "audio_backups");
        Files.createDirectories(backupBase);
        String nomeSeguro = usuarioLogado.getNome().toLowerCase().replaceAll("[^a-z0-9]", "_");

        Path userFolder = backupBase.resolve("user_" + nomeSeguro);
        if (!Files.exists(userFolder)) {
            Files.createDirectories(userFolder);
        }

        Path backupFile = userFolder.resolve(arquivoConvertido.getName());
        Files.copy(arquivoConvertido.toPath(), backupFile, StandardCopyOption.REPLACE_EXISTING);

        return backupFile.toFile();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sonory Extends");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setUsuarioLogado(User user) {
        this.usuarioLogado = user;
        System.out.println("Usuário recebido: " + user.getNome());
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return "";
    }
}
