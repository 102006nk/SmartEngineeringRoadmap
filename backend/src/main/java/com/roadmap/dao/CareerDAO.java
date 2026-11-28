package com.roadmap.dao;

import com.roadmap.model.Career;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CareerDAO {

    public List<Career> getAllCareers() throws SQLException {

        List<Career> careers = new ArrayList<>();

        String sql = "SELECT career_id, career_name, description FROM careers";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Career career = new Career(
                        resultSet.getInt("career_id"),
                        resultSet.getString("career_name"),
                        resultSet.getString("description")
                );

                careers.add(career);
            }
        }

        return careers;
    }

    public Career getCareerById(int careerId) throws SQLException {

        String sql = "SELECT career_id, career_name, description "
                   + "FROM careers WHERE career_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, careerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Career(
                            resultSet.getInt("career_id"),
                            resultSet.getString("career_name"),
                            resultSet.getString("description")
                    );
                }
            }
        }

        return null;
    }
}