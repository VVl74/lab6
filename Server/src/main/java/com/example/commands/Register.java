package com.example.commands;

import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

public class Register implements Command {
    @Override
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        if (args.length != 2) {
            out.println("Использование: register <логин> <пароль>");
            return;
        }
        String newLogin = args[0];
        String newPassword = args[1];
        if (collectionManager.registerUser(newLogin, newPassword)) {
            out.println("Вы успешно зарегистрировались. Аккаунт: " + newLogin);
        } else {
            out.println("Не удалось зарегистрироваться. Возможно, такой логин уже занят");
        }
    }

    @Override
    public String getComandInfo() {
        return "register <логин> <пароль> : зарегистрировать нового пользователя\n";
    }
}
