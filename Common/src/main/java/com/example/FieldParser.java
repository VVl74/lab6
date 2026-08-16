package com.example;

import java.util.Set;

public class FieldParser {
    public static final Set<String> CATEGORIES = Set.of("ASSAULT", "TACTICAL", "HELIX");
    public static final Set<String> WEAPONS = Set.of("BOLTGUN", "MELTAGUN", "FLAMER", "HEAVY_FLAMER");
    public static final Set<String> MELEE_WEAPONS = Set.of(
            "CHAIN_SWORD", "POWER_SWORD", "CHAIN_AXE", "MANREAPER", "POWER_FIST");

    public static int parseId(String raw) {
        int id = parseInt(raw, "id");
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть больше 0");
        }
        return id;
    }

    public static String parseName(String raw) {
        return parseNonEmpty(raw, "имя");
    }

    public static float parseX(String raw) {
        float x = parseFloat(raw, "координата X");
        if (x <= 0 || x > Float.MAX_VALUE) {
            throw new IllegalArgumentException("слишком большой или отрицательный x");
        }
        return x;
    }

    public static long parseY(String raw) {
        long y = parseLong(raw, "координата Y");
        if (y <= 0) {
            throw new IllegalArgumentException("слишком большой или отрицательный y");
        }
        if (y > 759) {
            throw new IllegalArgumentException("координата Y не может быть больше 759");
        }
        return y;
    }

    public static double parseHealth(String raw) {
        double health = parseDouble(raw, "здоровье");
        if (health <= 0 || Double.isNaN(health)) {
            throw new IllegalArgumentException("здоровье должно быть больше 0");
        }
        return health;
    }

    public static String parseCategory(String raw) {
        return parseEnum(raw, CATEGORIES, "категория");
    }

    public static String parseWeapon(String raw) {
        return parseEnum(raw, WEAPONS, "основное оружие");
    }

    public static String parseMeleeWeapon(String raw) {
        return parseEnum(raw, MELEE_WEAPONS, "оружие ближнего боя");
    }

    public static String parseChapterName(String raw) {
        return parseNonEmpty(raw, "название клана");
    }

    public static String parseParentLegion(String raw) {
        return parseNonEmpty(raw, "имя командира легиона");
    }

    public static long parseMarinesCount(String raw) {
        long count = parseLong(raw, "количество космодесантников");
        if (count <= 0 || count > 1000) {
            throw new IllegalArgumentException("численность должна быть от 1 до 1000");
        }
        return count;
    }

    public static String parseWorld(String raw) {
        return parseNonEmpty(raw, "название планеты");
    }

    public static String toArg(int value) {
        return Integer.toString(value);
    }

    public static String toArg(long value) {
        return Long.toString(value);
    }

    public static String toArg(float value) {
        return Float.toString(value);
    }

    public static String toArg(double value) {
        return Double.toString(value);
    }

    private static String parseNonEmpty(String raw, String field) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        String value = raw.trim();
        if (value.contains(" ")) {
            throw new IllegalArgumentException(field + " не должно содержать пробелов");
        }
        return value;
    }

    private static String parseEnum(String raw, Set<String> allowed, String field) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        String value = raw.trim().toUpperCase();
        if (!allowed.contains(value)) {
            throw new IllegalArgumentException(field + " должно быть одним из: " + String.join(", ", allowed));
        }
        return value;
    }

    private static int parseInt(String raw, String field) {
        try {
            double asDouble = Double.parseDouble(raw.trim());
            if (asDouble > Integer.MAX_VALUE || asDouble != Math.rint(asDouble)) {
                throw new NumberFormatException();
            }
            return Integer.parseInt(raw.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("неверный формат поля " + field);
        }
    }

    private static long parseLong(String raw, String field) {
        try {
            double asDouble = Double.parseDouble(raw.trim());
            if (asDouble > Long.MAX_VALUE) {
                throw new NumberFormatException();
            }
            return Long.parseLong(raw.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("неверный формат поля " + field);
        }
    }

    private static float parseFloat(String raw, String field) {
        try {
            return Float.parseFloat(raw.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("неверный формат поля " + field);
        }
    }

    private static double parseDouble(String raw, String field) {
        try {
            return Double.parseDouble(raw.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("неверный формат поля " + field);
        }
    }
}
