package com.example.managers;

import java.security.SecureRandom;

public class AutentManager {
    String pepper = "ICPCenjoier";

    public String newSalt() {
        byte[] salt = new byte[16];

        new SecureRandom().nextBytes(salt);

        StringBuilder  sb = new StringBuilder();

        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public hashPassword(String password, String salt) {
        String itog = password + salt + pepper;
    }
}
