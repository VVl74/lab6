package com.example.commands;

import java.util.HashMap;
import java.util.Set;

/**
 * Комманда для выведения справки по всем командам
 */
public class Help implements Command {
    private final HashMap<String, Command> commandHashMap;

    public Help(HashMap<String, Command> newCommandHashMap) {
        commandHashMap = newCommandHashMap;
    }

    @Override
    public void execute(CommandContext ctx) {
        Set<String> keys = commandHashMap.keySet();
        for (String i : keys) {
            ctx.getOut().println(commandHashMap.get(i).getComandInfo());
        }
        ctx.getOut().println("все команды выведены\n");
    }

    @Override
    public String getComandInfo() {
        return "help : вывести справку по доступным командам\n";
    }
}
