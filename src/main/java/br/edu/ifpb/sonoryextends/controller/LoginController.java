package br.edu.ifpb.sonoryextends.controller;

import br.edu.ifpb.sonoryextends.dao.UserDAO;
import br.edu.ifpb.sonoryextends.model.User;
import br.edu.ifpb.sonoryextends.util.SceneManager;
import br.edu.ifpb.sonoryextends.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
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
        User user = userDAO.findByName(nomeDigitado);

        if (user != null) {
            usuarioLogado = user;
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Usuário não encontrado.");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Usuário não encontrado. Deseja criar um novo usuário com esse nome?");

            Optional<ButtonType> resultado = confirmacao.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                usuarioLogado = userDAO.save(new User(nomeDigitado));
            } else {
                return;
            }
        }

        Session.setUsuarioAtual(usuarioLogado);
        entrarNoSistema();
    }

    public void entrarNoSistema() {
        ConvertController controller = (ConvertController) SceneManager.switchScene("/view/convert-view.fxml");
    }

    public void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
