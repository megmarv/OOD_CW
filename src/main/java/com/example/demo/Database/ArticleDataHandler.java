package com.example.demo.Database;

import com.example.demo.CoreModules.Article;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// class to perform CRUD operations for the articles table in the database
public class ArticleDataHandler {

    private static final String DB_URL = "jdbc:h2:./database/mindpulseDB";
    private static final String USER = "megmrv";
    private static final String PASSWORD = "naziqsucks";

    public void insertArticle(Article article) {
        String query = "INSERT INTO Articles (CategoryID, Title, AuthorName, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, article.getCategoryId());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getAuthorName());
            pstmt.setString(4, article.getContent());
            pstmt.executeUpdate();
            System.out.println("Article inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Article getArticleWithID(int id) {
        String query = "SELECT * FROM Articles WHERE ArticleID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Article(
                        rs.getInt("ArticleID"),
                        rs.getInt("CategoryID"),
                        rs.getString("Title"),
                        rs.getString("AuthorName"),
                        rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM Articles";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("ArticleID"),
                        rs.getInt("CategoryID"),
                        rs.getString("Title"),
                        rs.getString("AuthorName"),
                        rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public void deleteArticle(int id) {
        String query = "DELETE FROM Articles WHERE ArticleID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Article deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
