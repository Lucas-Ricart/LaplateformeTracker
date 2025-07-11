package com.plateforme.tracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    /**
     * Hash un mot de passe avec SHA-256.
     * @param password mot de passe en clair
     * @return hash hexadécimal du mot de passe
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hashage du mot de passe", e);
        }
    }

    /**
     * Vérifie si un mot de passe correspond à un hash donné.
     * @param password mot de passe en clair
     * @param hashedPassword hash attendu
     * @return true si correspond, false sinon
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashOfInput = hashPassword(password);
        return hashOfInput.equals(hashedPassword);
    }
}
