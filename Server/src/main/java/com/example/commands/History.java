package com.example.commands;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Комманда для выведения последних 14 команд
 */
public class History implements Command {
    private final ConcurrentLinkedDeque<String> history;

    public History(ConcurrentLinkedDeque<String> commandHistory) {
        history = commandHistory;
    }

    @Override
    public void execute(CommandContext ctx) {
        Object[] historyArray = history.toArray();
        int start = Math.max(historyArray.length - 14, 0);
        for (int i = start; i < historyArray.length; i++) {
            ctx.getOut().println(historyArray[i]);
        }
        ctx.getOut().println("история команд выведена\n");
    }

    @Override
    public String getComandInfo() {
        return "history : вывести последние 14 команд (без их аргументов)\n";
    }
}
