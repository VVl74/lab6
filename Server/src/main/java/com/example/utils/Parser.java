package com.example.utils;

import com.example.collection.*;
import com.example.exeptions.FileExeption;
import com.example.exeptions.InputExeption;

import java.time.LocalDateTime;
/**
 * Класс по сути билдер более сложных штук
 */
public class Parser {
    private static int id = 1;
    /**
     * Начало парсера
     *  <ol>
     *      <li> получаем массив с данными для SpaceMarine </li>
     *      <li> считываем и валидируем их </li>
     *      <li> возвращаем собранный элемент </li>
     *  </ol>
     */
    public SpaceMarine parsSpaceMarine(String[] args) {
        try {
            int nid = Integer.parseInt(args[0]);
            String name = args[1];
            float x = Float.parseFloat(args[2]);
            Long y = (long) Integer.parseInt(args[3]);

            Coordinates cord = new Coordinates(x, y);

            LocalDateTime ndate = LocalDateTime.now();

            double health = (double) Float.parseFloat(args[4]);

            AstartesCategory nc = AstartesCategory.valueOf(args[5]);

            Weapon nweapon = Weapon.valueOf(args[6]);

            MeleeWeapon nmeleeweapon = MeleeWeapon.valueOf(args[7]);

            String nchaptername = args[8];
            String nparentlegion = args[9];

            Long marinescount = (long) Integer.parseInt(args[10]);

            String world = args[11];

            Chapter marinchapt = new Chapter(nchaptername, nparentlegion, marinescount, world);

            SpaceMarine spacemar = new SpaceMarine(nid, name, cord, ndate, health, nc, nweapon, nmeleeweapon, marinchapt);
            return spacemar;
        } catch (Exception e) {
            throw new InputExeption();
        }
    }
    /**
     * Начало парсера
     *  <ol>
     *      <li> получаем массив с данными для Chapter </li>
     *      <li> считываем и валидируем их </li>
     *      <li> возвращаем собранный элемент </li>
     *  </ol>
     */
    public Chapter parseChapter(String[] args) {
        try {
            String s1 = args[0];
            String s2 = args[1];
            Long chislen = (long) Integer.parseInt(args[2]);
            String s3 = args[3];

            Chapter nchapt = new Chapter(s1, s2, chislen, s3);

            return nchapt;

        } catch(Exception e) {
            throw new InputExeption();
        }
    }

    public SpaceMarine parseCSV(String str) {
        try {
            String[] elem = str.split(";");

            if (elem.length != 11) {
                throw new FileExeption("неверное число аргументов");
            }

            for (int i=0; i < elem.length; i++) {
                elem[i] = elem[i].trim();
            }

            int nid = id;
            String name = elem[0];

            if (name.isEmpty()) {
                throw new FileExeption("имя не может быть пустым");
            }

            float x = Float.parseFloat(elem[1]);
            Long y = (long) Integer.parseInt(elem[2]);

            Coordinates cord = new Coordinates(x, y);

            LocalDateTime ndate = LocalDateTime.now();

            double health = (double) Float.parseFloat(elem[3]);

            if (Double.isNaN(health) || health < 0) {
                throw new FileExeption("здоровье не может быть NaN или < 0");
            }

            AstartesCategory nc = AstartesCategory.valueOf(elem[4].toUpperCase());

            Weapon nweapon = Weapon.valueOf(elem[5].toUpperCase());

            MeleeWeapon nmeleeweapon = MeleeWeapon.valueOf(elem[6].toUpperCase());

            String nchaptername = elem[7];
            String nparentlegion = elem[8];


            Long marinescount = (long) Integer.parseInt(elem[9]);

            if (marinescount <= 0 || marinescount > 1000) {
                throw new FileExeption("неверныая численность");
            }

            String world = elem[10];

            Chapter marinchapt = new Chapter(nchaptername, nparentlegion, marinescount, world);

            SpaceMarine spacemar = new SpaceMarine(nid, name, cord, ndate, health, nc, nweapon, nmeleeweapon, marinchapt);

            id++;
            return spacemar;
        } catch (Exception e) {
            throw new InputExeption();
        }
    }
}
