package com.example.exeptions;

import java.io.PrintWriter;

/**
 * Эксепшн когда неверный тип данных ввода
 *
 */
public class InputExeption extends RuntimeException {
    public InputExeption(String message, PrintWriter out) {
        out.println("Ошибка: Неверный тип данных ввода " + message);
    }
}
