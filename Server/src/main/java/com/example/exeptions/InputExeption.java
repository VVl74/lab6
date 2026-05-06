package com.example.exeptions;
/**
 * Эксепшн когда неверный тип данных ввода
 *
 */
public class InputExeption extends RuntimeException {
    public InputExeption() {
        System.out.println("Ошибка: Неверный тип данных ввода");
    }
}
