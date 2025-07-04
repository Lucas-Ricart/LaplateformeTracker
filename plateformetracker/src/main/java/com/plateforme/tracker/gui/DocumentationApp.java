package com.plateforme.tracker.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Documentation interactive de l'application Student Management.
 */
public class DocumentationApp extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
            new Label("Projet : Système de gestion d'étudiants en Java avec PostgreSQL"),
            new Label("- Console : com.plateforme.tracker.Main"),
            new Label("- Interface graphique : com.plateforme.tracker.gui.StudentManagerApp"),
            new Label("- Structure modulaire (DAO, POJO, gestion JDBC)"),
            new Label("- Toutes les requêtes SQL utilisent PreparedStatement"),
            new Label("- Gestion des erreurs et des saisies utilisateur"),
            new Label("- Voir DOCUMENTATION.md pour plus de détails")
        );
        stage.setTitle("Documentation Student Management");
        stage.setScene(new Scene(root, 500, 250));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
