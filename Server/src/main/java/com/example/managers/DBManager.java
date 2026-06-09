package com.example.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    final String USER = "s494533";
    final String PASSWD = "eW5IfMpYfQNlAvJ7";
    private Connection connection;
    private Statement statement;

    public DBManager() throws SQLException {
    }

    public void Connect(String URL) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWD);

        Statement statement = connection.createStatement();
        System.out.println("Connected successfully!\n");
        this.connection = connection;
        this.statement = statement;
    }
}
