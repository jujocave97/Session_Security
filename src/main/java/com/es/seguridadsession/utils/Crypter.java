package com.es.seguridadsession.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// clase para hashear contraseñas
public class Crypter {
    // método para hashear contraseña
    public static String hashPassword(String password) {
        try {
            // Crear instancia de MessageDigest con SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hashear la contraseña
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convertir el hash a formato hexadecimal
            StringBuilder hashHex = new StringBuilder();
            for (byte b : hashBytes) {
                hashHex.append(String.format("%02x", b));
            }

            return hashHex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al inicializar el algoritmo SHA-256", e);
        }
    }

}
