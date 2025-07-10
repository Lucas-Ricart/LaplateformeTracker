package com.plateforme.tracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des étudiants (CRUD).
 */
public class StudentDAO {
    private final DatabaseManager dbManager;

    /**
     * Initialise le DAO avec un gestionnaire de connexion.
     * @param dbManager Instance de DatabaseManager
     */
    public StudentDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Ajoute un étudiant à la base.
     * @param student Étudiant à ajouter
     * @throws SQLException en cas d'erreur SQL
     */
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, age, grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setFloat(4, student.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Met à jour un étudiant existant.
     * @param student Étudiant à mettre à jour (doit avoir un id)
     * @throws SQLException en cas d'erreur SQL
     */
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name=?, last_name=?, age=?, grade=? WHERE id=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setFloat(4, student.getGrade());
            stmt.setInt(5, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'étudiant : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Supprime un étudiant par son id.
     * @param id Identifiant de l'étudiant
     * @throws SQLException en cas d'erreur SQL
     */
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Récupère un étudiant par son id.
     * @param id Identifiant de l'étudiant
     * @return Student ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id=?";
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
                        rs.getFloat("grade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'étudiant : " + e.getMessage());
            throw e;
        }
        return null;
    }

    /**
     * Récupère tous les étudiants.
     * @return Liste des étudiants
     * @throws SQLException en cas d'erreur SQL
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = dbManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("age"),
                    rs.getFloat("grade")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
            throw e;
        }
        return students;
    }
}
