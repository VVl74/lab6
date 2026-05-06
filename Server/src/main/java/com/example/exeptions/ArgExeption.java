package com.example.exeptions;
/**
 * Эксепшн неверного числа аргументов
 *
 */
public class ArgExeption extends RuntimeException {
    public ArgExeption() {
        System.out.println("Ошибка: неверное число аргументов");
    }
}
