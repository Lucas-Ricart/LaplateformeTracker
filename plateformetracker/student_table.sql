-- Script SQL pour la table student
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT CHECK (age >= 0),
    grade REAL CHECK (grade >= 0 AND grade <= 20)
);
