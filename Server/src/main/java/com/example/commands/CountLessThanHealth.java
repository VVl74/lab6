package com.example.commands;

import com.example.exeptions.ArgExeption;
import com.example.exeptions.InputExeption;
import com.example.managers.DBCollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

/**
 * Комманда для подсчёта элементов чье здоровье меньше заданного
 */
public class CountLessThanHealth implements Command {
    private final DBCollectionManager collectionManager;

    public CountLessThanHealth(DBCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandContext ctx) {
        Logger logger = LoggerFactory.getLogger(CountLessThanHealth.class);
        String[] args = ctx.getArgs();
        PrintWriter out = ctx.getOut();

        if (args.length != 1) {
            throw new ArgExeption();
        }

        double hp;
        try {
            hp = Double.parseDouble(args[0]);
        } catch (Exception e) {
            throw new InputExeption(e.getMessage(), out);
        }

        int sh = collectionManager.countLessHealth(hp);
        out.println("колво элементов: " + sh);
        out.println("элементы посчитаны\n");
        logger.info("элементы посчитаны");
    }

    @Override
    public String getComandInfo() {
        return "count_less_than_health health (double) : вывести количество элементов, "
                + "значение поля health которых меньше заданного\n";
    }
}
