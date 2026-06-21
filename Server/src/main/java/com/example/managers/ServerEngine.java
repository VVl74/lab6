package com.example.managers;

import com.example.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class ServerEngine {
    Logger logger = LoggerFactory.getLogger(ServerEngine.class);
    CommandManager commandManager;
    ServerManager servChannel;
    InputManager inputManager;
    PackFactory packFactory;
    Deserializer deserializer;

    private final ForkJoinPool readPool = new ForkJoinPool(); // чтение запросов
    private final ForkJoinPool sendPool = new ForkJoinPool(); // отправка ответов

    public ServerEngine(CommandManager newCommandManager, ServerManager newServerManager, InputManager newInputManager) {
        commandManager = newCommandManager;
        servChannel = newServerManager;
        inputManager = newInputManager;
        packFactory = new PackFactory();
        deserializer = new Deserializer();
    }

    public void run() throws IOException {
        logger.info("Сервер запущен");
        while (true) {
            try {
                InputPack pack = readPool.submit(() -> servChannel.receive()).get(); //чтение запроса
                // тут мы читаем просто запрос от клиента (ждем какой-то InputPack)

                while (pack.client != null) {
                    logger.info("запрос получен");
                    final InputPack current = pack;
                    new Thread(() -> processing(current)).start(); // выполнение запросов
                    pack = readPool.submit(() -> servChannel.receive()).get(); // чтение накопившихся запросов
                }

                inputManager.inputTerm(commandManager);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processing(InputPack pack) {
        /**
         * Метод для обработки одного запроса в отдельном потоке
         * т.е создаем новый поток, куда мы передаем лямбду
         * new Thread(() -> processing(сюда запрос).start()) -- обработка запроса
         */
        try {
            ParsedRequest parsedRequest = deserializer.deserialize(pack);

            if (parsedRequest == null) {
                return;
            }

            String[] parts = parsedRequest.getParts();
            String login = parsedRequest.getLogin();
            String password = parsedRequest.getPassword();

            if (parts == null) {
                return;
            }

            OutputPack outPack = packFactory.BuildPack(pack.client, commandManager, parts, login, password);

            sendPool.execute(() -> { // эта штука .execute просто запустит лямбду в фоновом режиме (т.е просто отправит на сервак наш пакет)
                try {
                    servChannel.send(outPack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Ответ отправлен");

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
