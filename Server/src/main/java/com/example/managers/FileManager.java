package com.example.managers;

import com.example.collection.SpaceMarine;
import com.example.exeptions.ReadExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.utils.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Класс для работы с файломи
 * <p>
 *  Функции класса
 *  <ul>
 *      <li> Чтение данных из файла
 *      <li> Собирание данных из файлов в коллекцию
 *      <li> чтение команд из файла
 * </ul>
 */
public class FileManager {
    Logger logger = LoggerFactory.getLogger(FileManager.class);
    private static int id = 1;
    public String filename;
    Parser parser = new Parser();

    public void setFilename(String name) {
        filename = name;
    }


    /**
     * Читатель изначальной коллекции
     *  <ol>
     *      <li> читаем файл с данными </li>
     *      <li> собираем их в спейсмаринов </li>
     *      <li> собираем спейсмаринов в мапу </li>
     *      <li> возвращаем мапу спейсмаринов </li>
     *  </ol>
     */
    public HashMap<Integer, SpaceMarine> fileRead() throws FileNotFoundException {
        HashMap <Integer, SpaceMarine> spaceMarineHashMap = new HashMap<>();

        try (InputStreamReader bufreader = new InputStreamReader(new FileInputStream(filename))) {
            BufferedReader reader = new BufferedReader(bufreader);

            String input;

            while (true) {
                input = reader.readLine();

                if (input == null) {
                    break;
                }
                SpaceMarine newmarine;

                try {
                    newmarine = parser.parseCSV(input);
                } catch (Exception e) {
                    System.out.println("Считать строку не удалось");
                    continue;
                }

                spaceMarineHashMap.put(newmarine.getId(), newmarine);
                id++;
            }
        } catch(IOException exep) {
            throw new ReadExeption(exep.getMessage());
            // System.out.println("с файлом что то не то " + exep.getMessage());
        }

        return spaceMarineHashMap;
    }

    /**
     * Читатель команд
     *  <ol>
     *      <li> читаем файл с командами </li>
     *      <li> закидываем команду в массив </li>
     *      <li> возвращаем массив команд </li>
     *  </ol>
     */

    public ArrayList<String> commandRead() throws FileNotFoundException {
        ArrayList<String> args = new ArrayList<>();
        try (InputStreamReader bufreader = new InputStreamReader(new FileInputStream(filename))) {
            BufferedReader reader = new BufferedReader(bufreader);

            String input;


            while(true) {
                input = reader.readLine();

                if (input == null) {
                    break;
                }
                args.add(input);
            }
        } catch(IOException exep) {
            System.out.println("с файлом что то не то " + exep.getMessage());
        }

        return args;
    }
}
