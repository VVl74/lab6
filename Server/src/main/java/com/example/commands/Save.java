package com.example.commands;

import com.example.collection.SpaceMarine;
import com.example.commands.Command;
import com.example.exeptions.ArgExeption;
import com.example.exeptions.RecordExeption;
import com.example.managers.CollectionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
/**
 * Комманда для сохранения всех элементов коллекции в файл
 *
 */
/*
public class Save implements Command {
    @Override
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        if (args.length == 1) {
            String filename = args[0];

            HashMap <Integer, SpaceMarine> spaceMarineHashMap = collectionManager.getCollection();

            Set<Integer> keys = spaceMarineHashMap.keySet();

            try(BufferedWriter bufwriter = new BufferedWriter(new FileWriter(filename))) {

                for (int i : keys) {
                    SpaceMarine spaceMarine = spaceMarineHashMap.get(i);

                    String str = spaceMarine.getName() +
                            ";" + spaceMarine.getCoordinates().getX() + ";" + spaceMarine.getCoordinates().getY() +
                            ";" + spaceMarine.getHealth() + ";" +
                            spaceMarine.getCategory() + ";" + spaceMarine.getWeaponType() + ";" +
                            spaceMarine.getMeleeWeapon() + ";" + spaceMarine.getChapter().getName() + ";" +
                            spaceMarine.getChapter().getParentLegion() + ";" + spaceMarine.getChapter().getMarinesCount() + ";" +
                            spaceMarine.getChapter().getWorld() + "\n";

                    bufwriter.write(str);
                    bufwriter.flush();

                }
                System.out.println(System.getProperty("user.dir"));
                System.out.println(new File(filename).getAbsolutePath());

                System.out.println("коллекция сохранена\n");
            } catch(Exception e) {
                throw new RecordExeption();
            }



        } else {
            throw new ArgExeption();
        }
    }

    @Override
    public String getComandInfo() {
        return "save file_name (string): сохранить коллекцию в файл, необходимо передать название\n";
    }
}
 */
