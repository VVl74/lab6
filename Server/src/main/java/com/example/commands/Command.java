package com.example.commands;

import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

/**
 * Интерфейс для всех команд для комманд паттерна
 *
 */

public interface Command {
    void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword);
    String getComandInfo();
}
