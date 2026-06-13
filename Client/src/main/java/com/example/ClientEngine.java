package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientEngine {
    InpOutFactory factory;
    ServerManagerInterface serverManager;
    Reader reader;
    String login;
    String password;

    public ClientEngine(ServerManagerInterface newServerManager) {
        factory = new InpOutFactory();
        serverManager = newServerManager;
        reader = new Reader();
        // login = newlogin;
        // passwordHash = newpasswordHash;
    }

    public void run() throws IOException {
        Reg nreg = new Reg(reader);
        String[] user = nreg.autorize();
        login = user[0];
        password = user[1];
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null) {
                    continue;
                }
                processing(input, login, password);
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
