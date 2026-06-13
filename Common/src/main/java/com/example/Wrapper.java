package com.example;

public class Wrapper {
    private String zapr = "";
    private long otpravTime;
    private int controlSum;
    private int hash;
    private String login;
    private String password;

    public Wrapper() {
        otpravTime = System.currentTimeMillis();
    }

    public void setZapr(String nzapr, int newcontrolsum, int newhash, String nlogin, String npassword) {
        zapr = nzapr;
        controlSum = newcontrolsum;
        hash = newhash;
        login = nlogin;
        password = npassword;
    }

    public String getZapr() {
        return zapr;
    }
    public int getControlSum() {
        return controlSum;
    }

    public int getHash() {
        return hash;
    }

    public long getOtpravTime() {
        return otpravTime;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
