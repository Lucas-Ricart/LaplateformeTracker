package com.plateforme.tracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les étudiants dans la base de données.
 */
public class StudentDAO {
    private final DatabaseManager dbManager;

    public StudentDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    // Ajouter un étudiant
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (first_name, last_name, age, grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getGrade());
            stmt.executeUpdate();
        }
    }

    // Modifier un étudiant
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET first_name=?, last_name=?, age=?, grade=? WHERE id=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getGrade());
            stmt.setInt(5, student.getId());
            stmt.executeUpdate();
        }
    }

    // Supprimer un étudiant par ID
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM student WHERE id=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Récupérer un étudiant par ID
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student WHERE id=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getDouble("grade")
                    );
                }
            }
        }
        return null;
    }

    // Récupérer tous les étudiants
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getDouble("grade")
                ));
            }
        }
        return students;
    }

    // Pagination : récupérer des étudiants par page (limit, offset)
    public List<Student> getStudentsByPage(int limit, int offset) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id LIMIT ? OFFSET ?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getDouble("grade")
                    ));
                }
            }
        }
        return students;
    }

    // Récupérer le nombre total d'étudiants
    public int getTotalStudentsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM student";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
