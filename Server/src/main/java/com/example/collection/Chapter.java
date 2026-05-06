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

    public Chapter(String nname, String nparentLegion, long nmarinesCount, String nworld) {
        name = nname;
        parentLegion = nparentLegion;
        marinesCount = nmarinesCount;
        world = nworld;
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
