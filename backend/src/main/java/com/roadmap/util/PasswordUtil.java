
package com.roadmap.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a plain password
    public static String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Verify a plain password against a stored hash
    public static boolean verifyPassword(
            String password, String storedHash) {

        if (password == null || storedHash == null) {
            return false;
        }

        return BCrypt.checkpw(password, storedHash);
    }
}
