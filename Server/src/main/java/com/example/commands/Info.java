package com.example.commands;

import com.example.managers.DBCollectionManager;

/**
 * Комманда для выведения информации о коллекции
 */
public class Info implements Command {
    private final DBCollectionManager collectionManager;

    public Info(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        ctx.getOut().println(" время создания " + collectionManager.getTimeinit() +
                " колличество элементов " + collectionManager.countElement());
        ctx.getOut().println("информация о коллекции выведена\n");
    }

    @Override
    public String getComandInfo() {
        return "info : вывести в стандартный поток вывода информацию" +
                " о коллекции (тип, дата инициализации, количество элементов и т.д.)\n";
    }
}
