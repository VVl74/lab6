package com.example;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Writer;

public class Reg {
    Reader reader;

    public Reg(Reader nreader) {
        reader = nreader;
    }

    public String[] autorize() {
        System.out.println("Введите логин:");
        String login = reader.readLine();
        System.out.println("Введите пароль:");
        String password = reader.readLine();


        String[] res = new String[2];
        res[0] = login;
        res[1] = password;

        return res;
    }
}
