
package com.roadmap.dao;

import com.roadmap.model.RoadmapItem;
import com.roadmap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoadmapItemDAO {

    // Insert roadmap item
    public int insertRoadmapItem(RoadmapItem item)
            throws SQLException {

        String sql = "INSERT INTO roadmap_items "
                + "(roadmap_id, skill_id, item_title, "
                + "item_description, sequence_order, status, "
                + "target_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

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
                statement.setNull(
                        7, Types.DATE);
            }

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Roadmap item ID was not generated.");
    }

    // Retrieve items only if roadmap belongs to user
    public List<RoadmapItem> getItemsByRoadmapId(
            int roadmapId, int userId)
            throws SQLException {

        List<RoadmapItem> items =
                new ArrayList<>();

        String sql = "SELECT ri.item_id, ri.roadmap_id, "
                + "ri.skill_id, ri.item_title, "
                + "ri.item_description, ri.sequence_order, "
                + "ri.status, ri.target_date "
                + "FROM roadmap_items ri "
                + "JOIN roadmaps r "
                + "ON ri.roadmap_id = r.roadmap_id "
                + "WHERE r.roadmap_id = ? "
                + "AND r.user_id = ? "
                + "ORDER BY ri.sequence_order ASC";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, roadmapId);
            statement.setInt(2, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    items.add(mapRoadmapItem(resultSet));
                }
            }
        }

        return items;
    }

    // Retrieve item by ID
    public RoadmapItem getRoadmapItemById(int itemId)
            throws SQLException {

        String sql = "SELECT item_id, roadmap_id, skill_id, "
                + "item_title, item_description, sequence_order, "
                + "status, target_date "
                + "FROM roadmap_items WHERE item_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRoadmapItem(resultSet);
                }
            }
        }

        return null;
    }

    // Update item only when it belongs to current user
    public boolean updateItemStatus(
            int itemId, String status, int userId)
            throws SQLException {

        String sql = "UPDATE roadmap_items ri "
                + "JOIN roadmaps r "
                + "ON ri.roadmap_id = r.roadmap_id "
                + "SET ri.status = ? "
                + "WHERE ri.item_id = ? "
                + "AND r.user_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, itemId);
            statement.setInt(3, userId);

            return statement.executeUpdate() > 0;
        }
    }

    // Retrieve pending items only for owned roadmap
    public List<RoadmapItem> getPendingItemsByRoadmapId(
            int roadmapId, int userId)
            throws SQLException {

        List<RoadmapItem> items =
                new ArrayList<>();

        String sql = "SELECT ri.item_id, ri.roadmap_id, "
                + "ri.skill_id, ri.item_title, "
                + "ri.item_description, ri.sequence_order, "
                + "ri.status, ri.target_date "
                + "FROM roadmap_items ri "
                + "JOIN roadmaps r "
                + "ON ri.roadmap_id = r.roadmap_id "
                + "WHERE r.roadmap_id = ? "
                + "AND r.user_id = ? "
                + "AND ri.status = 'PENDING' "
                + "ORDER BY ri.sequence_order ASC";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, roadmapId);
            statement.setInt(2, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    items.add(mapRoadmapItem(resultSet));
                }
            }
        }

        return items;
    }

    // Map database row
    private RoadmapItem mapRoadmapItem(
            ResultSet resultSet) throws SQLException {

        Date targetDate =
                resultSet.getDate("target_date");

        return new RoadmapItem(
                resultSet.getInt("item_id"),
                resultSet.getInt("roadmap_id"),
                resultSet.getInt("skill_id"),
                resultSet.getString("item_title"),
                resultSet.getString("item_description"),
                resultSet.getInt("sequence_order"),
                resultSet.getString("status"),
                targetDate == null
                        ? null
                        : targetDate.toLocalDate()
        );
    }
}
