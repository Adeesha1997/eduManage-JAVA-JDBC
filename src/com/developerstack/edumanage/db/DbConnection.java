package com.developerstack.edumanage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Singleton is Creational Design Pattern

    // Rule No 1 = That is Instance variable
    private static DbConnection dbConnection; // This variable DEFAULT NULL

    private Connection connection;

    // Rule No 2 = Using This Singleton class cant create new objects.to apply this CONSTRUCTOR setup as PRIVATE
    private DbConnection() throws ClassNotFoundException, SQLException {
        // Load the Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Create the Connection
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lms3",
                "root",
                "1234");
    }

    // Rule No 3 = Give the Public Static Variable To Access Class From Outside
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
