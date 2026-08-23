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

    private SpaceMarine(Builder builder) {
        id = builder.id;
        name = builder.name;
        coordinates = builder.coordinates;
        creationDate = builder.creationDate;
        health = builder.health;
        category = builder.category;
        weaponType = builder.weapon;
        meleeWeapon = builder.meleeWeapon;
        chapter = builder.chapter;
        ownerId = builder.ownerId;
    }

    public static class Builder {
        private int id;
        private String name;
        private Coordinates coordinates;
        private LocalDateTime creationDate;
        private double health;
        private AstartesCategory category;
        private Weapon weapon;
        private MeleeWeapon meleeWeapon;
        private Chapter chapter;
        private int ownerId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }
        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }
        public Builder health(double health) {
            this.health = health;
            return this;
        }
        public Builder category(AstartesCategory category) {
            this.category = category;
            return this;
        }

        public Builder weapon(Weapon weapon) {
            this.weapon = weapon;
            return this;
        }

        public Builder meleeWeapon(MeleeWeapon meleeWeapon) {
            this.meleeWeapon = meleeWeapon;
            return this;
        }

        public Builder chapter(Chapter chapter) {
            this.chapter = chapter;
            return this;
        }

        public Builder ownerId(int ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public SpaceMarine build() {
            return new SpaceMarine(this);
        }
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
    public void setOwnerName(String nownerName) { ownerName = nownerName; }
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
        return "id= " + id
                + " name= " + name
                + " x= " + coordinates.getX()
                + " y= " + coordinates.getY()
                + " creationDate= " + creationDate
                + " health= " + health
                + " category= " + category
                + " weaponType= " + weaponType
                + " meleeWeapon= " + meleeWeapon
                + " chapterName= " + chapter.getName()
                + " parentLegion= " + chapter.getParentLegion()
                + " marinesCount= " + chapter.getMarinesCount()
                + " world= " + chapter.getWorld()
                + " ownerId= " + ownerId
                + " ownerName= " + ownerName;
    }
}