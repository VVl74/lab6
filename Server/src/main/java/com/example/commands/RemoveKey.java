package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для удаления элемента из коллекции по ключу
 */
public class RemoveKey implements Command {
    private final DBCollectionManager collectionManager;

    public RemoveKey(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        String[] args = ctx.getArgs();
        PrintWriter out = ctx.getOut();

        if (args.length != 1) {
            throw new ArgExeption();
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new InputExeption(e.getMessage(), out);
        }

        if (collectionManager.removeMarine(id, collectionManager.getUserId(ctx.getLogin()))) {
            out.println("элемент удален\n");
        } else {
            out.println("элемент не найден или принадлежит другому пользователю\n");
        }
    }

    @Override
    public String getComandInfo() {
        return "remove_key key (int) : удалить элемент из коллекции по его ключу\n";
    }
}
