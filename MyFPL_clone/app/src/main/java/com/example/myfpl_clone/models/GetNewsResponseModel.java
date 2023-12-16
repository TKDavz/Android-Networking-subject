package com.example.myfpl_clone.models;

import com.example.myfpl_clone.Model.News;

import java.util.List;

public class GetNewsResponseModel {
    private List<NewsModel> news;

    public GetNewsResponseModel() {
    }

    public GetNewsResponseModel(List<NewsModel> news) {
        this.news = news;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public void setNews(List<NewsModel> news) {
        this.news = news;
    }
}
