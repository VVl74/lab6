package com.example.commands;

import com.example.managers.DBCollectionManager;

import java.io.PrintWriter;

public class Register implements Command {
    @Override
    public void execute(String[] args, DBCollectionManager collectionManager, PrintWriter out, String login, String pasword) {
        collectionManager.registerUser(login, pasword);
        out.println("Пользователь зарегистрирован");
    }

    @Override
    public String getComandInfo() {
        return "Команда для регистрации пользователя, введите через пробел логи и пароль \n";
    }
}
