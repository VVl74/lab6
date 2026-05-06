package com.example.exeptions;
/**
 * Эксепшн когда в скрипте образовалась бесконечная рекурсия
 *
 */
public class RecursExeption extends RuntimeException {
    public RecursExeption() {
        System.out.println("Ошибка: бесконечная рекурсия чтения скриптов");
    }
}
