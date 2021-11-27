package ru.nsu.fit.lab4;

import java.util.HashMap;
import java.util.Map;

public class Subject {
    private Map<String, byte[]> content;
    private final String myNameIs; //crutch yes, like you know how to do it better
    private byte[] duration; //array of IDs of present semesters

    public Subject(String myNameIs,byte []grades, byte[] duration) {
        this.myNameIs =myNameIs;
        this.content= new HashMap<String, byte[]>();
        content.put(myNameIs,grades);
        this.duration = duration;
    }

    public Subject(String myNameIs,byte []duration) {
        this.myNameIs =myNameIs;
        this.content= new HashMap<String, byte[]>();
        this.duration = duration;
        byte[] grades = new byte[8];
        content.put(myNameIs,grades);
    }

    public Subject(String myNameIs) {
        this.myNameIs =myNameIs;
        byte[] grades = new byte[8];
        this.content= new HashMap<String, byte[]>();
        content.put(myNameIs,grades);
    }

    public void setDuration(byte[] duration) {
        this.duration = duration;
    }

    public byte[] getDuration() {
        return duration;
    }

    //there must be 8 elements because this array functions as a dictionary
    //semester ID - grade (4 years * 2 sem-s in each)
    public void setGrades(byte[] grades) throws IllegalArgumentException {
        if (grades.length < 9) {
            throw new IllegalArgumentException();
        }
        content.put(myNameIs,grades);
    }

    public void setGrade(byte semID, byte grade) {
        byte[] grades = content.get(myNameIs);
        grades[semID] = grade;
        content.put(myNameIs,grades);
    }

    public String getName() {
        return myNameIs;
    }

    public byte[] getGrades() {return content.get(myNameIs);}

}
