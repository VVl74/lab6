package com.example;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reader {
    Object reader;
    Terminal terminal = null;
    boolean useJLine;

    public  Reader() {
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

    public String readLine() {
        String input = null;
        try {
            if (useJLine) {
                input = ((LineReader) reader).readLine();
            } else {
                input = ((BufferedReader) reader).readLine();
            }
        } catch (Exception e) {
            System.out.println("ввод завершен");
        }

        return input;
    }
}
