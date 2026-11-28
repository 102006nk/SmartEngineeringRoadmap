package com.smartengineering.backend.dashboard.service;

import com.smartengineering.backend.dashboard.dao.DashboardDAO;
import com.smartengineering.backend.dashboard.model.DashboardSummary;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DashboardDAO dashboardDAO;
    private final ProgressService progressService;

    public DashboardService(
            DashboardDAO dashboardDAO,
            ProgressService progressService) {

        this.dashboardDAO = dashboardDAO;
        this.progressService = progressService;
    }

    // Get all dashboard statistics for a student
    public DashboardSummary getDashboardSummary(long userId) {

        DashboardSummary summary =
                dashboardDAO.getDashboardSummary(userId);

        double roadmapProgress =
                progressService.getRoadmapProgress(userId)
                        .getCompletionPercentage();

        double taskProgress =
                progressService.getTaskProgress(userId)
                        .getCompletionPercentage();

        double resourceProgress =
                progressService.getResourceProgress(userId)
                        .getCompletionPercentage();

        // Calculate average overall progress
        double overallProgress =
                (roadmapProgress + taskProgress + resourceProgress) / 3.0;

        summary.setOverallProgress(
                Math.round(overallProgress * 100.0) / 100.0
        );

        return summary;
    }
}