package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для удаления элемента из коллекции по ключу
 *
 */
public class RemoveKey implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        if (args.length !=1) {
            throw new ArgExeption();
            // System.out.println("неверное число аргументов");
        }
        int id = Integer.parseInt(args[0]);
        collectionManager.removeMarine(id, collectionManager.getUserId(login));

        out.println("элемент удален\n");
    }
    public String getComandInfo() {
        return "remove_key key (int) : удалить элемент из коллекции по его ключу\n";
    }
}
