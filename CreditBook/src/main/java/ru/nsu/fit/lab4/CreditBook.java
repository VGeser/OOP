package ru.nsu.fit.lab4;

import java.util.ArrayList;

public class CreditBook {
    private ArrayList<Subject> subjects;
    private final ArrayList<Byte> allGrades = new ArrayList<>();
    private byte qualification;

    public CreditBook() {
        subjects = new ArrayList<>();
    }

    public CreditBook(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setQualification(byte qualification) {
        this.qualification = qualification;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    private int setAllGrades() {
        int res=0;
        for (Subject sub : subjects) {
            byte[] grades = sub.getGrades();
            for (byte grade : grades) {
                if(grade!=0){
                    res+=grade;
                    allGrades.add(grade);
                }
            }
        }
        return res;
    }

    public double averageGrade() {
        int sum = setAllGrades();
        return (double) sum/(allGrades.size());
    }

    public boolean excellent() {
        int ref = Math.round(0.75f*(allGrades.size())*5);
        int fives=0;
        for (byte grade:allGrades) {
            if(grade==3)return false;
            if(grade==5)fives++;
        }
        return (ref <= fives) && (qualification == 5);
    }

    public boolean highScholarship(byte current) {
        ArrayList<Byte> currentGrades = new ArrayList<>();
        for (Subject sub:subjects) {
            byte[] sems = sub.getDuration();
            byte[] grades = sub.getGrades();
            for (byte sem:sems) {
                if(sem==current){
                    currentGrades.add(grades[sem]);
                }
            }
        }
        for (byte grade:currentGrades) {
            if(grade==3)return false;
        }
        return true;
    }

}

