package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Комманда для выведения всех элементов коллекции
 *
 */
public class Show implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        Collection<SpaceMarine> mapValues = collectionManager.getCollection().values();

        ArrayList<SpaceMarine> nmarines =  mapValues.stream()
                .sorted(Comparator.comparing(marine -> marine.getChapter().getWorld()))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));


        for (var v: nmarines) {
            out.println(v);
        }

        out.println("элементы коллекции выведены\n");
    }

    public  String getComandInfo() {
        return "show : вывести в стандартный поток вывода все элементы" +
                " коллекции в строковом представлении\n";
    }
}
