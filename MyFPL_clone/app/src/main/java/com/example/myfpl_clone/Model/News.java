package com.example.myfpl_clone.Model;

import java.sql.Date;

public class News {
   private int id ;
   private String title;
    private String content;
    private Date created_at;
    private int topic_id;
    private int user_id;

    public News() {
    }

    public News(int id, String title, String content, Date created_at, int topic_id, int user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.topic_id = topic_id;
        this.user_id = user_id;
    }

    public News(String title, Date created_at, int user_id) {
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
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
}
