package com.example.markcarlton.news;

import java.util.List;

/**
 * Created by Edward on 23/04/2017.
 */

public class ResponseList {

    private String source;
    private List<Article> articles;
    private String status;
    private List<Source> sources = null;

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public ResponseList(String source, List<Article> articles) {
        this.source = source;
        this.articles = articles;
    }

    public String getSource() {
        return source;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
