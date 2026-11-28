package com.roadmap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            System.getenv().getOrDefault(
                    "MYSQL_URL",
                    "jdbc:mysql://localhost:3306/smart_engineering_roadmap"
            );

    private static final String USER =
            System.getenv().getOrDefault("MYSQL_USER", "root");

    private static final String PASSWORD =
            System.getenv().getOrDefault("MYSQL_PASSWORD", "");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}