package com.example.collection;
/**
 * Класс ордена космодесантника
 *
 */
public class Chapter implements Comparable<Chapter> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private long marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле может быть null

    private Chapter(Builder builder) {
        name = builder.name;
        parentLegion = builder.parentLegion;
        marinesCount = builder.marinesCount;
        world = builder.world;
    }

    public static class Builder {
        private String name;
        private String parentLegion;
        private long marinesCount;
        private String world;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parentLegion(String parentLegion) {
            this.parentLegion = parentLegion;
            return this;
        }

        public Builder marinesCount(long marinesCount) {
            this.marinesCount = marinesCount;
            return this;
        }

        public Builder world(String world) {
            this.world = world;
            return this;
        }

        public Chapter build() {
            return new Chapter(this);
        }
    }


    @Override
    public int compareTo(Chapter p) {
        return Double.compare(this.marinesCount, p.marinesCount);
    }

    public long getMarinesCount() {
        return marinesCount;
    }
    public String getName() {
        return name;
    }

    public String getParentLegion() {
        return parentLegion;
    }
    public String getWorld() {
        return world;
    }
}
