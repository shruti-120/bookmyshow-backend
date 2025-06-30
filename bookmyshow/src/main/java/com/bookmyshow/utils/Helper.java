package com.bookmyshow.utils;

public class Helper {
    
    public static String calculateHMACSHA256(String payload, String secret) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        byte[] hash = mac.doFinal(payload.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b: hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
