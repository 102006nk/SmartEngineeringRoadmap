
package com.roadmap.service;

import com.roadmap.dao.UserDAO;
import com.roadmap.model.User;
import com.roadmap.util.PasswordUtil;

import com.roadmap.util.SessionManager;


import java.sql.SQLException;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    // Register a new student
    public int registerStudent(
            String name, String email, String password)
            throws SQLException {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Email cannot be empty.");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters.");
        }

        String normalizedEmail = email.trim().toLowerCase();

        if (userDAO.emailExists(normalizedEmail)) {
            throw new IllegalArgumentException(
                    "Email is already registered.");
        }

        String passwordHash = PasswordUtil.hashPassword(password);

        User user = new User(
                name.trim(),
                normalizedEmail,
                passwordHash,
                "STUDENT"
        );

        return userDAO.insertUser(user);
    }

    // Authenticate user
    public User login(String email, String password)
            throws SQLException {

        if (email == null || password == null) {
            return null;
        }

        String normalizedEmail = email.trim().toLowerCase();

        User user = userDAO.getUserByEmail(normalizedEmail);

        if (user == null) {
            return null;
        }

        boolean passwordMatches = PasswordUtil.verifyPassword(
                password, user.getPasswordHash());

       
if (!passwordMatches) {
    return null;
}

SessionManager.setCurrentUser(user);

return user;

    }
}
