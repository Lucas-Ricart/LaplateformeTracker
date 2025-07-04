package com.plateforme.tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe pour gérer la connexion à la base de données PostgreSQL.
 * 
 * @deprecated Utilisez {@link DatabaseManager} pour la gestion de la connexion JDBC.
 */
@Deprecated
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/student_management";
    private static final String USER = "postgres"; // Remplacez par votre utilisateur
    private static final String PASSWORD = "postgres"; // Remplacez par votre mot de passe
    private Connection connection;

    /**
     * Établit une connexion à la base de données.
     * 
     * @return Connection active
     * @throws SQLException si la connexion échoue
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture : " + e.getMessage());
        }
    }
}
