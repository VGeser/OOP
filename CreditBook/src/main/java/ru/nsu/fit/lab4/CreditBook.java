package ru.nsu.fit.lab4;

import java.util.ArrayList;

public class CreditBook {
    private ArrayList<Subject> subjects;
    private ArrayList<Byte> allGrades;
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

    /**
     * initializes class filed for all grades
     * @return sum of all grades
     */
    private int setAllGrades() {
        allGrades = new ArrayList<>();
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

    /**
     * @return float average grade (not double to save space)
     */
    public float averageGrade() {
        int sum = setAllGrades();
        return (float) sum/(allGrades.size());
    }

    /**
     * checks if student can get an excellent diploma
     * @return true if yes
     */
    public boolean excellent() {
        if(allGrades==null){
            setAllGrades();
        }
        int ref = Math.round(0.75f*(allGrades.size())*5);
        int fives=0;
        for (byte grade:allGrades) {
            if(grade==3)return false;
            if(grade==5)fives++;
        }
        return (ref <= fives) && (qualification == 5);
    }

    /**
     * checks if student will get additional scholarship in semester
     * @param current semester ID
     * @return true if yes
     */
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

