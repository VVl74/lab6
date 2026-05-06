package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.commands.Command;
import com.example.exeptions.ArgExeption;
import com.example.managers.CollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;

/**
 * Комманда замены элемента если его здоровье меньше чем текущего
 *
 */
public class ReplaceIfLoweNull implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        if (args.length != 12) {
            throw new ArgExeption();
            // System.out.println("Неверный ввод данных");
        }
        Parser parser = new Parser();

        SpaceMarine spacemar;
        try {
            spacemar = parser.parsSpaceMarine(args);
        } catch (Exception e) {
            out.println("ошибка ввода данных");
            return;
        }
        if (collectionManager.getCollection().get(spacemar.getId()).compareTo(spacemar) > 0) {
            collectionManager.swapElement(spacemar, spacemar.getId());
            out.println("элемент обновлен\n");
        } else {
            out.println("элемент не обновлен\n");
        }
    }
    public String getComandInfo() {
        return "replace_if_lower key {element} : заменить значение по ключу, если новое значение меньше старого\n" +
                "сравнение производится по полю health\n" +
                "элемент необходимо передать в том же формате, что и в команде insert\n";
    }
}
