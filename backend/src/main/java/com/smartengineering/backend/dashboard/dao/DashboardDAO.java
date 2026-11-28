package com.smartengineering.backend.dashboard.dao;

import com.smartengineering.backend.dashboard.model.DashboardSummary;
import com.smartengineering.backend.dashboard.model.ProgressSummary;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardDAO {

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection
    public DashboardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all dashboard statistics for one student
    public DashboardSummary getDashboardSummary(long userId) {

        String sql = """
            SELECT
                (SELECT COUNT(*)
                 FROM roadmaps
                 WHERE user_id = ?) AS total_roadmaps,

                (SELECT COUNT(*)
                 FROM roadmaps
                 WHERE user_id = ?
                 AND status = 'COMPLETED') AS completed_roadmaps,

                (SELECT COUNT(*)
                 FROM study_tasks
                 WHERE user_id = ?) AS total_tasks,

                (SELECT COUNT(*)
                 FROM study_tasks
                 WHERE user_id = ?
                 AND status = 'COMPLETED') AS completed_tasks,

                (SELECT COUNT(*)
                 FROM study_tasks
                 WHERE user_id = ?
                 AND status = 'PENDING') AS pending_tasks,

                (SELECT COUNT(*)
                 FROM student_resource_progress
                 WHERE user_id = ?) AS total_resources,

                (SELECT COUNT(*)
                 FROM student_resource_progress
                 WHERE user_id = ?
                 AND completion_status = 'COMPLETED') AS completed_resources
            """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            DashboardSummary summary = new DashboardSummary();

            summary.setTotalRoadmaps(rs.getInt("total_roadmaps"));
            summary.setCompletedRoadmaps(rs.getInt("completed_roadmaps"));

            summary.setTotalTasks(rs.getInt("total_tasks"));
            summary.setCompletedTasks(rs.getInt("completed_tasks"));
            summary.setPendingTasks(rs.getInt("pending_tasks"));

            summary.setTotalResources(rs.getInt("total_resources"));
            summary.setCompletedResources(rs.getInt("completed_resources"));

            return summary;

        },
        userId,
        userId,
        userId,
        userId,
        userId,
        userId,
        userId);
    }

    // Get roadmap item progress
    public ProgressSummary getRoadmapProgress(long userId) {

        String sql = """
            SELECT
                COUNT(*) AS total_items,
                COALESCE(SUM(
                    CASE WHEN ri.status = 'COMPLETED'
                    THEN 1 ELSE 0 END
                ), 0) AS completed_items
            FROM roadmap_items ri
            INNER JOIN roadmaps r
                ON ri.roadmap_id = r.roadmap_id
            WHERE r.user_id = ?
            """;

        return getProgressSummary(sql, userId);
    }

    // Get study task progress
    public ProgressSummary getTaskProgress(long userId) {

        String sql = """
            SELECT
                COUNT(*) AS total_items,
                COALESCE(SUM(
                    CASE WHEN status = 'COMPLETED'
                    THEN 1 ELSE 0 END
                ), 0) AS completed_items
            FROM study_tasks
            WHERE user_id = ?
            """;

        return getProgressSummary(sql, userId);
    }

    // Get learning resource progress
    public ProgressSummary getResourceProgress(long userId) {

        String sql = """
            SELECT
                COUNT(*) AS total_items,
                COALESCE(SUM(
                    CASE WHEN completion_status = 'COMPLETED'
                    THEN 1 ELSE 0 END
                ), 0) AS completed_items
            FROM student_resource_progress
            WHERE user_id = ?
            """;

        return getProgressSummary(sql, userId);
    }

    // Common method for calculating progress counts
    private ProgressSummary getProgressSummary(
            String sql, long userId) {

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            ProgressSummary progress = new ProgressSummary();

            int total = rs.getInt("total_items");
            int completed = rs.getInt("completed_items");

            progress.setTotalItems(total);
            progress.setCompletedItems(completed);
            progress.setPendingItems(total - completed);

            double percentage = total == 0
                    ? 0.0
                    : (completed * 100.0) / total;

            progress.setCompletionPercentage(percentage);

            return progress;

        }, userId);
    }
}