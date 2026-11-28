
package com.roadmap.dao;

import com.roadmap.model.RoadmapItem;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RoadmapItemDAO {

    // Insert a roadmap item
    public int insertRoadmapItem(RoadmapItem item)
            throws SQLException {

        String sql = "INSERT INTO roadmap_items "
                   + "(roadmap_id, skill_id, item_title, "
                   + "item_description, sequence_order, status, "
                   + "target_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, item.getRoadmapId());
            statement.setInt(2, item.getSkillId());
            statement.setString(3, item.getItemTitle());
            statement.setString(4, item.getItemDescription());
            statement.setInt(5, item.getSequenceOrder());
            statement.setString(6, item.getStatus());

            if (item.getTargetDate() != null) {
                statement.setDate(
                        7, Date.valueOf(item.getTargetDate()));
            } else {
                statement.setNull(7, java.sql.Types.DATE);
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(
                        "Roadmap item insertion failed.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Roadmap item ID was not generated.");
    }

    // Retrieve all items for a roadmap
    public List<RoadmapItem> getItemsByRoadmapId(int roadmapId)
            throws SQLException {

        List<RoadmapItem> items = new ArrayList<>();

        String sql = "SELECT item_id, roadmap_id, skill_id, "
                   + "item_title, item_description, sequence_order, "
                   + "status, target_date "
                   + "FROM roadmap_items WHERE roadmap_id = ? "
                   + "ORDER BY sequence_order ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roadmapId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    items.add(mapRoadmapItem(resultSet));
                }
            }
        }

        return items;
    }

    // Retrieve a roadmap item by ID
    public RoadmapItem getRoadmapItemById(int itemId)
            throws SQLException {

        String sql = "SELECT item_id, roadmap_id, skill_id, "
                   + "item_title, item_description, sequence_order, "
                   + "status, target_date "
                   + "FROM roadmap_items WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRoadmapItem(resultSet);
                }
            }
        }

        return null;
    }

    // Update roadmap item status
    public boolean updateItemStatus(
            int itemId, String status) throws SQLException {

        String sql = "UPDATE roadmap_items SET status = ? "
                   + "WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, itemId);

            return statement.executeUpdate() > 0;
        }
    }

    // Retrieve pending items
    public List<RoadmapItem> getPendingItemsByRoadmapId(
            int roadmapId) throws SQLException {

        List<RoadmapItem> items = new ArrayList<>();

        String sql = "SELECT item_id, roadmap_id, skill_id, "
                   + "item_title, item_description, sequence_order, "
                   + "status, target_date "
                   + "FROM roadmap_items "
                   + "WHERE roadmap_id = ? AND status = 'PENDING' "
                   + "ORDER BY sequence_order ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roadmapId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    items.add(mapRoadmapItem(resultSet));
                }
            }
        }

        return items;
    }

    // Convert database row into RoadmapItem
    private RoadmapItem mapRoadmapItem(ResultSet resultSet)
            throws SQLException {

        Date targetDate = resultSet.getDate("target_date");

        return new RoadmapItem(
                resultSet.getInt("item_id"),
                resultSet.getInt("roadmap_id"),
                resultSet.getInt("skill_id"),
                resultSet.getString("item_title"),
                resultSet.getString("item_description"),
                resultSet.getInt("sequence_order"),
                resultSet.getString("status"),
                targetDate == null ? null : targetDate.toLocalDate()
        );
    }
}
