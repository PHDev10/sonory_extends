package br.edu.ifpb.sonoryextends.dao;

import br.edu.ifpb.sonoryextends.model.ConversionHistory;
import br.edu.ifpb.sonoryextends.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistoryDAO {
    public void save(ConversionHistory conversionHistory) {
        String sql = "INSERT INTO conversion_history (nome_original, formato_original, formato_conversao, pacote_saida, data_conversao, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = Conexao.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, conversionHistory.getNomeOriginal());
            statement.setString(2, conversionHistory.getFormatoOriginal());
            statement.setString(3, conversionHistory.getFormatoConversao());
            statement.setString(4, conversionHistory.getPacoteDeSaida());
            statement.setTimestamp(5, Timestamp.valueOf(conversionHistory.getDataConversao()));
            statement.setString(6, conversionHistory.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ConversionHistory> findAll() {
        List<ConversionHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM conversion_history ORDER BY conversion_date DESC";

        try (Connection connection = Conexao.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ConversionHistory history = new ConversionHistory();
                
                history.setId(null);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;
    }
}
