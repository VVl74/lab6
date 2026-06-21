package com.example.collection;

import com.example.collection.MeleeWeapon;

import java.time.LocalDateTime;
/**
 * Базовый класс космодесантника
 *
 */
public class SpaceMarine implements Comparable<SpaceMarine> {
    private int ownerId;
    private String ownerName;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double health; //Значение поля должно быть больше 0
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(int nid, String nname, Coordinates ncoordinates, LocalDateTime time,  double nhealth, AstartesCategory ncategory, Weapon nweaponType, MeleeWeapon nmeleeWeapon, Chapter nchapter, int owid) {
        id = nid;
        name = nname;
        coordinates = ncoordinates;
        creationDate = time;
        health = nhealth;
        category = ncategory;
        weaponType = nweaponType;
        meleeWeapon = nmeleeWeapon;
        chapter = nchapter;
        ownerId = owid;
    }

    @Override
    public int compareTo(SpaceMarine p) {
        return Double.compare(this.health, p.health);
    }

    public int getId() {
        return id;
    }
    public double getHealth() {
        return health;
    }
    public String getName() {
        return name;
    }
    public void setId(int nid) {
        id = nid;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Chapter getChapter() {
        return chapter;
    }
    public String getOwnerName() { return ownerName; }
    public int getOwnerId() {return ownerId; }

    public LocalDateTime spaceGetTime() {
        return creationDate;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }



    @Override
    public String toString() {
        return "id= " + id + " name= " + name + " health= " + health + " chapter= "
                + chapter.getName() + " world = " + chapter.getWorld()
                 + " owner id: " + getOwnerId() + " owner name: " + getOwnerName();
    }
}