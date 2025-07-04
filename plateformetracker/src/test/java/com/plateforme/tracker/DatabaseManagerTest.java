package com.plateforme.tracker;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour DatabaseManager.
 */
public class DatabaseManagerTest {
    private DatabaseManager dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager("jdbc:postgresql://localhost:5432/student_management", "postgres", "postgres");
    }

    @Test
    void testOpenConnection() throws SQLException {
        try (Connection conn = dbManager.openConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        }
    }

    @Test
    void testInvalidConnection() {
        DatabaseManager badDb = new DatabaseManager("jdbc:postgresql://localhost:5432/bad_db", "bad", "bad");
        assertThrows(SQLException.class, badDb::openConnection);
    }
}
