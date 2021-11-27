package ru.nsu.fit.lab4;

import java.util.ArrayList;

public class CreditBook {
    private ArrayList<Subject> subjects;
    private ArrayList<Byte> allGrades;
    private byte qualification;
    private float average;
    private int sumGrades, counted;

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

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * adds grades to the list
     *
     * @param start - from which position to add grades
     */
    private void addGrades(int start) {
        if (start == 0) allGrades = new ArrayList<>();
        for (int i = start; i < subjects.size(); i++) {
            for (byte grade : (subjects.get(i)).getGrades()) {
                if (grade != 0) {
                    allGrades.add(grade);
                }
            }
        }
    }

    /**
     * counts sum of a certain amount of grades
     *
     * @param begin - index from which grade to start
     */
    private void setSumGrades(int begin) {
        for (int i = begin; i < allGrades.size(); i++) {
            sumGrades += (allGrades.get(i));
        }
    }

    /**
     * @return float average grade (no need for double precision)
     */
    public float averageGrade() {
        int tmpSize = subjects.size();
        if (allGrades == null) {
            this.addGrades(0);
            sumGrades = 0;
            setSumGrades(0);
            counted = tmpSize;
        } else {
            if (counted == tmpSize) {
                return average;
            } else {
                addGrades(counted);
                setSumGrades(counted);
            }
        }
        average = (float) sumGrades / (allGrades.size());
        return average;
    }

    /**
     * checks if student can get an excellent diploma
     *
     * @return true if yes
     */
    public boolean excellent() {
        if (allGrades == null) {
            addGrades(0);
        }
        int ref = Math.round(0.75f * (allGrades.size()));
        int fives = 0;
        for (byte grade : allGrades) {
            if (grade == 3) return false;
            if (grade == 5) fives++;
        }
        return (ref <= fives) && (qualification == 5);
    }

    /**
     * checks if student will get additional scholarship in semester
     *
     * @param current semester ID
     * @return true if yes
     */
    public boolean highScholarship(byte current) {
        ArrayList<Byte> currentGrades = new ArrayList<>();
        for (Subject sub : subjects) {
            byte[] sems = sub.getDuration();
            byte[] grades = sub.getGrades();
            for (byte sem : sems) {
                if (sem == current) {
                    currentGrades.add(grades[sem]);
                }
            }
        }
        for (byte grade : currentGrades) {
            if (grade != 5) return false;
        }
        return true;
    }

}

