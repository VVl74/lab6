package com.example.exeptions;
/**
 * Эксепшн когда не удалось считать что то из файла
 *
 */
public class FileExeption extends RuntimeException {
    public FileExeption(String message) {
        super("Ошибка, неверный формат ввода: " + message);
    }
}
