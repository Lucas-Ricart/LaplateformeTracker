package com.plateforme.tracker;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller pour la page d'accueil (connexion).
 */
public class PageAccueilController {

    private Main mainApp;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    /**
     * Permet à Main de passer une référence à ce controller,
     * afin de pouvoir changer la scène après authentification.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Connexion".
     * Effectue l'authentification et, si réussie, change la scène.
     */@FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            showAlert("Erreur de saisie", "Le nom d'utilisateur ne peut pas être vide.");
            return;
        }
        if (password == null || password.isEmpty()) {
            showAlert("Erreur de saisie", "Le mot de passe ne peut pas être vide.");
            return;
        }

        try {
            // Ici, tu devrais hasher le mot de passe avant d'appeler authenticate
            String passwordHash = PasswordUtils.hashPassword(password);

            // 👇👇 Ajoute ce print pour vérifier le hash généré
            System.out.println("Test hash for '" + password + "': " + passwordHash);

            UserDAO userDAO = new UserDAO(new DatabaseManager(
                "jdbc:postgresql://localhost:5432/laplateforme",
                "postgres",
                "admin"
            ));
            User user = userDAO.authenticate(username, passwordHash);

            if (user != null) {
                mainApp.showMainApp();
            } else {
                showAlert("Authentification échouée", "Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de l'authentification.");
        }
    }



    /**
     * Méthode appelée lors du clic sur le bouton "Inscription".
     * Ici tu peux ouvrir la fenêtre d'inscription ou autre.
     */
    @FXML
    private void handleSignup() {
        showAlert("Information", "La fonction d'inscription n'est pas encore implémentée.");
    }

    /**
     * Affiche une boîte de dialogue d'alerte.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
