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
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String password) {
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

        try {
            collectionManager.updateLessHelth(spacemar.getId(), spacemar, collectionManager.getUserId(login));
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
