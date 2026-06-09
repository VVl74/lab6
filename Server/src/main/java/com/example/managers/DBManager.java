package com.example.managers;

import java.sql.*;

/**
 * Базовый класс менеджера базы данных для установки соединения с БД и выполнения запросов к ней
 *
 */

public class DBManager {
    private static DBManager instance;
    final String USER = "s494533";
    final String PASSWD = "eW5IfMpYfQNlAvJ7";
    final String URL = "jdbc:postgresql://localhost:5433/studs";
    private Connection connection;
    private Statement statement;

    public DBManager() throws SQLException {
    }

    public static DBManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            System.out.println("Connected to bd successfully. \n");
        }
    }

    public void disconect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from db successfully. \n");
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeUpdate(sql);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public Connection getConnection() {
        return connection;
    }
}
