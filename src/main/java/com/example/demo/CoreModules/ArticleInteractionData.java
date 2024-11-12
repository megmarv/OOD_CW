package com.example.demo.CoreModules;

import java.sql.Time;

public class ArticleInteractionData {
    private Article article;  // Reference to an Article object
    private int interactionId;
    private int userId;
    private String rating;
    private Time timeTaken;

    // Constructor
    public ArticleInteractionData(Article article, int interactionId, int userId, String rating, Time timeTaken) {
        this.article = article;
        this.interactionId = interactionId;
        this.userId = userId;
        this.rating = rating;
        this.timeTaken = timeTaken;
    }

    // Getters
    public Article getArticle() { return article; }
    public int getInteractionId() { return interactionId; }
    public int getUserId() { return userId; }
    public String getRating() { return rating; }
    public Time getTimeTaken() { return timeTaken; }
}
