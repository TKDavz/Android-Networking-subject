package com.example.myfpl_clone.models;

import java.sql.Date;

public class NewsDetailModel {
    private int id ;
    private String title;
    private String content;
    private String created_at;
    private String image;
    private int topic_id;
    private int user_id;

    private String topic_name;
    private String author_name;

    public NewsDetailModel() {
    }

    public NewsDetailModel(int id, String title, String content, String created_at, int topic_id, int user_id, String author_name, String topic_name) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.topic_id = topic_id;
        this.user_id = user_id;
        this.author_name = author_name;
        this.topic_name = topic_name;
    }

    public NewsDetailModel(String title, String created_at, int user_id) {
        this.title = title;
        this.created_at = created_at;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    @Override
    public String toString() {
        return "NewsDetailModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                ", image='" + image + '\'' +
                ", topic_id=" + topic_id +
                ", user_id=" + user_id +
                ", topic_name='" + topic_name + '\'' +
                ", author_name='" + author_name + '\'' +
                '}';
    }
}
