package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для выведения информации о коллекции
 *
 */
public class Info implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        out.println("Тип коллекции: " + collectionManager.getCollection().getClass().getName() +
                " время создания "  + collectionManager.getTime() +
                " колличество элементов " + collectionManager.getCollection().size());

        out.println("информация о коллекции выведена\n");
    }
    public String getComandInfo() {
        return "info : вывести в стандартный поток вывода информацию" +
                " о коллекции (тип, дата инициализации, количество элементов и т.д.)\n";
    }
}
