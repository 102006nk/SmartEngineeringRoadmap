
package com.roadmap.util;

public class AccessControl {

    // Ensure a user is logged in
    public static void requireLogin() {

        if (!SessionManager.isLoggedIn()) {
            throw new SecurityException(
                    "Please log in first.");
        }
    }

    // Ensure the user is an admin
    public static void requireAdmin() {

        requireLogin();

        if (!SessionManager.isAdmin()) {
            throw new SecurityException(
                    "Admin access is required.");
        }
    }

    // Ensure the user is a student
    public static void requireStudent() {

        requireLogin();

        if (SessionManager.isAdmin()) {
            throw new SecurityException(
                    "Student access is required.");
        }
    }
}
