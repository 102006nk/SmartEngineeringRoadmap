package com.roadmap.model;

import java.time.LocalDate;

public class RoadmapItem {

    private int itemId;
    private int roadmapId;
    private int skillId;
    private String itemTitle;
    private String itemDescription;
    private int sequenceOrder;
    private String status;
    private LocalDate targetDate;

    public RoadmapItem() {
    }

    public RoadmapItem(int itemId, int roadmapId, int skillId,
                       String itemTitle, String itemDescription,
                       int sequenceOrder, String status,
                       LocalDate targetDate) {

        this.itemId = itemId;
        this.roadmapId = roadmapId;
        this.skillId = skillId;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.sequenceOrder = sequenceOrder;
        this.status = status;
        this.targetDate = targetDate;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(int roadmapId) {
        this.roadmapId = roadmapId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(int sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
}