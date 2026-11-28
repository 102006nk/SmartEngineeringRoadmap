
package com.roadmap.dao;

import com.roadmap.model.Roadmap;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RoadmapDAO {

    // Insert a roadmap
    public int insertRoadmap(Roadmap roadmap)
            throws SQLException {

        String sql = "INSERT INTO roadmaps "
                   + "(user_id, roadmap_title, career_path, "
                   + "description, start_date, target_date, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, roadmap.getUserId());
            statement.setString(2, roadmap.getRoadmapTitle());
            statement.setString(3, roadmap.getCareerPath());
            statement.setString(4, roadmap.getDescription());

            statement.setDate(5, Date.valueOf(roadmap.getStartDate()));
            statement.setDate(6, Date.valueOf(roadmap.getTargetDate()));

            statement.setString(7, roadmap.getStatus());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Roadmap insertion failed.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException("Roadmap ID was not generated.");
    }

    // Retrieve a roadmap by ID
    public Roadmap getRoadmapById(int roadmapId)
            throws SQLException {

        String sql = "SELECT roadmap_id, user_id, roadmap_title, "
                   + "career_path, description, start_date, "
                   + "target_date, status "
                   + "FROM roadmaps WHERE roadmap_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roadmapId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRoadmap(resultSet);
                }
            }
        }

        return null;
    }

    // Retrieve all roadmaps for a user
    public List<Roadmap> getRoadmapsByUserId(int userId)
            throws SQLException {

        List<Roadmap> roadmaps = new ArrayList<>();

        String sql = "SELECT roadmap_id, user_id, roadmap_title, "
                   + "career_path, description, start_date, "
                   + "target_date, status "
                   + "FROM roadmaps WHERE user_id = ? "
                   + "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    roadmaps.add(mapRoadmap(resultSet));
                }
            }
        }

        return roadmaps;
    }

    // Update roadmap status
    public boolean updateRoadmapStatus(
            int roadmapId, String status) throws SQLException {

        String sql = "UPDATE roadmaps SET status = ? "
                   + "WHERE roadmap_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, roadmapId);

            return statement.executeUpdate() > 0;
        }
    }

    // Retrieve latest active roadmap
    public Roadmap getActiveRoadmapByUserId(int userId)
            throws SQLException {

        String sql = "SELECT roadmap_id, user_id, roadmap_title, "
                   + "career_path, description, start_date, "
                   + "target_date, status "
                   + "FROM roadmaps "
                   + "WHERE user_id = ? AND status = 'ACTIVE' "
                   + "ORDER BY created_at DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRoadmap(resultSet);
                }
            }
        }

        return null;
    }

    // Convert database row into Roadmap
    private Roadmap mapRoadmap(ResultSet resultSet)
            throws SQLException {

        return new Roadmap(
                resultSet.getInt("roadmap_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("roadmap_title"),
                resultSet.getString("career_path"),
                resultSet.getString("description"),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("target_date").toLocalDate(),
                resultSet.getString("status")
        );
    }
}
