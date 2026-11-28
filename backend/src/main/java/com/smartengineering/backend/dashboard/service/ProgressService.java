package com.smartengineering.backend.dashboard.service;

import com.smartengineering.backend.dashboard.dao.DashboardDAO;
import com.smartengineering.backend.dashboard.model.ProgressSummary;

import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    private final DashboardDAO dashboardDAO;

    public ProgressService(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    // Get roadmap progress
    public ProgressSummary getRoadmapProgress(long userId) {
        return dashboardDAO.getRoadmapProgress(userId);
    }

    // Get study task progress
    public ProgressSummary getTaskProgress(long userId) {
        return dashboardDAO.getTaskProgress(userId);
    }

    // Get resource progress
    public ProgressSummary getResourceProgress(long userId) {
        return dashboardDAO.getResourceProgress(userId);
    }

    // Calculate overall progress
    public double calculateOverallProgress(long userId) {

        double roadmap =
                getRoadmapProgress(userId).getCompletionPercentage();

        double tasks =
                getTaskProgress(userId).getCompletionPercentage();

        double resources =
                getResourceProgress(userId).getCompletionPercentage();

        return Math.round(
                ((roadmap + tasks + resources) / 3.0) * 100.0
        ) / 100.0;
    }
}