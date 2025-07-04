package com.plateforme.tracker;

/**
 * POJO représentant un étudiant.
 */
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private float grade;

    /**
     * Constructeur sans ID (pour insertion).
     */
    public Student(String firstName, String lastName, int age, float grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.grade = grade;
    }

    /**
     * Constructeur avec ID (pour lecture).
     */
    public Student(int id, String firstName, String lastName, int age, float grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.grade = grade;
    }

    /**
     * Constructeur sans ID (pour insertion) - version double.
     */
    public Student(String firstName, String lastName, int age, double grade) {
        this(firstName, lastName, age, (float) grade);
    }

    /**
     * Constructeur avec ID (pour lecture) - version double.
     */
    public Student(int id, String firstName, String lastName, int age, double grade) {
        this(id, firstName, lastName, age, (float) grade);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public float getGrade() { return grade; }
    public void setGrade(float grade) { this.grade = grade; }
    public void setGrade(double grade) { this.grade = (float) grade; }

    @Override
    public String toString() {
        return String.format("[%d] %s %s, %d ans, note: %.2f", id, firstName, lastName, age, grade);
    }
}
