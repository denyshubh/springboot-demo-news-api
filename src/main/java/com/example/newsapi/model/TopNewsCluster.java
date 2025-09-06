package com.example.newsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopNewsCluster {
    private List<WorldNewsItem> news;

    public List<WorldNewsItem> getNews() {
        return news == null ? null : List.copyOf(news);
    }

    public void setNews(List<WorldNewsItem> news) {
        this.news = news == null ? null : List.copyOf(news);
    }

}

