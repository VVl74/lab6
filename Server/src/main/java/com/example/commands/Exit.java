package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Комманда для выхода из программы
 *
 */
public class Exit implements Command {
    private HashMap<String, Command> commandHashMap;
    private String filename = "collect_file";
    public Exit(HashMap<String, Command> commandHashMap) {
        this.commandHashMap = commandHashMap;
    }
    public void execute() {}
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        // commandHashMap.get("save").execute(new String[]{filename}, collectionManager, out);
        System.exit(0);
    }
    public String getComandInfo() {
        return "exit : завершить программу (без сохранения в файл)\n";
    }
}
