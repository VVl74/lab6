package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.managers.DBCollectionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Комманда для выведения всех элементов коллекции
 */
public class Show implements Command {
    private final DBCollectionManager collectionManager;

    public Show(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        Collection<SpaceMarine> mapValues = collectionManager.getCollection().values();

        ArrayList<SpaceMarine> nmarines = mapValues.stream()
                .sorted(Comparator.comparing(marine -> marine.getChapter().getWorld()))
                .collect(Collectors.toCollection(ArrayList::new));

        for (var v : nmarines) {
            ctx.getOut().println(
                    "id= " + v.getId()
                    + " name= " + v.getName()
                    + " x= " + v.getCoordinates().getX()
                    + " y= " + v.getCoordinates().getY()
                    + " creationDate= " + v.spaceGetTime()
                    + " health= " + v.getHealth()
                    + " category= " + v.getCategory()
                    + " weaponType= " + v.getWeaponType()
                    + " meleeWeapon= " + v.getMeleeWeapon()
                    + " chapterName= " + v.getChapter().getName()
                    + " parentLegion= " + v.getChapter().getParentLegion()
                    + " marinesCount= " + v.getChapter().getMarinesCount()
                    + " world= " + v.getChapter().getWorld()
                    + " ownerId= " + v.getOwnerId()
                    + " ownerName= " + v.getOwnerName()
            );
        }

        ctx.getOut().println("элементы коллекции выведены\n");
    }

    @Override
    public String getComandInfo() {
        return "show : вывести в стандартный поток вывода все элементы" +
                " коллекции в строковом представлении\n";
    }
}
