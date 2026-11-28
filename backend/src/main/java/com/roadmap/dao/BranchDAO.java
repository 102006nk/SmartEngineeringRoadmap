package com.roadmap.dao;

import com.roadmap.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BranchDAO {

    public Map<Integer, String> getAllBranches() throws SQLException {

        Map<Integer, String> branches = new LinkedHashMap<>();

        String sql = "SELECT branch_id, branch_name FROM branches ORDER BY branch_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                branches.put(
                    resultSet.getInt("branch_id"),
                    resultSet.getString("branch_name")
                );
            }
        }

        return branches;
    }
}
