package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.exeptions.ArgExeption;
import com.example.managers.CollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;

/**
 * Комманда для обновления элемента на новый по заданному ID
 *
 */
public class UpdateId implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        if (args.length != 12) {
            throw new ArgExeption();
        }
        Parser parser = new Parser();
        SpaceMarine spacemar;
        try {
            spacemar = parser.parsSpaceMarine(args);
        } catch (Exception e) {
            out.println("ошибка ввода данных");
            return;
        }
        collectionManager.swapElement(spacemar, spacemar.getId());

        out.println("элемент обновлен\n");
    }
    public String getComandInfo() {
        return "update id {element} : обновить значение элемента коллекции," +
                " id которого равен заданному передать элемент необходимо в формате" +
                "аналогичном команде insert\n";
    }
}
