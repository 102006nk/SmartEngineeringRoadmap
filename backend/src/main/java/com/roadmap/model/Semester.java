package com.roadmap.model;

public class Semester {

    private int semesterId;
    private int semesterNumber;

    public Semester() {
    }

    public Semester(int semesterId, int semesterNumber) {
        this.semesterId = semesterId;
        this.semesterNumber = semesterNumber;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }
}