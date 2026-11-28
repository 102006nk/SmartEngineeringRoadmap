package com.roadmap.model;

import java.time.LocalDate;

public class Roadmap {

    private int roadmapId;
    private int userId;
    private String roadmapTitle;
    private String careerPath;
    private String description;
    private LocalDate startDate;
    private LocalDate targetDate;
    private String status;

    public Roadmap() {
    }

    public Roadmap(int roadmapId, int userId, String roadmapTitle,
                   String careerPath, String description,
                   LocalDate startDate, LocalDate targetDate,
                   String status) {
        this.roadmapId = roadmapId;
        this.userId = userId;
        this.roadmapTitle = roadmapTitle;
        this.careerPath = careerPath;
        this.description = description;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.status = status;
    }

    public int getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(int roadmapId) {
        this.roadmapId = roadmapId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoadmapTitle() {
        return roadmapTitle;
    }

    public void setRoadmapTitle(String roadmapTitle) {
        this.roadmapTitle = roadmapTitle;
    }

    public String getCareerPath() {
        return careerPath;
    }

    public void setCareerPath(String careerPath) {
        this.careerPath = careerPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}