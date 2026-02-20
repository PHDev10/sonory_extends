package br.edu.ifpb.sonoryextends.dao;

import br.edu.ifpb.sonoryextends.model.AudioFile;
import br.edu.ifpb.sonoryextends.util.Connection;

import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AudioFileDAO {

    public void inserir(AudioFile audioFile) {
        String sql = "INSERT INTO audio_file (nome, caminho_original, formato, tamanho) VALUES (?, ?, ?, ?, ?)";

        try (java.sql.Connection connection = Connection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, audioFile.getNome());
            statement.setString(2, audioFile.getCaminhoOriginal());
            statement.setString(3, audioFile.getFormato());
            statement.setLong(1, audioFile.getTamanho());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
