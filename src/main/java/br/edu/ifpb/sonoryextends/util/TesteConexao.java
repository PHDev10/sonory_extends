package br.edu.ifpb.sonoryextends.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("Iniciando teste...");
        try (Connection connection = Conexao.getConnection()) {
            System.out.println("Conectado!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
