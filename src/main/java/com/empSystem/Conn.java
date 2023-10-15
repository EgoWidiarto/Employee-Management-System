package com.empSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/emp_management";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Could not find JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Could not connect to database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("Connected to PostgreSQL database!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Could not connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
