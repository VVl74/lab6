package com.example.commands;

import com.example.commands.Command;
import com.example.exeptions.ArgExeption;
import com.example.exeptions.RecursExeption;
import com.example.managers.CollectionManager;
import com.example.managers.CommandManager;
import com.example.managers.DBCollectionManager;
import com.example.managers.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * Комманда для запуска скрипта с командами
 *
 */

public class ExecuteScriptFileName implements Command {

    public void execute(String[] args, DBCollectionManager collection, PrintWriter out, String login, String pasword) {
        Logger logger = LoggerFactory.getLogger(ExecuteScriptFileName.class);
        if (args.length > 1 || args.length == 0) {
            throw  new ArgExeption();
            // System.out.println("не правильный формат ввода");
        }
        String filename = args[0];

        if (collection.scriptIf(filename)) {
            throw new RecursExeption();
        } else {
            collection.scriptInsert(filename);
        }

        FileManager fileManager = new FileManager();

        fileManager.setFilename(filename);
        ArrayList<String> commands = null;

        try {
            commands = fileManager.commandRead();
        } catch (FileNotFoundException e) {
            out.println("имя файла неверно или файл не читаем");
            logger.info("имя файла неверно или файл не читаем");
        }

        CommandManager commandManager = new CommandManager(collection, login, pasword);

        if (commands != null) {
            for (String i : commands) {
                String[] newArgs = i.split(" ");
                commandManager.newCommand(newArgs, out);
            }
            collection.scriptRemove(filename);
        }
        out.println("Скрипт выполнен\n");
        logger.info("Скрипт выполнен");
    }

    public String getComandInfo() {
        return "execute_script file_name (string) : считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь" +
                " в интерактивном режиме.\n";

    }
}
