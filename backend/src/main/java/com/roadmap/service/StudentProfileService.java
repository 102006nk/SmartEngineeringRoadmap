
package com.roadmap.service;

import com.roadmap.dao.StudentProfileDAO;
import com.roadmap.model.StudentProfile;
import com.roadmap.util.SessionManager;

import java.sql.SQLException;

public class StudentProfileService {

    private final StudentProfileDAO profileDAO =
            new StudentProfileDAO();

    // Save profile for current user
    public int saveProfile(StudentProfile profile)
            throws SQLException {

        if (profile == null) {
            throw new IllegalArgumentException(
                    "Profile cannot be null.");
        }

        if (profile.getBranchId() <= 0) {
            throw new IllegalArgumentException(
                    "Select a valid branch.");
        }

        if (profile.getSemesterId() <= 0) {
            throw new IllegalArgumentException(
                    "Select a valid semester.");
        }

        if (profile.getDailyStudyHours() <= 0
                || profile.getDailyStudyHours() > 24) {

            throw new IllegalArgumentException(
                    "Daily study hours must be between 1 and 24.");
        }

        int userId = SessionManager.getCurrentUserId();

        profile.setUserId(userId);

        if (profileDAO.profileExists(userId)) {
            throw new IllegalArgumentException(
                    "Profile already exists. Use updateProfile.");
        }

        return profileDAO.insertProfile(profile);
    }

    // Retrieve current user's profile
    public StudentProfile getCurrentProfile()
            throws SQLException {

        int userId = SessionManager.getCurrentUserId();

        return profileDAO.getProfileByUserId(userId);
    }

    // Update current user's profile
    public boolean updateCurrentProfile(StudentProfile profile)
            throws SQLException {

        if (profile == null) {
            throw new IllegalArgumentException(
                    "Profile cannot be null.");
        }

        int userId = SessionManager.getCurrentUserId();

        profile.setUserId(userId);

        if (profile.getBranchId() <= 0
                || profile.getSemesterId() <= 0) {

            throw new IllegalArgumentException(
                    "Branch and semester are required.");
        }

        if (profile.getDailyStudyHours() <= 0
                || profile.getDailyStudyHours() > 24) {

            throw new IllegalArgumentException(
                    "Daily study hours must be between 1 and 24.");
        }

        return profileDAO.updateProfile(profile);
    }

    // Check whether current user has a profile
    public boolean currentProfileExists()
            throws SQLException {

        return profileDAO.profileExists(
                SessionManager.getCurrentUserId());
    }
}
