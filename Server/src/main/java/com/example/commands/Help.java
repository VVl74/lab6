package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
/**
 * Комманда для выведения справки по всем командам
 *
 */

public class Help implements Command {
    private HashMap<String, Command> commandHashMap;

    public Help(HashMap<String, Command> newCommandHashMap) {
        commandHashMap = newCommandHashMap;
    }
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        Set<String> keys = commandHashMap.keySet();

        for(String i: keys) {
            out.println(commandHashMap.get(i).getComandInfo());
        }

        out.println("все команды выведены\n");
    }
    public String getComandInfo() {
        return "help : вывести справку по доступным командам\n";
    }
}
