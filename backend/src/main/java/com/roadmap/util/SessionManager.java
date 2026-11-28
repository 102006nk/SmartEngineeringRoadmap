
package com.roadmap.util;

import com.roadmap.model.User;

public class SessionManager {

    private static User currentUser;

    // Store logged-in user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Retrieve logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Retrieve current user ID
    public static int getCurrentUserId() {

        if (currentUser == null) {
            throw new IllegalStateException(
                    "No user is currently logged in.");
        }

        return currentUser.getUserId();
    }

    // Check whether user is logged in
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Check whether current user is admin
    public static boolean isAdmin() {

        return currentUser != null
                && "ADMIN".equals(currentUser.getRole());
    }

    // Clear session
    public static void logout() {
        currentUser = null;
    }
}
