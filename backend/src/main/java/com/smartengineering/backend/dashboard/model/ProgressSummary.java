package com.smartengineering.backend.dashboard.model;

public class ProgressSummary {

    private int totalItems;
    private int completedItems;
    private int pendingItems;

    private double completionPercentage;

    public ProgressSummary() {
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCompletedItems() {
        return completedItems;
    }

    public void setCompletedItems(int completedItems) {
        this.completedItems = completedItems;
    }

    public int getPendingItems() {
        return pendingItems;
    }

    public void setPendingItems(int pendingItems) {
        this.pendingItems = pendingItems;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
