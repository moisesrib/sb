package com.sb.utils;

import java.security.SecureRandom;

public class BarcodeUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCode128Barcode() {
        StringBuilder barcode = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            barcode.append(CHARACTERS.charAt(index));
        }
        return barcode.toString();
    }

}
