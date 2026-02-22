package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.dao.UserDAO;
import br.edu.ifpb.sonoryextends.model.ConversionHistory;
import br.edu.ifpb.sonoryextends.model.User;
import br.edu.ifpb.sonoryextends.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController {
    @FXML
    private TextField txtNome;
    private User usuarioLogado;
    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void handleEntrar(ActionEvent event) {
        String nomeDigitado = txtNome.getText().trim();

        if (nomeDigitado.isEmpty()) {
            mostrarAlerta("Erro", "Nome digitado inválido");
            return;
        }
        User user = userDAO.finByName(nomeDigitado);

        if (user != null) {
            usuarioLogado = user;
            entarNoSistema();
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Usuário não encontrado.");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Usuário não encontrado. Deseja criar um novo usuário com esse nome?");

            Optional<ButtonType> resultado = confirmacao.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                User newUser = new User(nomeDigitado);
                usuarioLogado = userDAO.save(newUser);
                entarNoSistema();
            }
        }

        Session.setUsuarioAtual(user);
    }

    public void entarNoSistema() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/convert-view.fxml"));

            Scene scene = new Scene(loader.load());
            ConvertController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sonory Extends - Conversão");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public User getUsuarioLogado() {
        return usuarioLogado;
    }
}
