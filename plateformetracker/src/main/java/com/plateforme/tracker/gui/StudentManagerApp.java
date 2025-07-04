package com.plateforme.tracker.gui;

import java.sql.SQLException;

import com.plateforme.tracker.DatabaseManager;
import com.plateforme.tracker.Student;
import com.plateforme.tracker.StudentDAO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Interface graphique simple pour tester les opérations CRUD sur les étudiants.
 */
public class StudentManagerApp extends Application {
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private StudentDAO dao;
    private TableView<Student> tableView;

    @Override
    public void start(Stage primaryStage) {
        // À adapter selon votre configuration PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/student_management";
        String user = "postgres";
        String password = "postgres";
        dao = new StudentDAO(new DatabaseManager(url, user, password));
        tableView = new TableView<>(studentList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()));
        TableColumn<Student, String> fnCol = new TableColumn<>("Prénom");
        fnCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));
        TableColumn<Student, String> lnCol = new TableColumn<>("Nom");
        lnCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        TableColumn<Student, Number> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAge()));
        TableColumn<Student, Number> gradeCol = new TableColumn<>("Note");
        gradeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getGrade()));
        // Ajout suppression du warning de type safety
        @SuppressWarnings("unchecked")
        TableColumn<Student, ?>[] columns = new TableColumn[] {idCol, fnCol, lnCol, ageCol, gradeCol};
        tableView.getColumns().addAll(columns);

        Button refreshBtn = new Button("Rafraîchir");
        refreshBtn.setOnAction(e -> refreshList());
        Button addBtn = new Button("Ajouter");
        addBtn.setOnAction(e -> showAddDialog());
        Button delBtn = new Button("Supprimer");
        delBtn.setOnAction(e -> deleteSelected());

        HBox btnBox = new HBox(10, refreshBtn, addBtn, delBtn);
        btnBox.setPadding(new Insets(10));
        VBox root = new VBox(10, tableView, btnBox);
        root.setPadding(new Insets(10));
        primaryStage.setTitle("Gestion Étudiants (Test GUI)");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        refreshList();
    }

    private void refreshList() {
        studentList.clear();
        try {
            studentList.addAll(dao.getAllStudents());
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
        }
    }

    private void showAddDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un étudiant");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField fn = new TextField(); fn.setPromptText("Prénom");
        TextField ln = new TextField(); ln.setPromptText("Nom");
        TextField age = new TextField(); age.setPromptText("Âge");
        TextField grade = new TextField(); grade.setPromptText("Note");
        grid.add(new Label("Prénom:"), 0, 0); grid.add(fn, 1, 0);
        grid.add(new Label("Nom:"), 0, 1); grid.add(ln, 1, 1);
        grid.add(new Label("Âge:"), 0, 2); grid.add(age, 1, 2);
        grid.add(new Label("Note:"), 0, 3); grid.add(grade, 1, 3);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Student(0, fn.getText(), ln.getText(), Integer.parseInt(age.getText()), Double.parseDouble(grade.getText()));
                } catch (NumberFormatException e) {
                    System.err.println("Erreur : " + e.getMessage());
                }
            }
            return null;
        });
        dialog.showAndWait().ifPresent(student -> {
            try {
                dao.addStudent(student);
                refreshList();
            } catch (SQLException e) {
                showAlert("Erreur SQL", e.getMessage());
            }
        });
    }

    private void deleteSelected() {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dao.deleteStudent(selected.getId());
                refreshList();
            } catch (SQLException e) {
                showAlert("Erreur SQL", e.getMessage());
            }
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
