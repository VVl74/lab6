package com.example.commands;

import com.example.managers.DBCollectionManager;

public class Login implements Command {
    private final DBCollectionManager collectionManager;

    public Login(DBCollectionManager collectionManager) {
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
            ctx.getOut().println("Использование: login <логин> <пароль>");
            return;
        }
        String userLogin = args[0];
        String userPassword = args[1];
        if (collectionManager.proverkUser(userLogin, userPassword)) {
            ctx.getOut().println("Вы успешно вошли в аккаунт " + userLogin);
        } else {
            ctx.getOut().println("Неверный логин или пароль");
        }
    }

    @Override
    public String getComandInfo() {
        return "login <логин> <пароль> : войти в существующий аккаунт\n";
    }
}
