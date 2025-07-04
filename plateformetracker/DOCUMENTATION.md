# Documentation du projet Student Management

## Présentation
Ce projet est une application Java permettant de gérer des étudiants avec une base de données PostgreSQL. Il propose une interface console et une interface graphique de test (JavaFX).

## Structure du projet
- `DatabaseManager` : gestion de la connexion JDBC à PostgreSQL.
- `Student` : POJO représentant un étudiant.
- `StudentDAO` : accès aux données (CRUD) pour la table `student`.
- `Main` : interface console pour manipuler les étudiants.
- `gui/StudentManagerApp` : interface graphique JavaFX pour tester les opérations CRUD.

## Lancement
1. **Base de données** :
   - Créez la base et la table avec ce script :
     ```sql
     CREATE DATABASE student_management;
     \c student_management
     CREATE TABLE student (
         id SERIAL PRIMARY KEY,
         first_name VARCHAR(100) NOT NULL,
         last_name VARCHAR(100) NOT NULL,
         age INT NOT NULL,
         grade FLOAT
     );
     ```
2. **Configuration** :
   - Modifiez l’URL, l’utilisateur et le mot de passe dans `Main.java` et `StudentManagerApp.java` si besoin.
3. **Compilation et exécution** :
   - Pour la console :
     ```sh
     mvn compile exec:java -Dexec.mainClass="com.plateforme.tracker.Main"
     ```
   - Pour l’interface graphique :
     ```sh
     mvn javafx:run -Dexec.mainClass="com.plateforme.tracker.gui.StudentManagerApp"
     ```

## Bonnes pratiques
- Toutes les requêtes SQL utilisent `PreparedStatement`.
- Les connexions sont fermées automatiquement (`try-with-resources`).
- Gestion des erreurs SQL et de saisie utilisateur avec messages clairs.
- Code commenté et structuré.

## Dépendances
- Java 17+
- JavaFX 21
- PostgreSQL JDBC

## Auteur
Projet généré avec l’aide de GitHub Copilot.
