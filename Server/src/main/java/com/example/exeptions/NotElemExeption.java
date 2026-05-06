package com.example.exeptions;
/**
 * Эксепшн когда нет элемента с таким ID
 *
 */
public class NotElemExeption extends RuntimeException {
    public NotElemExeption() {
        System.out.println("Ошибка: нет элемента с таким ID");
    }
}
