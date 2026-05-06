package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для выхода из программы
 *
 */
public class Exit implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        System.exit(0);
    }
    public String getComandInfo() {
        return "exit : завершить программу (без сохранения в файл)\n";
    }
}
