package ru.nsu.fit.lab4;

import java.util.IllegalFormatException;

public class Subject {
    private final String name;
    private byte[] grades;
    private byte[] duration; //array of IDs of present semesters

    public Subject(String name, byte[] duration) {
        this.name = name;
        this.duration = duration;
        this.grades = new byte[8];
    }

    public Subject(String name) {
        this.name = name;
        this.grades = new byte[8];
    }

    public void setDuration(byte[] duration) {
        this.duration = duration;
    }

    public byte[] getDuration() {
        return duration;
    }

    public byte[] getGrades() {
        return grades;
    }

    //there must be 8 elements because this array functions as a dictionary
    //semester ID - grade (4 years * 2 sem-s in each)
    public void setGrades(byte[] grades) throws IllegalArgumentException {
        if (grades.length < 9) {
            throw new IllegalArgumentException();
        }
        this.grades = grades;
    }

    public void setGrade(byte semID, byte grade) {
        grades[semID] = grade;
    }

    public String getName() {
        return name;
    }

}
