package com.example.commands;

import com.example.collection.Chapter;
import com.example.collection.SpaceMarine;
import com.example.commands.Command;
import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Комманда для выведения всех элементов чей Chapter меньше заданного
 *
 */
public class FilterLessThanChapter implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        if (args.length !=4) {
            throw  new ArgExeption();
            // System.out.println("неверное число аргументов");
        }
        Parser parser = new Parser();
        Chapter chapter = null;
        try {
            chapter = parser.parseChapter(args);
        } catch (Exception e) {
            throw new InputExeption();
        }


        ConcurrentHashMap<Integer, SpaceMarine> res = collectionManager.selectChapterLess((int) chapter.getMarinesCount());


        for (var v : res.values()) {
            out.println(collectionManager.getCollection().get(v));
        }

        out.println("все элементы с Chapter < заданного выведены\n");
    }
    public String getComandInfo() {
        return "filter_greater_than_chapter chapter : вывести элементы, значение поля chapter которых меньше заданного\n" +
                "сравнение производится по полю marinesCount\n" +
                "chapter вводится через пробел в следующем порядке:\n" +
                "(string) name (string) parentLegion (long) marinesCount (string) world\n";
    }
}
