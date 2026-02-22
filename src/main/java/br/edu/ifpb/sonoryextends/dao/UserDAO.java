package br.edu.ifpb.sonoryextends.dao;

import br.edu.ifpb.sonoryextends.model.User;
import br.edu.ifpb.sonoryextends.util.Connection;

import java.sql.*;

public class UserDAO {
    public User finByName(String nome) {
        String sql = "SELECT * FROM user_profile WHERE nome = ?";

        try (java.sql.Connection connection = Connection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setNome(resultSet.getString("nome"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User save(User user) {
        String sql = "INSERT INTO user_profile (nome) VALUES (?) RETURNING id";

        try (java.sql.Connection connection = Connection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getNome());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
