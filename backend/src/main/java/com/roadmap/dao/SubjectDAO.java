
package com.roadmap.dao;

import com.roadmap.model.Subject;
import com.roadmap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // Retrieve subjects for a student's branch and semester
    public List<Subject> getSubjectsByBranchAndSemester(
            int branchId, int semesterId) throws SQLException {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT subject_id, branch_id, semester_id, "
                   + "subject_code, subject_name, credits, description "
                   + "FROM branch_semester_subjects "
                   + "WHERE branch_id = ? AND semester_id = ? "
                   + "ORDER BY subject_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, branchId);
            statement.setInt(2, semesterId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    subjects.add(new Subject(
                            resultSet.getInt("subject_id"),
                            resultSet.getInt("branch_id"),
                            resultSet.getInt("semester_id"),
                            resultSet.getString("subject_code"),
                            resultSet.getString("subject_name"),
                            resultSet.getInt("credits"),
                            resultSet.getString("description")
                    ));
                }
            }
        }

        return subjects;
    }

    // Retrieve subject by ID
    public Subject getSubjectById(int subjectId)
            throws SQLException {

        String sql = "SELECT subject_id, branch_id, semester_id, "
                   + "subject_code, subject_name, credits, description "
                   + "FROM branch_semester_subjects "
                   + "WHERE subject_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subjectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Subject(
                            resultSet.getInt("subject_id"),
                            resultSet.getInt("branch_id"),
                            resultSet.getInt("semester_id"),
                            resultSet.getString("subject_code"),
                            resultSet.getString("subject_name"),
                            resultSet.getInt("credits"),
                            resultSet.getString("description")
                    );
                }
            }
        }

        return null;
    }
}
