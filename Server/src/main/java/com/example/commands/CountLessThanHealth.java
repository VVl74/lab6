package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Set;
/**
 * Комманда для выведения всех элементов чье здоровье меньше заданного
 *
 */
public class CountLessThanHealth implements Command {
    public void execute(String[] args, CollectionManager collectionManager, PrintWriter out) {
        Logger logger = LoggerFactory.getLogger(CountLessThanHealth.class);
        if (args.length > 1) {
            throw new ArgExeption();
            // System.out.println("нужен только 1 аргумент");
        }

        double hp;

        try {
            hp = Double.parseDouble(args[0]);
        } catch (Exception e) {
            throw new InputExeption();
        }

        int sh = 0;
        Set <Integer> mapValues = collectionManager.getCollection().keySet();

        for (var v : mapValues) {
            if (collectionManager.getCollection().get(v).getHealth() < hp) {
                sh++;
            }
        }
        out.println("колво элементов:" + sh);
        out.println("элементы посчитаны\n");
        logger.info("элементы посчитаны");
    }
    public String getComandInfo() {
        return "count_less_than_health health (double) : вывести количество элементов, "
                + "значение поля health которых меньше заданного\n";
    }
}
