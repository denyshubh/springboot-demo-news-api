package com.example.newsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopNewsResponse {

    @JsonProperty("top_news")
    private List<TopNewsCluster> topNews;
    private String language;
    private String country;

    public List<TopNewsCluster> getTopNews() {
        return topNews == null ? null : List.copyOf(topNews);
    }

    public void setTopNews(List<TopNewsCluster> topNews) {
        this.topNews = topNews == null ? null : List.copyOf(topNews);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

