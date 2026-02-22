package br.edu.ifpb.sonoryextends.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connection {
    private static final String URL = "jdbc:postgresql://localhost:5432/sonory_extends";
    private static final String USER = "postgres";
    private static final String PASSWORD = "54321";

    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public PreparedStatement pr() {
        return null;
    }
}
