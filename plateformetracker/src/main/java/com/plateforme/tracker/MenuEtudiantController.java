package com.plateforme.tracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrôleur pour la vue MenuEtudiant.fxml
 * Gère les actions CRUD, affichage avec pagination.
 */
public class MenuEtudiantController {

    @FXML private TableView<Student> tableViewStudents;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colFirstName;
    @FXML private TableColumn<Student, String> colLastName;
    @FXML private TableColumn<Student, Integer> colAge;
    @FXML private TableColumn<Student, Double> colGrade;

    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAge;
    @FXML private TextField tfGrade;
    @FXML private TextField tfIdSearch;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnSearch;

    @FXML private Button btnPrevPage;
    @FXML private Button btnNextPage;
    @FXML private Label lblPageInfo;

    private final StudentDAO studentDAO;

    private final int rowsPerPage = 10;
    private int currentPage = 1;
    private int totalStudents;
    private int totalPages;

    public MenuEtudiantController() {
        this.studentDAO = new StudentDAO(new DatabaseManager(
        "jdbc:postgresql://localhost:5432/laplateforme",
        "postgres",
        "admin"
    ));
    }

    @FXML
    public void initialize() {
        // Initialiser les colonnes du TableView
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        colGrade.setCellValueFactory(cellData -> cellData.getValue().gradeProperty().asObject());

        loadStudentsPage(currentPage);
    }

    private void loadStudentsPage(int page) {
        try {
            totalStudents = studentDAO.getTotalStudentsCount();
            totalPages = (int) Math.ceil((double) totalStudents / rowsPerPage);

            int offset = (page - 1) * rowsPerPage;
            List<Student> students = studentDAO.getStudentsByPage(rowsPerPage, offset);

            ObservableList<Student> data = FXCollections.observableArrayList(students);
            tableViewStudents.setItems(data);

            lblPageInfo.setText("Page " + page + " / " + totalPages);

            // Gérer l'état des boutons pagination
            btnPrevPage.setDisable(page <= 1);
            btnNextPage.setDisable(page >= totalPages);

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des étudiants : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        try {
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            int age = Integer.parseInt(tfAge.getText());
            double grade = Double.parseDouble(tfGrade.getText());

            Student newStudent = new Student(0, firstName, lastName, age, grade);
            studentDAO.addStudent(newStudent);

            clearInputFields();
            loadStudentsPage(currentPage);
            showAlert("Succès", "Étudiant ajouté avec succès.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Âge et note doivent être des nombres valides.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'étudiant : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            int id = Integer.parseInt(tfIdSearch.getText());
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            int age = Integer.parseInt(tfAge.getText());
            double grade = Double.parseDouble(tfGrade.getText());

            Student existing = studentDAO.getStudentById(id);
            if (existing == null) {
                showAlert("Erreur", "Étudiant introuvable avec cet ID.", Alert.AlertType.ERROR);
                return;
            }

            Student updatedStudent = new Student(id, firstName, lastName, age, grade);
            studentDAO.updateStudent(updatedStudent);

            clearInputFields();
            loadStudentsPage(currentPage);
            showAlert("Succès", "Étudiant modifié avec succès.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID, âge et note doivent être des nombres valides.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            int id = Integer.parseInt(tfIdSearch.getText());
            Student existing = studentDAO.getStudentById(id);
            if (existing == null) {
                showAlert("Erreur", "Étudiant introuvable avec cet ID.", Alert.AlertType.ERROR);
                return;
            }

            studentDAO.deleteStudent(id);
            clearInputFields();
            loadStudentsPage(currentPage);
            showAlert("Succès", "Étudiant supprimé avec succès.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID doit être un nombre valide.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la suppression : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            int id = Integer.parseInt(tfIdSearch.getText());
            Student student = studentDAO.getStudentById(id);
            if (student == null) {
                showAlert("Info", "Aucun étudiant trouvé avec cet ID.", Alert.AlertType.INFORMATION);
                return;
            }
            // Affiche uniquement l'étudiant recherché
            ObservableList<Student> data = FXCollections.observableArrayList(student);
            tableViewStudents.setItems(data);

            // Remplit les champs pour modification
            tfFirstName.setText(student.getFirstName());
            tfLastName.setText(student.getLastName());
            tfAge.setText(String.valueOf(student.getAge()));
            tfGrade.setText(String.valueOf(student.getGrade()));

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID doit être un nombre valide.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handlePrevPage(ActionEvent event) {
        if (currentPage > 1) {
            currentPage--;
            loadStudentsPage(currentPage);
        }
    }

    @FXML
    private void handleNextPage(ActionEvent event) {
        if (currentPage < totalPages) {
            currentPage++;
            loadStudentsPage(currentPage);
        }
    }

    private void clearInputFields() {
        tfIdSearch.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfAge.clear();
        tfGrade.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
