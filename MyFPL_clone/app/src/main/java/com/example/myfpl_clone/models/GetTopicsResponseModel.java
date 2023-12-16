package com.example.myfpl_clone.models;

import com.example.myfpl_clone.Model.News;

import java.util.List;

public class GetTopicsResponseModel {
    private List<TopicModel> topics;

    public GetTopicsResponseModel() {
    }

    public GetTopicsResponseModel(List<TopicModel> news) {
        this.topics = topics;
    }

    public List<TopicModel> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicModel> news) {
        this.topics = topics;
    }
}
