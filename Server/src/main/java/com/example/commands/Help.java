package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;

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
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
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
