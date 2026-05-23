package com.example;

public class WrapperUtils {
    static int mod = 16384;
    int prost;
    public WrapperUtils(int nprost){
        prost = nprost;
    }

    public int getControlSum(String mes) {
        int sum = 0;
        for (int i=0; i < mes.length(); i++) {
            int simvcode = mes.charAt(i);
            sum = sum + simvcode;
            sum = sum % mod;
        }

        return sum;
    }

    public int makeHash(String mes) {
        int hash = 0;
        for (int i=0; i < mes.length(); i++) {
            int simvcode = mes.charAt(i);
            hash = hash * prost + simvcode;
            hash = hash % mod;
        }

        return hash;
    }
}
