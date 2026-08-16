package com.example.commands;

import com.example.managers.DBCollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Комманда для удаления всех элементов коллекции
 */
public class Clear implements Command {
    private final DBCollectionManager collectionManager;

    public Clear(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        Logger logger = LoggerFactory.getLogger(Clear.class);
        collectionManager.removeAll(collectionManager.getUserId(ctx.getLogin()));
        ctx.getOut().println("коллекция очищена\n");
        logger.info("коллекция очщена");
    }

    @Override
    public String getComandInfo() {
        return "clear: очистить коллекцию\n";
    }
}
