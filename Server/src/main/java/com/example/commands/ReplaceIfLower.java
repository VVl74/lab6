package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.exeptions.ArgExeption;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Комманда замены элемента если его здоровье меньше чем текущего
 *
 */
public class ReplaceIfLower implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        if (args.length != 12) {
            throw new ArgExeption();
            // System.out.println("Неверный ввод данных");
        }
        Parser parser = new Parser(out);
        int ownerId = collectionManager.getUserId(login);

        SpaceMarine spacemar;
        try {
            spacemar = parser.parsSpaceMarine(args, ownerId);
        } catch (Exception e) {
            out.println("ошибка ввода данных");
            return;
        }

        try {
            if (collectionManager.updateLessHelth(spacemar.getId(), spacemar, ownerId)) {
                out.println("элемент заменен\n");
            } else {
                out.println("элемент не заменен\n");
            }
        } catch (SQLException e) {
            out.println("запрос не удался");
            return;
        }
    }
    public String getComandInfo() {
        return "replace_if_lower key {element} : заменить значение по ключу, если новое значение меньше старого\n" +
                "сравнение производится по полю health\n" +
                "элемент необходимо передать в том же формате, что и в команде insert\n";
    }
}
