package com.example.exeptions;
/**
 * Эксепшн для случаев когда команда по каким то причинам не выполнилась
 *
 */
public class CommandExeption extends RuntimeException {
    public CommandExeption() {

        System.out.println("Ошибка: команда не выполнена");
    }
}
