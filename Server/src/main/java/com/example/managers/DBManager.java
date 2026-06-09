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
    final String URL = "jdbc:postgresql://localhost:5433/studs"; // тут нужно будет пробросить порт с твоего компа на сервак, у меня только так оно заработало
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

    // Подключение к базе данных
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            System.out.println("Connected to bd successfully. \n");
        }
    }

    // Отключение от базы данных
    public void disconect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from db successfully. \n");
        }
    }

    // Выполнение QUERY-запроса
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeQuery(sql);
    }

    // Выполнение update-запроса
    public int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeUpdate(sql);
    }

    // Подготовка запроса (Там на метоните было написано про это но чет лень было читать, думаю, пусть будет)
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    // Возвращает наше подключение, пока хз зачем, но идешка сказала что нужно
    public Connection getConnection() {
        return connection;
    }
}
