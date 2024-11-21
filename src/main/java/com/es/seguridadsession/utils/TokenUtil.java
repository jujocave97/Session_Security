package com.es.seguridadsession.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * CLASE ENCARGADA DE GENERAR TOKENS
 */
public class TokenUtil {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ADC"; // 16 caracteres

    public static String encrypt(String data) throws Exception {
        byte[] originalKey = SECRET_KEY.getBytes(); // Ejemplo: 12 bytes
        byte[] fixedKey = Arrays.copyOf(originalKey, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(fixedKey, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData) throws Exception {
        byte[] originalKey = SECRET_KEY.getBytes(); // Ejemplo: 12 bytes
        byte[] fixedKey = Arrays.copyOf(originalKey, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(fixedKey, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

}
