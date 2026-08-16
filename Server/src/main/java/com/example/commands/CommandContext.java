package com.example.commands;

import java.io.PrintWriter;

/**
 * Данные одного запроса к команде: аргументы, куда писать ответ и кто пользователь.
 * Долгоживущие зависимости (коллекция, история) сюда не входят — они в конструкторе команды.
 */
public class CommandContext {
    private final String[] args;
    private final PrintWriter out;
    private final String login;
    private final String password;

    public CommandContext(String[] args, PrintWriter out, String login, String password) {
        this.args = args;
        this.out = out;
        this.login = login;
        this.password = password;
    }

    public String[] getArgs() {
        return args;
    }

    public PrintWriter getOut() {
        return out;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
