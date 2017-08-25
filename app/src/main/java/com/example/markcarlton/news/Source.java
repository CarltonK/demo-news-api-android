package com.example.markcarlton.news;

import java.util.List;

/**
 * Created by Edward on 24/04/2017.
 */

public class Source {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    private UrlsToLogos urlsToLogos;
    private List<String> sortBysAvailable = null;

    public Source(String id, String name, String description, String url, String category, String language, String country, UrlsToLogos urlsToLogos, List<String> sortBysAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.urlsToLogos = urlsToLogos;
        this.sortBysAvailable = sortBysAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public UrlsToLogos getUrlsToLogos() {
        return urlsToLogos;
    }

    public List<String> getSortBysAvailable() {
        return sortBysAvailable;
    }
}
