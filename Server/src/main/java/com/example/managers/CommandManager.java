package com.example.managers;

import com.example.commands.*;

import com.example.exeptions.ArgExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    ArrayList<String> commandHistory = new ArrayList<String>();

    String login;
    String password;

    public CommandManager(DBCollectionManager newCollectionManager, String nlogin, String npasswordHash) {
        collectionManager = newCollectionManager;
        commandHashMap.put("help", new Help(commandHashMap));
        commandHashMap.put("info", new Info());
        commandHashMap.put("history", new History(commandHistory));
        commandHashMap.put("exit", new Exit(commandHashMap));
        commandHashMap.put("clear", new Clear());
        commandHashMap.put("show", new Show());
        commandHashMap.put("insert", new Insert());
        commandHashMap.put("update", new UpdateId());
        commandHashMap.put("remove_key", new RemoveKey());
        commandHashMap.put("execute_script", new ExecuteScriptFileName());
        // commandHashMap.put("save", new Save());
        commandHashMap.put("replace_if_lowe", new ReplaceIfLower());
        commandHashMap.put("remove_lower_key", new RemoveLowerKey());
        commandHashMap.put("count_less_than_health", new CountLessThanHealth());
        commandHashMap.put("filter_less_than_chapter", new FilterLessThanChapter());
        commandHashMap.put("filter_greater_than_chapter", new FilterGreaterThanChapter());

        login = nlogin;
        password = npasswordHash;
    }

    /**
     * Метод непосредственно обрабатывающий полученную команду
     * <p>
     *  Функции класса
     *  <ul>
     *      <li> Получение строки с командой и ее аргументами </li>
     *      <li> выделение команды и аргументов </li>
     *      <li> Валидирование команды  </li>
     *      <li> Вызов полученной команды </li>
     *      <li> Добавление вызванной команды в историю </li>
     * </ul>
     */
    public void newCommand(String[] args, PrintWriter out) {
        PrintStream old =System.out;

        String com = args[0];

        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (commandHashMap.containsKey(com)) {
            try {
                logger.info("команда выполняется");
                if (com == "register") {
                    commandHashMap.get(com).execute(commandArgs, collectionManager, out, login, password);
                    return;
                }
                if (collectionManager.proverkUser(login, password)) {
                    commandHashMap.get(com).execute(commandArgs, collectionManager, out, login, password);
                    return;
                } else {
                    out.println("Ошибка, неверный логин или пароль");
                }
            } catch (ArgExeption e) {
                logger.info("ошибка, команда не выполнена");
                out.println("Ошибка, команда не выполнена");
            }
            commandHistory.add(com);
        } else {
            logger.info("неизвестная команда");
            out.println("неизвестная команда");
        }
    }
}
