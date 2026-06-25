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
        // Считываем адрес сервера первой строкой ввода (например localhost:1111)
        Reader reader = new Reader();
        System.out.println("Введите адрес сервера в формате host:port (например localhost:1111):");

        String host = null;
        int port = 0;
        // Читаем строку, пока не получим корректный адрес
        while (host == null) {
            String line = reader.readLine();
            if (line == null) {
                return;
            }
            String[] parts = line.trim().split(":");
            if (parts.length != 2) {
                System.out.println("Неверный формат, введите host:port");
                continue;
            }
            try {
                host = parts[0];
                port = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Порт должен быть числом, введите host:port");
                host = null;
            }
        }

        ServerManager channel = new ServerManager(host, port);

         ServerManagerInterface retryChannel = new RetryDecor(channel, 5);


        ClientEngine clientEngine = new ClientEngine(retryChannel);

        clientEngine.run();
    }
}

