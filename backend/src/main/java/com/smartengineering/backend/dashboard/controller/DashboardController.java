package com.smartengineering.backend.dashboard.controller;

import com.smartengineering.backend.dashboard.model.DashboardSummary;
import com.smartengineering.backend.dashboard.model.ProgressSummary;
import com.smartengineering.backend.dashboard.service.DashboardService;
import com.smartengineering.backend.dashboard.service.ProgressService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ProgressService progressService;

    public DashboardController(
            DashboardService dashboardService,
            ProgressService progressService) {

        this.dashboardService = dashboardService;
        this.progressService = progressService;
    }

    // Get complete dashboard summary
    @GetMapping("/{userId}")
    public DashboardSummary getDashboardSummary(
            @PathVariable long userId) {

        return dashboardService.getDashboardSummary(userId);
    }

    // Get roadmap progress
    @GetMapping("/{userId}/roadmap")
    public ProgressSummary getRoadmapProgress(
            @PathVariable long userId) {

        return progressService.getRoadmapProgress(userId);
    }

    // Get study task progress
    @GetMapping("/{userId}/tasks")
    public ProgressSummary getTaskProgress(
            @PathVariable long userId) {

        return progressService.getTaskProgress(userId);
    }

    // Get learning resource progress
    @GetMapping("/{userId}/resources")
    public ProgressSummary getResourceProgress(
            @PathVariable long userId) {

        return progressService.getResourceProgress(userId);
    }
}