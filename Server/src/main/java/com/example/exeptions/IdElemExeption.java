package com.example.exeptions;
/**
 * Эксепшн когда ID занят и элемент не удалось вставить
 *
 */
public class IdElemExeption extends RuntimeException {
    public IdElemExeption() {
        System.out.println("Ошибка: ID уже занят");
    }
}
