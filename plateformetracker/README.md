/*
👨‍💻 Projet : Système de gestion d'étudiants en Java avec PostgreSQL

📌 Objectif : Développer une application Java capable de gérer les étudiants avec une base de données PostgreSQL via JDBC.

🧠 Contexte 1 – À implémenter avec ton aide Copilot :
1. Créer une base de données PostgreSQL nommée "student_management".
2. Créer une table "student" avec les colonnes :
   - id (INT, clé primaire, auto-incrémentée),
   - first_name (VARCHAR),
   - last_name (VARCHAR),
   - age (INT),
   - grade (FLOAT).
3. Créer une classe Java `DatabaseManager` :
   - Gérer la connexion à la base PostgreSQL.
   - Utiliser JDBC avec `PreparedStatement`.
   - Ajouter gestion des erreurs (SQLException).
4. Créer une classe `Student` (POJO) avec les attributs correspondants et leurs getters/setters.
5. Créer une classe `StudentDAO` contenant les méthodes CRUD :
   - addStudent(Student s)
   - updateStudent(Student s)
   - deleteStudent(int id)
   - getStudentById(int id)
   - getAllStudents()
6. Ajouter une classe `Main` pour interagir avec l’utilisateur depuis la console (scanner ou menu).
7. Documenter chaque classe et méthode avec JavaDoc.
8. Gérer les erreurs de saisie et affichage clair en cas d'échec de connexion ou d'exécution SQL.

🎯 Règles importantes :
- Utiliser `PreparedStatement` pour toutes les requêtes SQL.
- Fermer les connexions dans un bloc `finally` ou utiliser `try-with-resources`.
- Ajouter des commentaires explicatifs dans le code.
- Nommer les variables et méthodes clairement.
- Proposer une structure propre et modulaire (ex : DAO séparé).

🚀 Let’s go Copilot, construis étape par étape une application fonctionnelle et bien structurée !
*/
