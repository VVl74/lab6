package com.example.utils;

public class ParsedRequest {
    public  String[] parts;
    public String login;
    public String password;

    public ParsedRequest(String[] parts, String login, String password) {
        this.parts = parts;
        this.login = login;
        this.password = password;
    }

    public String[] getParts() {
        return parts;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
