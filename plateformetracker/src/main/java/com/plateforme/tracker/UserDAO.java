package com.plateforme.tracker;

import java.sql.*;

/**
 * DAO pour la gestion des utilisateurs (authentification).
 */
public class UserDAO {
    private final DatabaseManager dbManager;

    public UserDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Authentifie un utilisateur par son nom d'utilisateur et mot de passe hashé.
     * @param username nom d'utilisateur
     * @param passwordHash hash du mot de passe
     * @return User si trouvé, sinon null
     * @throws SQLException en cas d'erreur SQL
     */
    public User authenticate(String username, String passwordHash) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password_hash=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
}
