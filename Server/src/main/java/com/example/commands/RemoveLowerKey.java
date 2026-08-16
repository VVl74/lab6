package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

/**
 * Комманда для удаления всех элементов чей ключ меньше заданного
 */
public class RemoveLowerKey implements Command {
    private final DBCollectionManager collectionManager;

    public RemoveLowerKey(DBCollectionManager collectionManager) {
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

        collectionManager.removeLowerKeyMarine(id, collectionManager.getUserId(ctx.getLogin()));
        out.println("все элементы чей ключ < заданного удалены\n");
    }

    @Override
    public String getComandInfo() {
        return "remove_lower_key key (int) : удалить из коллекции все элементы, " +
                "ключ которых меньше, чем заданный\n";
    }
}
