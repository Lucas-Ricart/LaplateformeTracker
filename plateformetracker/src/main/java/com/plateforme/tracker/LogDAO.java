package com.plateforme.tracker;

import java.sql.*;

/**
 * DAO pour la gestion des logs d'action.
 */
public class LogDAO {
    private final DatabaseManager dbManager;

    public LogDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Ajoute un log d'action.
     * @param action description de l'action
     * @param studentId id de l'étudiant concerné (nullable)
     * @param userId id de l'utilisateur ayant effectué l'action
     * @throws SQLException en cas d'erreur SQL
     */
    public void addLog(String action, Integer studentId, int userId) throws SQLException {
        String sql = "INSERT INTO logs (action, student_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, action);
            if (studentId != null) stmt.setInt(2, studentId); else stmt.setNull(2, Types.INTEGER);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        }
    }
}
