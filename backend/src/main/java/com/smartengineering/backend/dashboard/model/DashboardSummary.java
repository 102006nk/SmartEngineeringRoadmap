package com.smartengineering.backend.dashboard.model;

public class DashboardSummary {

    private int totalRoadmaps;
    private int completedRoadmaps;

    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;

    private int totalResources;
    private int completedResources;

    private double overallProgress;

    public DashboardSummary() {
    }

    public int getTotalRoadmaps() {
        return totalRoadmaps;
    }

    public void setTotalRoadmaps(int totalRoadmaps) {
        this.totalRoadmaps = totalRoadmaps;
    }

    public int getCompletedRoadmaps() {
        return completedRoadmaps;
    }

    public void setCompletedRoadmaps(int completedRoadmaps) {
        this.completedRoadmaps = completedRoadmaps;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public int getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(int totalResources) {
        this.totalResources = totalResources;
    }

    public int getCompletedResources() {
        return completedResources;
    }

    public void setCompletedResources(int completedResources) {
        this.completedResources = completedResources;
    }

    public double getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(double overallProgress) {
        this.overallProgress = overallProgress;
    }
}
