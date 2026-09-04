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
    final String URL = "jdbc:postgresql://localhost:5432/studs";
    private Connection connection; // ПОдключение к нашей базе

    // Конструктор дефолтный
    public DBManager() throws SQLException {
    }

    // Синглтон. Проверка на уже существующий экземпляр класса
    public static DBManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    /**
     * Команда для подключения к базе данных
     *
     */
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            System.out.println("Подключены к БД. \n");
        }
    }

    /**
     * Команда для отключения от базы данных
     *
     */
    public void disconect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Отключены от БД. \n");
        }
    }

    /**
     * Метод для выполнения SQL-query запроса
     *
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeQuery(sql);
    }

    /**
     * Метод для выполнения SQL-update запроса
     *
     */
    public int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeUpdate(sql);
    }

    /**
     * Метод для подготовки выполнения запроса
     *
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Метод для получения текущего соединения с БД
     */
    public Connection getConnection() {
        return connection;
    }
}
