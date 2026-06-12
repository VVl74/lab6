package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для выведения информации о коллекции
 *
 */
public class Info implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        out.println(" время создания "  + collectionManager.getTimeinit() +
                " колличество элементов " + collectionManager.countElement());

        out.println("информация о коллекции выведена\n");
    }
    public String getComandInfo() {
        return "info : вывести в стандартный поток вывода информацию" +
                " о коллекции (тип, дата инициализации, количество элементов и т.д.)\n";
    }
}
