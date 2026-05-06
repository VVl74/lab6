package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientEngine {
    InpOutFactory factory;
    ServerManager serverManager;
    Reader reader;

    public ClientEngine(ServerManager newServerManager) {
        factory = new InpOutFactory();
        serverManager = newServerManager;
        reader = new Reader();
    }

    public void run() throws IOException {
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null) {
                    continue;
                }
                processing(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processing(String input) throws IOException {
        ByteBuffer sendBuf = factory.OutputFactory(input);
        try {
            serverManager.send(sendBuf);

            ByteBuffer vvodBuf = ByteBuffer.allocate(16384);

            vvodBuf = serverManager.recive(vvodBuf);

            String itog  = factory.InputFactory(vvodBuf);

            System.out.println(itog);
        } catch (Exception e) {
            System.out.println("Ошибка, команда не выполнена "  + e.getMessage());
        }
    }
}
