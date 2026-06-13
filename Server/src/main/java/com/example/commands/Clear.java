package com.example.commands;

import com.example.commands.Command;
import com.example.managers.CollectionManager;
import com.example.managers.DBCollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

/**
 * Комманда для удаления всех элементов коллекции
 *
 */
public class Clear implements Command {
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        Logger logger = LoggerFactory.getLogger(Clear.class);
        collectionManager.removeAll(collectionManager.getUserId(login));
        //collectionManager.getCollection().clear();
        out.println("коллекция очищена\n");
        logger.info("коллекция очщена");
    }
    public String getComandInfo() {
        return "clear: очистить коллекцию\n";
    }
}

