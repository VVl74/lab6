package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientEngine {
    InpOutFactory factory;
    ServerManagerInterface serverManager;
    Reader reader;
    ElementPrompter prompter;
    String login;
    String password;

    public ClientEngine(ServerManagerInterface newServerManager) {
        factory = new InpOutFactory();
        serverManager = newServerManager;
        reader = new Reader();
        prompter = new ElementPrompter(reader);
    }

    public void run() throws IOException {
        authorize();
        System.out.println("Можно вводить команды. Наберите help для списка.");
        while (true) {
            try {
                String input = reader.readLine();

                if (input == null) {
                    continue;
                }
                input = input.trim();
                if (input.isEmpty()) {
                    continue;
                }
                input = completeIfNeeded(input);
                processing(input, login, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void authorize() {
        System.out.println("Добро пожаловать.");
        System.out.println("Если вы новый пользователь, введите: register <логин> <пароль>");
        System.out.println("Если у вас уже есть аккаунт, введите: login <логин> <пароль>");

        while (true) {
            try {
                String input = reader.readLine();
                if (input == null) {
                    continue;
                }
                input = input.trim();
                if (input.isEmpty()) {
                    continue;
                }

                String[] parts = input.split(" ");
                String cmd = parts[0];

                if (!cmd.equals("register") && !cmd.equals("login")) {
                    System.out.println("Сначала войдите или зарегистрируйтесь.");
                    System.out.println("Новый пользователь: register <логин> <пароль>");
                    System.out.println("Существующий аккаунт: login <логин> <пароль>");
                    continue;
                }

                if (parts.length != 3) {
                    System.out.println("Использование: " + cmd + " <логин> <пароль>");
                    continue;
                }

                String tryLogin = parts[1];
                String tryPassword = parts[2];
                String response = processing(input, tryLogin, tryPassword);
                if (response != null && response.contains("Вы успешно")) {
                    login = tryLogin;
                    password = tryPassword;
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String completeIfNeeded(String input) {
        String[] parts = input.split(" ");
        String cmd = parts[0];
        int argc = parts.length - 1;

        if ((cmd.equals("insert") || cmd.equals("update") || cmd.equals("replace_if_lowe")) && argc != 12) {
            return cmd + " " + String.join(" ", prompter.readMarine());
        }
        if ((cmd.equals("filter_less_than_chapter") || cmd.equals("filter_greater_than_chapter")) && argc != 4) {
            return cmd + " " + String.join(" ", prompter.readChapter());
        }
        return input;
    }

    private String processing(String input, String login, String passwordHash) throws IOException {
        ByteBuffer sendBuf = factory.OutputFactory(input, login, passwordHash);
        try {
            System.out.println("[ОТПРАВКА] Отправляем команду '" + input + "' (login: " + login + ")");
            serverManager.send(sendBuf);

            ByteBuffer vvodBuf = ByteBuffer.allocate(16384);

            vvodBuf = serverManager.receive(vvodBuf);

            String itog  = factory.InputFactory(vvodBuf);

            System.out.println(itog);
            return itog;
        } catch (Exception e) {
            System.out.println("Ошибка, команда не выполнена "  + e.getMessage());
            return null;
        }
    }
}
