package com.example.myfpl_clone.models;

import java.sql.Date;
import java.util.List;

public class GetNewsDetailResponseModel {
    private NewsDetailModel news;
    private String message;

    public GetNewsDetailResponseModel() {
    }

    public GetNewsDetailResponseModel(NewsDetailModel news, String message) {
        this.news = news;
        this.message = message;
    }

    public NewsDetailModel getNews() {
        return news;
    }

    public void setNews(NewsDetailModel news) {
        this.news = news;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
