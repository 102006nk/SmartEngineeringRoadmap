package com.roadmap.dao;

import com.roadmap.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SemesterDAO {

    public Map<Integer, String> getAllSemesters() throws SQLException {

        Map<Integer, String> semesters = new LinkedHashMap<>();

        String sql = "SELECT semester_id, semester_number FROM semesters ORDER BY semester_number";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                semesters.put(
                    resultSet.getInt("semester_id"),
                    "Semester " + resultSet.getInt("semester_number")
                );
            }
        }

        return semesters;
    }
}
