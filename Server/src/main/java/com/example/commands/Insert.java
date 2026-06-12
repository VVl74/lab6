package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.exeptions.ArgExeption;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;

/**
 * Комманда для добавления элемента в коллекцию
 *
 */
public class Insert implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String passwordHash) {
        if (args.length != 12) {
            throw new ArgExeption();
            //System.out.println("неверное число аргументов " + args.length);
        }
        Parser parser = new Parser();
        SpaceMarine spacemar = null;
        try {
            spacemar = parser.parsSpaceMarine(args);
        } catch (Exception e) {
            out.println("ошибка ввода данных");
            return;
        }

        collectionManager.insertMarine(spacemar, collectionManager.getUserId(login));
        // collectionManager.inputElement(spacemar);

        out.println("элемент добавлен\n");
    }
    public String getComandInfo() {
        // insert 500 Ultramarine 12.5 7 150 ASSAULT BOLTGUN CHAIN_SWORD Ultramar Guilliman 500 Macragge
        return "insert {element} — добавить новый элемент с указанным ключом.\n" +
                "Аргументы необходимо вводить через пробел в следующем порядке:\n" +
                "1. (int) id — идентификатор\n" +
                "2. (string) name — имя\n" +
                "3. (float) cordX — координата X\n" +
                "4. (long) cordY — координата Y\n" +
                "5. (float) health — уровень здоровья\n" +
                "\n" +
                "6. AstartesCategory — категория (одно из значений):\n" +
                "   ASSAULT, TACTICAL, HELIX\n" +
                "\n" +
                "7. Weapon — основное оружие (одно из значений):\n" +
                "   BOLTGUN, MELTAGUN, FLAMER, HEAVY_FLAMER\n" +
                "\n" +
                "8. MeleeWeapon — оружие ближнего боя (одно из значений):\n" +
                "   CHAIN_SWORD, POWER_SWORD, CHAIN_AXE, MANREAPER, POWER_FIST\n" +
                "\n" +
                "9. (string) name_legion — название легиона\n" +
                "10. (string) commander_legion — имя командира легиона\n" +
                "11. (int) marinesCount — количество космодесантников\n" +
                "12. (string) planetName — название планеты\n";
    }
}
