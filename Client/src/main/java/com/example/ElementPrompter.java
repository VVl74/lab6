package com.example;

public class ElementPrompter {
    private final Reader reader;

    public ElementPrompter(Reader reader) {
        this.reader = reader;
    }

    public String[] readMarine() {
        return new String[] {
                FieldParser.toArg(askInt("Введите id: ", FieldParser::parseId)),
                ask("Введите имя: ", FieldParser::parseName),
                FieldParser.toArg(askFloat("Введите координату X: ", FieldParser::parseX)),
                FieldParser.toArg(askLong("Введите координату Y: ", FieldParser::parseY)),
                FieldParser.toArg(askDouble("Введите уровень здоровья: ", FieldParser::parseHealth)),
                ask("Введите категорию (ASSAULT, TACTICAL, HELIX): ", FieldParser::parseCategory),
                ask("Введите основное оружие (BOLTGUN, MELTAGUN, FLAMER, HEAVY_FLAMER): ", FieldParser::parseWeapon),
                ask("Введите оружие ближнего боя (CHAIN_SWORD, POWER_SWORD, CHAIN_AXE, MANREAPER, POWER_FIST): ",
                        FieldParser::parseMeleeWeapon),
                ask("Введите название клана: ", FieldParser::parseChapterName),
                ask("Введите имя командира легиона: ", FieldParser::parseParentLegion),
                FieldParser.toArg(askLong("Введите количество космодесантников: ", FieldParser::parseMarinesCount)),
                ask("Введите название планеты: ", FieldParser::parseWorld)
        };
    }

    public String[] readChapter() {
        return new String[] {
                ask("Введите название клана: ", FieldParser::parseChapterName),
                ask("Введите имя командира легиона: ", FieldParser::parseParentLegion),
                FieldParser.toArg(askLong("Введите количество космодесантников: ", FieldParser::parseMarinesCount)),
                ask("Введите название планеты: ", FieldParser::parseWorld)
        };
    }

    private String ask(String prompt, java.util.function.Function<String, String> parser) {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            try {
                return parser.apply(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    private int askInt(String prompt, java.util.function.Function<String, Integer> parser) {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            try {
                return parser.apply(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    private long askLong(String prompt, java.util.function.Function<String, Long> parser) {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            try {
                return parser.apply(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    private float askFloat(String prompt, java.util.function.Function<String, Float> parser) {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            try {
                return parser.apply(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }

    private double askDouble(String prompt, java.util.function.Function<String, Double> parser) {
        while (true) {
            System.out.print(prompt);
            String line = reader.readLine();
            try {
                return parser.apply(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }
}
