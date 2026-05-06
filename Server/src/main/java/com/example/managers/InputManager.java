package com.example.managers;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;
import java.util.logging.Logger;

public class InputManager {
    Logger logger = Logger.getLogger(String.valueOf(InputManager.class));
    Terminal terminal;
    Object reader;
    boolean useJLine;
    public InputManager() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            reader = LineReaderBuilder.builder().terminal(terminal).build();
            useJLine = true;
        } catch (Exception e) {
            System.out.println("JLine не доступен используем обычный терминал");
            reader = new BufferedReader(new InputStreamReader(System.in));
            useJLine = false;

        }
    }
    public void InputTerm(CommandManager curCommandManager) {
        try {
            if (System.in.available() <= 0) {
                return;
            }
            String TerInput = null;
            if (useJLine) {
                TerInput = ((LineReader) reader).readLine();
            } else {
                TerInput = ((BufferedReader) reader).readLine();
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
        } catch (Exception e) {
            System.out.println("Ошибка при обработке строки");
        }
    }
}
