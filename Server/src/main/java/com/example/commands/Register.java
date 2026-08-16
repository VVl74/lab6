package com.example.commands;

import com.example.managers.DBCollectionManager;

public class Register implements Command {
    private final DBCollectionManager collectionManager;

    public Register(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean needsAuth() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx) {
        String[] args = ctx.getArgs();
        if (args.length != 2) {
            ctx.getOut().println("Использование: register <логин> <пароль>");
            return;
        }
        String newLogin = args[0];
        String newPassword = args[1];
        if (collectionManager.registerUser(newLogin, newPassword)) {
            ctx.getOut().println("Вы успешно зарегистрировались. Аккаунт: " + newLogin);
        } else {
            ctx.getOut().println("Не удалось зарегистрироваться. Возможно, такой логин уже занят");
        }
    }

    @Override
    public String getComandInfo() {
        return "register <логин> <пароль> : зарегистрировать нового пользователя\n";
    }
}
