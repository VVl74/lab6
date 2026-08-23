package com.example.utils;

import com.example.FieldParser;
import com.example.collection.*;
import com.example.exeptions.FileExeption;
import com.example.exeptions.InputExeption;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
/**
 * Класс по сути билдер более сложных штук
 */
public class Parser {
    private static int id = 1;
    private PrintWriter out;
    /**
     * Начало парсера
     *  <ol>
     *      <li> получаем массив с данными для SpaceMarine </li>
     *      <li> считываем и валидируем их </li>
     *      <li> возвращаем собранный элемент </li>
     *  </ol>
     */

    public Parser(PrintWriter out) {
        this.out = out;
    }

    public Parser() {}
    // public Parser() {}

    public SpaceMarine parsSpaceMarine(String[] args, int ownerid) {
        try {
            int nid = FieldParser.parseId(args[0]);
            String name = FieldParser.parseName(args[1]);
            float x = FieldParser.parseX(args[2]);
            Long y = FieldParser.parseY(args[3]);
            Coordinates cord = new Coordinates(x, y);
            LocalDateTime ndate = LocalDateTime.now();
            double health = FieldParser.parseHealth(args[4]);
            AstartesCategory nc = FieldParser.parseCategory(args[5]);
            Weapon nweapon = FieldParser.parseWeapon(args[6]);
            MeleeWeapon nmeleeweapon = FieldParser.parseMeleeWeapon(args[7]);
            String nchaptername = FieldParser.parseChapterName(args[8]);
            String nparentlegion = FieldParser.parseParentLegion(args[9]);
            Long marinescount = FieldParser.parseMarinesCount(args[10]);
            String world = FieldParser.parseWorld(args[11]);
            Chapter marinchapt = new Chapter(nchaptername, nparentlegion, marinescount, world);
            return new SpaceMarine.Builder()
                    .id(nid)
                    .name(name)
                    .coordinates(cord)
                    .creationDate(ndate)
                    .health(health)
                    .category(nc)
                    .weapon(nweapon)
                    .meleeWeapon(nmeleeweapon)
                    .chapter(marinchapt)
                    .ownerId(ownerid)
                    .build();
        } catch (Exception e) {
            throw new InputExeption(e.getMessage(), out);
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
            String s1 = FieldParser.parseChapterName(args[0]);
            String s2 = FieldParser.parseParentLegion(args[1]);
            Long chislen = FieldParser.parseMarinesCount(args[2]);
            String s3 = FieldParser.parseWorld(args[3]);
            return new Chapter(s1, s2, chislen, s3);
        } catch (Exception e) {
            throw new InputExeption(e.getMessage(), out);
        }
    }

    public SpaceMarine parseSQLMarine(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int ownerId = rs.getInt("owner_id");
        String name = rs.getString("name");
        int x = rs.getInt("X");
        long y = rs.getLong("Y");
        Coordinates coords = new Coordinates((float) x, y);
        LocalDateTime creationDate = rs.getTimestamp("datetime").toLocalDateTime();
        double health = rs.getDouble("health");
        AstartesCategory category = AstartesCategory.valueOf(rs.getString("category"));
        String weaponStr = rs.getString("weapontype");
        Weapon weapon = weaponStr != null ? Weapon.valueOf(weaponStr) : null;
        MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(rs.getString("meleeweapon"));
        String chapterName = rs.getString("chapter_name");
        String parentLegion = rs.getString("chapter_parent_legion");
        long marinesCount = rs.getLong("chapter_marines_count");
        String world = rs.getString("chapter_world");
        Chapter chapter = new Chapter(chapterName, parentLegion, marinesCount, world);
        SpaceMarine marine = new SpaceMarine.Builder()
                .id(id)
                .name(name)
                .coordinates(coords)
                .creationDate(creationDate)
                .health(health)
                .category(category)
                .weapon(weapon)
                .meleeWeapon(meleeWeapon)
                .chapter(chapter)
                .ownerId(ownerId)
                .build();
        marine.setOwnerName(rs.getString("owner_name"));
        return marine;
    }
/*
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
 */
}
