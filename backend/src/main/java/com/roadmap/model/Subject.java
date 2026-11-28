package com.roadmap.model;

public class Subject {

    private int subjectId;
    private int branchId;
    private int semesterId;
    private String subjectCode;
    private String subjectName;
    private int credits;
    private String description;

    public Subject() {
    }

    public Subject(int subjectId, int branchId, int semesterId,
                   String subjectCode, String subjectName,
                   int credits, String description) {

        this.subjectId = subjectId;
        this.branchId = branchId;
        this.semesterId = semesterId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}