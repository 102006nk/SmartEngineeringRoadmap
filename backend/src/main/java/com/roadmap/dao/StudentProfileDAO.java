
package com.roadmap.dao;

import com.roadmap.model.StudentProfile;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentProfileDAO {

    // Insert a student profile
    public int insertProfile(StudentProfile profile)
            throws SQLException {

        String sql = "INSERT INTO student_profiles "
                   + "(user_id, branch_id, semester_id, interests, "
                   + "existing_skills, career_goal, daily_study_hours) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, profile.getUserId());
            statement.setInt(2, profile.getBranchId());
            statement.setInt(3, profile.getSemesterId());
            statement.setString(4, profile.getInterests());
            statement.setString(5, profile.getExistingSkills());
            statement.setString(6, profile.getCareerGoal());
            statement.setDouble(7, profile.getDailyStudyHours());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(
                        "Profile insertion failed.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException("Profile ID was not generated.");
    }

    // Retrieve profile by user ID
    public StudentProfile getProfileByUserId(int userId)
            throws SQLException {

        String sql = "SELECT profile_id, user_id, branch_id, "
                   + "semester_id, interests, existing_skills, "
                   + "career_goal, daily_study_hours "
                   + "FROM student_profiles WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapProfile(resultSet);
                }
            }
        }

        return null;
    }

    // Update an existing profile
    public boolean updateProfile(StudentProfile profile)
            throws SQLException {

        String sql = "UPDATE student_profiles SET "
                   + "branch_id = ?, semester_id = ?, interests = ?, "
                   + "existing_skills = ?, career_goal = ?, "
                   + "daily_study_hours = ? "
                   + "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, profile.getBranchId());
            statement.setInt(2, profile.getSemesterId());
            statement.setString(3, profile.getInterests());
            statement.setString(4, profile.getExistingSkills());
            statement.setString(5, profile.getCareerGoal());
            statement.setDouble(6, profile.getDailyStudyHours());
            statement.setInt(7, profile.getUserId());

            return statement.executeUpdate() > 0;
        }
    }

    // Check whether a profile exists for a user
    public boolean profileExists(int userId)
            throws SQLException {

        String sql = "SELECT 1 FROM student_profiles "
                   + "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Convert database row into StudentProfile
    private StudentProfile mapProfile(ResultSet resultSet)
            throws SQLException {

        return new StudentProfile(
                resultSet.getInt("profile_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("branch_id"),
                resultSet.getInt("semester_id"),
                resultSet.getString("interests"),
                resultSet.getString("existing_skills"),
                resultSet.getString("career_goal"),
                resultSet.getDouble("daily_study_hours")
        );
    }
}

