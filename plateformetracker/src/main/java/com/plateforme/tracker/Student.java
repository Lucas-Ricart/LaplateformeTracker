package com.plateforme.tracker;

import javafx.beans.property.*;

public class Student {

    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty age;
    private final DoubleProperty grade;

    public Student(int id, String firstName, String lastName, int age, double grade) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.age = new SimpleIntegerProperty(age);
        this.grade = new SimpleDoubleProperty(grade);
    }

    // Getters et setters classiques
    public int getId() {
        return id.get();
    }
    public void setId(int value) {
        id.set(value);
    }
    public IntegerProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String value) {
        firstName.set(value);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String value) {
        lastName.set(value);
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }

    public int getAge() {
        return age.get();
    }
    public void setAge(int value) {
        age.set(value);
    }
    public IntegerProperty ageProperty() {
        return age;
    }

    public double getGrade() {
        return grade.get();
    }
    public void setGrade(double value) {
        grade.set(value);
    }
    public DoubleProperty gradeProperty() {
        return grade;
    }
}
