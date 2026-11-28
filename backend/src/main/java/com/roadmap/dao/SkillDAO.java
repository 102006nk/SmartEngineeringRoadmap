package com.roadmap.dao;

import com.roadmap.model.Skill;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    // Retrieve all skills
    public List<Skill> getAllSkills() throws SQLException {

        List<Skill> skills = new ArrayList<>();

        String sql = "SELECT skill_id, skill_name, skill_category, description "
                   + "FROM skills ORDER BY skill_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Skill skill = new Skill(
                        resultSet.getInt("skill_id"),
                        resultSet.getString("skill_name"),
                        resultSet.getString("skill_category"),
                        resultSet.getString("description")
                );

                skills.add(skill);
            }
        }

        return skills;
    }

    // Retrieve a skill by its ID
    public Skill getSkillById(int skillId) throws SQLException {

        String sql = "SELECT skill_id, skill_name, skill_category, description "
                   + "FROM skills WHERE skill_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Skill(
                            resultSet.getInt("skill_id"),
                            resultSet.getString("skill_name"),
                            resultSet.getString("skill_category"),
                            resultSet.getString("description")
                    );
                }
            }
        }

        return null;
    }

    // Retrieve skills by category
    public List<Skill> getSkillsByCategory(String category)
            throws SQLException {

        List<Skill> skills = new ArrayList<>();

        String sql = "SELECT skill_id, skill_name, skill_category, description "
                   + "FROM skills WHERE skill_category = ? "
                   + "ORDER BY skill_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Skill skill = new Skill(
                            resultSet.getInt("skill_id"),
                            resultSet.getString("skill_name"),
                            resultSet.getString("skill_category"),
                            resultSet.getString("description")
                    );

                    skills.add(skill);
                }
            }
        }

        return skills;
    }
}