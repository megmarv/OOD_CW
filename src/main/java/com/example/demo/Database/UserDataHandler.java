package com.example.demo.Database;

import com.example.demo.CoreModules.Article;
import com.example.demo.CoreModules.ArticleInteractionData;
import com.example.demo.CoreModules.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataHandler {

    private static final String DB_URL = "jdbc:h2:./database/mindpulseDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public void insertUser(User user) {
        String query = "INSERT INTO Users (Fname, Lname, Age, email, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUsername());
            pstmt.setString(6, user.getPassword());
            pstmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserWithID(int id) {
        String query = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getInt("Age"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("UserID"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getInt("Age"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUserDetails(User user) {
        String query = "UPDATE Users SET Fname = ?, Lname = ?, Age = ?, email = ?, username = ?, password = ? WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUsername());
            pstmt.setString(6, user.getPassword());
            pstmt.setInt(7, user.getUserId());
            pstmt.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArticleInteractionData> getUserHistory(int userId) {
        List<ArticleInteractionData> userHistory = new ArrayList<>();
        String query = "SELECT a.ArticleID, a.CategoryID, a.Title, a.AuthorName, a.Content, " +
                "ai.InteractionID, ai.UserID, ai.Rating, ai.TimeTaken " +
                "FROM Articles a " +
                "JOIN ArticleInteractions ai ON a.ArticleID = ai.ArticleID " +
                "WHERE ai.UserID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int articleId = rs.getInt("ArticleID");
                int categoryId = rs.getInt("CategoryID");
                String title = rs.getString("Title");
                String authorName = rs.getString("AuthorName");
                String content = rs.getString("Content");
                int interactionId = rs.getInt("InteractionID");
                String rating = rs.getString("Rating");
                Time timeTaken = rs.getTime("TimeTaken");

                // Create an Article object
                Article article = new Article(articleId, categoryId, title, authorName, content);

                // Create an ArticleInteractionData object that references the Article
                ArticleInteractionData interactionData = new ArticleInteractionData(article, interactionId, userId, rating, timeTaken);
                userHistory.add(interactionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userHistory;
    }



}