package com.example.commands;

import com.example.collection.Chapter;
import com.example.commands.Command;
import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.CollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;
import java.util.Set;
/**
 * Комманда для выведения всех элементов чей Chapter больше заданного
 *
 */
public class FilterGreaterThanChapter implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        if (args.length !=4) {
            throw new ArgExeption();
        }

        Parser parser = new Parser();
        Chapter chapter = null;
        try {
            chapter = parser.parseChapter(args);
        } catch (Exception e) {
            throw new InputExeption();
        }

        Set<Integer> mapValues = collectionManager.getCollection().keySet();

        for (var v : mapValues) {
            if (collectionManager.getCollection().get(v).getChapter().compareTo(chapter) > 0) {
                out.println(collectionManager.getCollection().get(v));
            }
        }

        out.println("все элементы с Chapter > заданного выведены\n");
    }
    public String getComandInfo() {
        return "filter_greater_than_chapter chapter : вывести элементы, значение поля chapter которых больше заданного\n" +
                "сравнение производится по полю marinesCount\n" +
                "chapter вводится через пробел в следующем порядке:\n" +
                "(string) name (string) parentLegion (long) marinesCount (string) world\n";
    }
}
