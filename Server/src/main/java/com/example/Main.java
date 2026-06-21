package com.example;

import com.example.managers.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Главный класс приложения отвечающий за запуск сервера
 * <p>
 *  Функции класса
 *  <ul>
 *      <li> Подключение к базе данных </li>
 *      <li> Создание коллекшн менеджера и команд менеджера </li>
 *      <li> Запуск сервера для обработки запросов клиентов </li>
 * </ul>
 */
public class Main {
    /**
     * Начало программы
     *  <ol>
     *      <li> Подключаемся к базе данных </li>
     *      <li> Создаем коллекшн менеджер и команд менеджер </li>
     *      <li> Загружаем коллекцию из БД в оперативную память </li>
     *      <li> Запускаем сервер </li>
     *  </ol>
     */
    public static void main(String[] args) throws IOException, SQLException {
        DBManager dbManager = new DBManager();
        dbManager.connect();

        DBCollectionManager collectionManager = new DBCollectionManager(dbManager);
        collectionManager.InitTable();
        collectionManager.loadCollection();

        CommandManager curCommandManager = new CommandManager(collectionManager);

        ServerManager servChannel = new ServerManager(12345);

        InputManager inputManager = new InputManager();

        ServerEngine servEng = new ServerEngine(curCommandManager, servChannel, inputManager);

        servEng.run();
    }
}
