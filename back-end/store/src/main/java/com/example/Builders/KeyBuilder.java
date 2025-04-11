package com.example.Builders;

import java.util.Random;

public abstract class KeyBuilder {
    private static final int LIMIT = 64;
    private static final String DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

    public static String generateKey(){
        StringBuilder randString = new StringBuilder(LIMIT);
        Random rand = new Random();

        for (int i = 0; i < LIMIT; i++) {
            int randIndex = rand.nextInt(DATA.length());
            char randChar = DATA.charAt(randIndex);
            randString.append(randChar);
        }
        return randString.toString();
    }
}
