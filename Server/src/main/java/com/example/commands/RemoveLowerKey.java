package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Комманда для удаления всеъ элементов чей улюч меньше заданного
 *
 */
public class RemoveLowerKey implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        if (args.length > 1) {
            throw new ArgExeption();
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new InputExeption();
        }

        collectionManager.removeLowerKeyMarine(id, collectionManager.getUserId(login));

        out.println("все элементы чей ключ < заданного удалены\n");
    }
    public String getComandInfo() {
        return "remove_lower_key key (int) : удалить из коллекции все элементы, " +
                "ключ которых меньше, чем заданный\n";
    }
}
