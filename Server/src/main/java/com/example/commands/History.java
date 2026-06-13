package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.Math.max;
/**
 * Комманда для выведения последних 14 команд
 *
 */
public class History implements Command {
    ArrayList<String> history;
    public History(ArrayList<String> commandHistory) {
        history = commandHistory;
    }

    @Override
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        for (int i = max(history.toArray().length - 14, 0); i < history.toArray().length; i++) {
            out.println(history.get(i));
        }

        out.println("история команд выведена\n");
    }

    @Override
    public String getComandInfo() {
        return "history : вывести последние 14 команд (без их аргументов)\n";
    }
}
