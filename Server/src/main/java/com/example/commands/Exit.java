package com.example.commands;

/**
 * Комманда для выхода из программы
 */
public class Exit implements Command {
    @Override
    public void execute(CommandContext ctx) {
        System.exit(0);
    }

    @Override
    public String getComandInfo() {
        return "exit : завершить программу (без сохранения в файл)\n";
    }
}
