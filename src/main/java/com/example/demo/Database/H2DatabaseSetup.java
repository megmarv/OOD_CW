package com.example.demo.Database;

import java.sql.*;

public class H2DatabaseSetup implements DatabaseHandler {

    private static final String DB_URL = "jdbc:h2:./database/mindpulseDB"; // Path to H2 DB file
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create Users table if it doesn't exist
            String createUserTable = "CREATE TABLE IF NOT EXISTS Users (" +
                    "UserID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Fname VARCHAR(20) NOT NULL, " +
                    "Lname VARCHAR(20) NOT NULL, " +
                    "Age INT, " +
                    "email VARCHAR(50), " +
                    "username VARCHAR(20), " +
                    "password VARCHAR(20))";
            stmt.execute(createUserTable);

            // Create Categories table if it doesn't exist
            String createCategoryTable = "CREATE TABLE IF NOT EXISTS Categories (" +
                    "CategoryID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "CategoryName VARCHAR(30), " +
                    "Description VARCHAR(60))";
            stmt.execute(createCategoryTable);

            // Create Articles table if it doesn't exist
            String createArticlesTable = "CREATE TABLE IF NOT EXISTS Articles (" +
                    "ArticleID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "CategoryID INT, " +
                    "Title VARCHAR(100), " +
                    "AuthorName VARCHAR(100), " +
                    "content TEXT, " +
                    "FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID))";
            stmt.execute(createArticlesTable);

            // Create ArticleInteractions table if it doesn't exist
            String createArticleInteractionsTable = "CREATE TABLE IF NOT EXISTS ArticleInteractions (" +
                    "InteractionID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "ArticleID INT, " +
                    "UserID INT, " +
                    "Rating VARCHAR(10) CHECK (Rating IN ('like', 'dislike')), " +
                    "TimeTaken TIME, " + // Time taken to read in HH:MM:SS format
                    "FOREIGN KEY (ArticleID) REFERENCES Articles(ArticleID), " +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID))";
            stmt.execute(createArticleInteractionsTable);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertSampleData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Check if data already exists in Users table
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM Users");
            rs.next();
            int userCount = rs.getInt("count");

            if (userCount == 0) { // Insert only if no data exists
                String insertUser = "INSERT INTO Users (UserID, Fname, Lname, Age, email, username, password) " +
                        "VALUES (1, 'John', 'Doe', 30, 'john@example.com', 'john_doe', 'password123')";
                stmt.executeUpdate(insertUser);

                String insertCategory = "INSERT INTO Categories (CategoryID, CategoryName, Description) " +
                        "VALUES (1, 'Technology', 'Tech-related articles')";
                stmt.executeUpdate(insertCategory);

                String insertArticle = "INSERT INTO Articles (ArticleID, CategoryID, Title, AuthorName, content) " +
                        "VALUES (1, 1, 'AI for Good', 'Jane Smith', 'This is an article about AI...')";
                stmt.executeUpdate(insertArticle);

                String insertInteraction = "INSERT INTO ArticleInteractions (ArticleID, UserID, Rating, TimeTaken) " +
                        "VALUES (1, 1, 'like', '00:03:15')";
                stmt.executeUpdate(insertInteraction);

                System.out.println("Sample data inserted.");
            } else {
                System.out.println("Sample data already exists. Skipping insertion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods for interacting with the database (e.g., CRUD operations) can be added here
}