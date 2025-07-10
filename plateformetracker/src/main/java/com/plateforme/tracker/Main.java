package com.plateforme.tracker;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale pour l'interaction console avec authentification et logs.
 */
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/school_management";
        String user = "postgres";
        String password = "postgres";

        DatabaseManager dbManager = new DatabaseManager(url, user, password);
        StudentDAO dao = new StudentDAO(dbManager);
        UserDAO userDAO = new UserDAO(dbManager);
        LogDAO logDAO = new LogDAO(dbManager);
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        // Authentification
        while (currentUser == null) {
            System.out.println("--- Connexion requise ---");
            System.out.print("Nom d'utilisateur : ");
            String login = scanner.nextLine();
            System.out.print("Mot de passe (hashé, pour test) : ");
            String pwd = scanner.nextLine();
            try {
                currentUser = userDAO.authenticate(login, pwd);
                if (currentUser == null) {
                    System.out.println("Identifiants invalides. Réessayez.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur SQL lors de l'authentification : " + e.getMessage());
            }
        }
        System.out.println("Connecté en tant que " + currentUser.getUsername() + " (rôle : " + currentUser.getRole() + ")");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Gestion Étudiants ---");
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Modifier un étudiant");
            System.out.println("3. Supprimer un étudiant");
            System.out.println("4. Afficher tous les étudiants");
            System.out.println("5. Rechercher un étudiant par ID");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            String choix = scanner.nextLine();
            try {
                switch (choix) {
                    case "1": {
                        System.out.print("Prénom : ");
                        String fn = scanner.nextLine();
                        System.out.print("Nom : ");
                        String ln = scanner.nextLine();
                        System.out.print("Âge : ");
                        int age = Integer.parseInt(scanner.nextLine());
                        System.out.print("Note : ");
                        double grade = Double.parseDouble(scanner.nextLine());
                        dao.addStudent(new Student(0, fn, ln, age, grade));
                        logDAO.addLog("Ajout étudiant", null, currentUser.getId());
                        System.out.println("Étudiant ajouté !");
                        break;
                    }
                    case "2": {
                        System.out.print("ID de l'étudiant à modifier : ");
                        int idMod = Integer.parseInt(scanner.nextLine());
                        Student sMod = dao.getStudentById(idMod);
                        if (sMod == null) {
                            System.out.println("Aucun étudiant trouvé.");
                            break;
                        }
                        System.out.print("Nouveau prénom (" + sMod.getFirstName() + ") : ");
                        String fn2 = scanner.nextLine();
                        if (!fn2.isEmpty()) sMod.setFirstName(fn2);
                        System.out.print("Nouveau nom (" + sMod.getLastName() + ") : ");
                        String ln2 = scanner.nextLine();
                        if (!ln2.isEmpty()) sMod.setLastName(ln2);
                        System.out.print("Nouvel âge (" + sMod.getAge() + ") : ");
                        String age2 = scanner.nextLine();
                        if (!age2.isEmpty()) sMod.setAge(Integer.parseInt(age2));
                        System.out.print("Nouvelle note (" + sMod.getGrade() + ") : ");
                        String grade2 = scanner.nextLine();
                        if (!grade2.isEmpty()) sMod.setGrade(Double.parseDouble(grade2));
                        dao.updateStudent(sMod);
                        logDAO.addLog("Modification étudiant", idMod, currentUser.getId());
                        System.out.println("Étudiant modifié !");
                        break;
                    }
                    case "3": {
                        if (!"admin".equalsIgnoreCase(currentUser.getRole())) {
                            System.out.println("Seuls les administrateurs peuvent supprimer un étudiant.");
                            break;
                        }
                        System.out.print("ID de l'étudiant à supprimer : ");
                        int idSup = Integer.parseInt(scanner.nextLine());
                        dao.deleteStudent(idSup);
                        logDAO.addLog("Suppression étudiant", idSup, currentUser.getId());
                        System.out.println("Étudiant supprimé !");
                        break;
                    }
                    case "4": {
                        List<Student> students = dao.getAllStudents();
                        if (students.isEmpty()) {
                            System.out.println("Aucun étudiant trouvé.");
                        } else {
                            students.forEach(System.out::println);
                        }
                        logDAO.addLog("Affichage liste étudiants", null, currentUser.getId());
                        break;
                    }
                    case "5": {
                        System.out.print("ID de l'étudiant à rechercher : ");
                        int idRech = Integer.parseInt(scanner.nextLine());
                        Student s = dao.getStudentById(idRech);
                        if (s == null) {
                            System.out.println("Aucun étudiant trouvé.");
                        } else {
                            System.out.println(s);
                        }
                        logDAO.addLog("Recherche étudiant", idRech, currentUser.getId());
                        break;
                    }
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur SQL : " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Entrée invalide (nombre attendu).");
            }
        }
        System.out.println("Au revoir !");
    }
}
