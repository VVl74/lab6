package com.example;

import java.io.IOException;

/**
 * Главный класс приложения отвечающий за запуск работы с коллекциями
 * <p>
 *  Функции класса
 *  <ul>
 *      <li> Чтение коллекции с помощью файл менеджера </li>
 *      <li> Создание коллекшн менеджера и запуск работы с коллекцией </li>
 *      <li> Обработка пользовательских комманд </li>
 *      <li> Обработка некоторых ошибок </li>
 * </ul>
 */
public class Client {
    /**
     * Начало программы
     *  <ol>
     *      <li> Получаем имя файла </li>
     *      <li> Считываем коллекцию </li>
     *      <li> Создаем коллекшн менеджер и команд менеджер </li>
     *      <li> Запускаем режим интерактивной работы с коллекцией </li>
     *  </ol>
     */
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 44231;

        ServerManager channel = new ServerManager(host, port);

         ServerManagerInterface retryChannel = new RetryDecor(channel, 5);


        ClientEngine clientEngine = new ClientEngine(retryChannel);

        clientEngine.run();
    }
}

