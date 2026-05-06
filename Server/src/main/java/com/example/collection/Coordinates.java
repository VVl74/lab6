package com.example.collection;
/**
 * Класс координат космоесантника
 *
 */
public class Coordinates {
    private Float x; //Поле не может быть null
    private Long y; //Максимальное значение поля: 759, Поле не может быть null

    public Coordinates(Float nx, Long ny) {
        x = nx;
        y = ny;
    }

    public float getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
