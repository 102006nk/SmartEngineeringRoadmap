package com.roadmap.model;

public class Skill {

    private int skillId;
    private String skillName;
    private String skillCategory;
    private String description;

    public Skill() {
    }

    public Skill(int skillId, String skillName,
                 String skillCategory, String description) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
        this.description = description;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}