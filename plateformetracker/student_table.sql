-- Table pour les étudiants
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INTEGER CHECK (age >= 0),
    grade NUMERIC(4,2) CHECK (grade >= 0 AND grade <= 20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table pour les utilisateurs (authentification)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL, -- Stocke le hash du mot de passe
    role VARCHAR(20) DEFAULT 'user' CHECK (role IN ('user', 'admin')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table pour les sauvegardes automatiques
CREATE TABLE backups (
    id SERIAL PRIMARY KEY,
    backup_data JSONB NOT NULL, -- Stocke les données des étudiants en JSON
    backup_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table pour les logs (facultatif, pour tracer les actions)
CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    student_id INTEGER REFERENCES students(id),
    user_id INTEGER REFERENCES users(id),
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index pour améliorer les performances (tri, recherche)
CREATE INDEX idx_students_last_name ON students(last_name);
CREATE INDEX idx_students_age ON students(age);
CREATE INDEX idx_students_grade ON students(grade);

-- Insertion de données initiales pour les utilisateurs (exemple)
INSERT INTO users (username, password_hash, role) VALUES
    ('admin', '$2a$10$Xo0e8u9f3k2p4q6r8t9yX.abcdefg', 'admin'), -- Hash simulé (utilisez un vrai hash comme bcrypt)
    ('user1', '$2a$10$Xo0e8u9f3k2p4q6r8t9yX.hijklmnop', 'user');
