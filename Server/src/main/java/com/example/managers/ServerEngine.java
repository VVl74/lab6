package com.example.managers;

import com.example.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerEngine {
    Logger logger = LoggerFactory.getLogger(ServerEngine.class);
    CommandManager commandManager;
    ServerManager servChannel;
    InputManager inputManager;
    PackFactory packFactory;
    Deserializer deserializer;
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
                InputPack pack;
                pack = servChannel.receive();

                if (pack.client != null) {
                    logger.info("запрос получен");
                    processing(pack);
                }

                inputManager.inputTerm(commandManager);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processing(InputPack pack) throws IOException {
        ParsedRequest parsedRequest = deserializer.deserialize(pack);
        String[] parts = parsedRequest.getParts();

        if (parts == null) {
            return;
        }

        OutputPack outPack = packFactory.BuildPack(pack.client, commandManager, parts);

        servChannel.send(outPack);

        logger.info("Ответ отправлен");
    }
}
