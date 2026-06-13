package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientEngine {
    InpOutFactory factory;
    ServerManagerInterface serverManager;
    Reader reader;
    String login;
    String passwordHash;

    public ClientEngine(ServerManagerInterface newServerManager, String newlogin, String newpasswordHash) {
        factory = new InpOutFactory();
        serverManager = newServerManager;
        reader = new Reader();
        // login = newlogin;
        // passwordHash = newpasswordHash;
    }

    public void run() throws IOException {
        System.out.print("Enter your login: ");
        reader.readLine();

        System.out.print("Enter your password: ");
        reader.readLine();
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null) {
                    continue;
                }
                processing(input, login, passwordHash);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processing(String input, String login, String passwordHash) throws IOException {
        ByteBuffer sendBuf = factory.OutputFactory(input, login, passwordHash);
        try {
            serverManager.send(sendBuf);

            ByteBuffer vvodBuf = ByteBuffer.allocate(16384);

            vvodBuf = serverManager.receive(vvodBuf);

            String itog  = factory.InputFactory(vvodBuf);

            System.out.println(itog);
        } catch (Exception e) {
            System.out.println("Ошибка, команда не выполнена "  + e.getMessage());
        }
    }
}
