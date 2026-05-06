package com.example;

public class Wrapper {
    private String zapr = "";
    private long otpravTime;
    private int controlSum;
    private int hash;

    public Wrapper() {
        otpravTime = System.currentTimeMillis();
    }

    public void setZapr(String nzapr, int newcontrolsum, int newhash) {
        zapr = nzapr;
        controlSum = newcontrolsum;
        hash = newhash;
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
}
