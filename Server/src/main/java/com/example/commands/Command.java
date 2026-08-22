package com.example.commands;

/**
 * Интерфейс для всех команд для комманд паттерна
 *
 */
public interface Command {
    void execute(CommandContext ctx);
    String getComandInfo();

    default boolean needsAuth() {
        return true;
    }
}
