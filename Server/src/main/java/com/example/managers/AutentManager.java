package com.example.managers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

    public String hashPassword(String password, String salt) {
        String itog = password + salt + pepper;

        try {
            MessageDigest prhash = MessageDigest.getInstance("SHA-1");

            byte[] hash = prhash.digest(itog.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            System.out.println("не поддерживается этот алгоритм");
            return "";
        }
    }
}
