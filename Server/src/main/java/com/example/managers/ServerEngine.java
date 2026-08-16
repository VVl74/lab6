package com.example.managers;

import com.example.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerEngine {
    Logger logger = LoggerFactory.getLogger(ServerEngine.class);
    CommandManager commandManager;
    ServerManager servChannel;
    InputManager inputManager;
    PackFactory packFactory;
    Deserializer deserializer;

    private final ExecutorService processingPool = Executors.newFixedThreadPool(10);
    private final ForkJoinPool readPool = new ForkJoinPool(); // чтение запросов
    private final ForkJoinPool sendPool = new ForkJoinPool(); // отправка ответов

    public ServerEngine(CommandManager newCommandManager, ServerManager newServerManager, InputManager newInputManager) {
        commandManager = newCommandManager;
        servChannel = newServerManager;
        inputManager = newInputManager;
        packFactory = new PackFactory(commandManager);
        deserializer = new Deserializer();
    }

    public void run() throws IOException {
        logger.info("Сервер запущен");
        while (true) {
            try {
                InputPack pack = readPool.submit(() -> servChannel.receive()).get();   // тут мы читаем просто запрос от клиента (ждем какой-то InputPack)

                while (pack.client != null) {
                    logger.info("Запрос от {} принят в обработку", pack.client);
                    final InputPack current = pack;
                    processingPool.execute(() -> processing(current));

                    pack = readPool.submit(() -> servChannel.receive()).get();
                }

                inputManager.inputTerm(commandManager);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processing(InputPack pack) {
        /**
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

            logger.info("Команда '{}' от {} (login: {}) обработана, готовлю ответ", parts.length > 0 ? parts[0] : "?", pack.client, login);
            OutputPack outPack = packFactory.buildPack(pack.client, parsedRequest);

            sendPool.execute(() -> { // эта штука .execute просто запустит лямбду в фоновом режиме (т.е просто отправит на сервак наш пакет)
                try {
                    servChannel.send(outPack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Ответ клиенту {} отправлен", pack.client);

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
