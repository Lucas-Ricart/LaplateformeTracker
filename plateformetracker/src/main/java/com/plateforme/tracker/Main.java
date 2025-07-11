package com.plateforme.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PageAcceuil.fxml"));
        Parent root = loader.load();

        PageAccueilController controller = loader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Plateforme Tracker - Connexion");
        primaryStage.show();
    }

    public void showMainApp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuEtudiant.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Plateforme Tracker - Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
