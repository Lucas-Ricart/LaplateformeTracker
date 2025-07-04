package com.plateforme.tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gère la connexion à la base de données PostgreSQL.
 */
public class DatabaseManager {
    private final String url;
    private final String user;
    private final String password;

    /**
     * Initialise le gestionnaire de connexion.
     * @param url URL JDBC de la base de données
     * @param user Nom d'utilisateur PostgreSQL
     * @param password Mot de passe PostgreSQL
     */
    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Ouvre une connexion à la base de données.
     * @return Connection JDBC
     * @throws SQLException en cas d'échec de connexion
     */
    public Connection openConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            throw e;
        }
    }
}
