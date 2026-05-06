package com.example;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Reader {
    LineReader reader;
    Terminal terminal = null;

    public  Reader() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        reader = LineReaderBuilder.builder().terminal(terminal).build();
    }

    public String readLine() {
        String input = null;
        try {
            input = reader.readLine();
        } catch (Exception e) {
            System.out.println("ввод завершен");
        }

        return input;
    }
}
