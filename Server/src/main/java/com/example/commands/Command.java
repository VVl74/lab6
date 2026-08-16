package com.example.commands;

/**
 * Интерфейс команды. Зависимости передаются в конструктор,
 * данные текущего запроса — в {@link CommandContext}.
 */
public interface Command {
    void execute(CommandContext ctx);
    String getComandInfo();

    default boolean needsAuth() {
        return true;
    }
}
