package com.sb.utils;

import java.util.UUID;

public class BarcodeUtils {
    public static String generateEAN13Barcode() {
        String base12Digits = UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        int sum = 0;
        for (int i = 0; i < base12Digits.length(); i++) {
            int digit = Character.getNumericValue(base12Digits.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return base12Digits + checkDigit;
    }
}
