package com.example.managers;

import com.example.commands.*;

import com.example.exeptions.ArgExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Ключевой класс приложения отвечающий за работу с командами
 * <p>
 *  Функции класса
 *  <ul>
 *      <li> Хранение списка команд </li>
 *      <li> Валидирование и вызов нужных команд </li>
 *      <li> Обработка ошибок </li>
 *      <li> Хранение истории команд </li>
 * </ul>
 */
public class CommandManager {
    Logger logger = LoggerFactory.getLogger(CommandManager.class);
    public HashMap<String, Command> commandHashMap = new HashMap<>();
    public DBCollectionManager collectionManager;
    ConcurrentLinkedDeque<String> commandHistory = new ConcurrentLinkedDeque<String>();

    public CommandManager(DBCollectionManager newCollectionManager) {
        collectionManager = newCollectionManager;
        commandHashMap.put("register", new Register(collectionManager));
        commandHashMap.put("login", new Login(collectionManager));
        commandHashMap.put("help", new Help(commandHashMap));
        commandHashMap.put("info", new Info(collectionManager));
        commandHashMap.put("history", new History(commandHistory));
        commandHashMap.put("exit", new Exit());
        commandHashMap.put("clear", new Clear(collectionManager));
        commandHashMap.put("show", new Show(collectionManager));
        commandHashMap.put("insert", new Insert(collectionManager));
        commandHashMap.put("update", new UpdateId(collectionManager));
        commandHashMap.put("remove_key", new RemoveKey(collectionManager));
        commandHashMap.put("execute_script", new ExecuteScriptFileName(this));
        commandHashMap.put("replace_if_lowe", new ReplaceIfLower(collectionManager));
        commandHashMap.put("remove_lower_key", new RemoveLowerKey(collectionManager));
        commandHashMap.put("count_less_than_health", new CountLessThanHealth(collectionManager));
        commandHashMap.put("filter_less_than_chapter", new FilterLessThanChapter(collectionManager));
        commandHashMap.put("filter_greater_than_chapter", new FilterGreaterThanChapter(collectionManager));
    }

    public DBCollectionManager getCollectionManager() {
        return collectionManager;
    }

    public void newCommand(String[] args, PrintWriter out, String login, String password) {
        String com = args[0];
        Command command = commandHashMap.get(com);

        if (command == null) {
            logger.info("неизвестная команда");
            out.println("неизвестная команда");
            return;
        }

        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (command.needsAuth()
                && !collectionManager.proverkUser(login, password)
                && !(login.equals("admin") && password.equals("admin"))) {
            out.println("Ошибка, неверный логин или пароль");
            return;
        }

        try {
            logger.info("команда выполняется");
            command.execute(new CommandContext(commandArgs, out, login, password));
            commandHistory.add(com);
        } catch (ArgExeption e) {
            logger.info("ошибка, команда не выполнена");
            out.println("Ошибка, команда не выполнена");
        }
    }
}
