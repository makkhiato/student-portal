package com.group2.studentportal.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Connects to database (SQL Developer)
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "InfoManCRS";
    private static final String PASSWORD = "InfoManCRS"; // <--- CHANGE THIS!

    // Get Access to Database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Loads the Oracle JDBC Driver into memory
            Class.forName("oracle.jdbc.OracleDriver");

            // Connects to the database using the creds above
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle Database Successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver missing! Check your Libraries.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection Failed! Wrong URL, User, or Password.");
            e.printStackTrace();
        }
        return connection;
    }
}