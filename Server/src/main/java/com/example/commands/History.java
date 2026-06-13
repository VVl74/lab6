package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Math.max;
/**
 * Комманда для выведения последних 14 команд
 *
 */
public class History implements Command {
    ConcurrentLinkedDeque<String> history;
    public History(ConcurrentLinkedDeque<String> commandHistory) {
        history = commandHistory;
    }

    @Override
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        Object[] historyArray = history.toArray();

        int start = Math.max(historyArray.length - 14, 0);

        for (int i = start; i < historyArray.length; i++) {
            out.println(historyArray[i]);
        }
        out.println("история команд выведена\n");
    }

    @Override
    public String getComandInfo() {
        return "history : вывести последние 14 команд (без их аргументов)\n";
    }
}
