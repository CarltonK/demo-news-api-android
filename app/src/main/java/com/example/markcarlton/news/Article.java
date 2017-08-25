package com.example.markcarlton.news;

/**
 * Created by Edward on 23/04/2017.
 */

public class Article {

    private String author;
    private String title;
    private String description;
    private String urlToImage;

    public Article(String author, String title, String description, String urlToImage) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
}
