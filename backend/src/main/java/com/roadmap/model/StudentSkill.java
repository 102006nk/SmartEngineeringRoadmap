package com.roadmap.model;

public class StudentSkill {

    private int studentSkillId;
    private int userId;
    private int skillId;
    private String proficiency;
    private String learningStatus;

    public StudentSkill() {
    }

    public StudentSkill(int studentSkillId, int userId, int skillId,
                       String proficiency, String learningStatus) {

        this.studentSkillId = studentSkillId;
        this.userId = userId;
        this.skillId = skillId;
        this.proficiency = proficiency;
        this.learningStatus = learningStatus;
    }

    public int getStudentSkillId() {
        return studentSkillId;
    }

    public void setStudentSkillId(int studentSkillId) {
        this.studentSkillId = studentSkillId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getLearningStatus() {
        return learningStatus;
    }

    public void setLearningStatus(String learningStatus) {
        this.learningStatus = learningStatus;
    }
}