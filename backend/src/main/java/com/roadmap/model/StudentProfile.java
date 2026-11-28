package com.roadmap.model;

public class StudentProfile {

    private int profileId;
    private int userId;
    private int branchId;
    private int semesterId;
    private String interests;
    private String existingSkills;
    private String careerGoal;
    private double dailyStudyHours;

    public StudentProfile() {
    }

    public StudentProfile(int profileId, int userId, int branchId,
                          int semesterId, String interests,
                          String existingSkills, String careerGoal,
                          double dailyStudyHours) {

        this.profileId = profileId;
        this.userId = userId;
        this.branchId = branchId;
        this.semesterId = semesterId;
        this.interests = interests;
        this.existingSkills = existingSkills;
        this.careerGoal = careerGoal;
        this.dailyStudyHours = dailyStudyHours;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getExistingSkills() {
        return existingSkills;
    }

    public void setExistingSkills(String existingSkills) {
        this.existingSkills = existingSkills;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public double getDailyStudyHours() {
        return dailyStudyHours;
    }

    public void setDailyStudyHours(double dailyStudyHours) {
        this.dailyStudyHours = dailyStudyHours;
    }
}