package com.example.managers;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class InputManager {
    Logger logger = Logger.getLogger(String.valueOf(InputManager.class));
    Terminal terminal;
    LineReader reader;
    public InputManager() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        reader = LineReaderBuilder.builder().terminal(terminal).build();
    }
    public void InputTerm(CommandManager curCommandManager) throws IOException {
        if (System.in.available() > 0) {
            String TerInput = null;
            try {
                TerInput = reader.readLine();
            } catch (Exception e) {
                System.out.println("ввод завершен");
            }

            if (TerInput == null) {
                return;
            }

            TerInput = TerInput.trim();

            String[] TerArgs = TerInput.split(" ");

            ByteArrayOutputStream bufStr = new ByteArrayOutputStream();
            PrintWriter out = new PrintWriter(bufStr);

            if (TerArgs[0].equals("exit") || TerArgs[0].equals("save")) {
                logger.info("команда выполнена");
                curCommandManager.newCommand(TerArgs, out);
                System.out.println(bufStr.toString());
            } else {
                logger.info("команда не выполнена");
            }
        }
    }
}
