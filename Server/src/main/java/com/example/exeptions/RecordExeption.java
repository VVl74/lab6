package com.example.exeptions;
/**
 * Эксепшн когда не получилось записать данные в файл
 *
 */
public class RecordExeption extends RuntimeException {
    public RecordExeption() {
        System.out.println("Ошибка: запись не удалась");
    }
}
