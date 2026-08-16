package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.exeptions.ArgExeption;
import com.example.managers.DBCollectionManager;
import com.example.utils.Parser;

import java.io.PrintWriter;

/**
 * Комманда для добавления элемента в коллекцию
 */
public class Insert implements Command {
    private final DBCollectionManager collectionManager;

    public Insert(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        String[] args = ctx.getArgs();
        PrintWriter out = ctx.getOut();
        String login = ctx.getLogin();

        if (args.length != 12) {
            throw new ArgExeption();
        }
        Parser parser = new Parser(out);
        int ownerId = collectionManager.getUserId(login);
        SpaceMarine spacemar;
        try {
            spacemar = parser.parsSpaceMarine(args, ownerId);
            spacemar.setOwnerName(login);
        } catch (Exception e) {
            out.println("ошибка ввода данных");
            return;
        }

        if (collectionManager.insertMarine(spacemar, collectionManager.getUserId(login))) {
            out.println("элемент добавлен\n");
        } else {
            out.println("не удалось добавить элемент\n");
        }
    }

    @Override
    public String getComandInfo() {
        return "insert {element} — добавить новый элемент с указанным ключом.\n" +
                "В интерактивном режиме поля запрашиваются по одному после приглашения.\n" +
                "Можно также передать все аргументы в одной строке через пробел в порядке:\n" +
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
