package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.CollectionManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Комманда для удаления всеъ элементов чей улюч меньше заданного
 *
 */
public class RemoveLowerKey implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        if (args.length > 1) {
            throw new ArgExeption();
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new InputExeption();
        }

        List<Integer> removeList = new ArrayList<>();

        Set<Integer> mapValues = collectionManager.getCollection().keySet();
        for (var v : mapValues) {
            if (v < id) {
                removeList.add(v);
            }
        }

        for (var k: removeList) {
            collectionManager.removeElement(k);
        }

        out.println("все элементы чей ключ < заданного удалены\n");
    }
    public String getComandInfo() {
        return "remove_lower_key key (int) : удалить из коллекции все элементы, " +
                "ключ которых меньше, чем заданный\n";
    }
}
