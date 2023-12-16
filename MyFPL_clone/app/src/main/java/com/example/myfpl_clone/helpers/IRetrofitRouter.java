package com.example.myfpl_clone.helpers;

import com.example.myfpl_clone.models.ForgotPassModel;
import com.example.myfpl_clone.models.ForgotPassResponseModel;
import com.example.myfpl_clone.models.GetNewsDetailResponseModel;
import com.example.myfpl_clone.models.GetNewsResponseModel;
import com.example.myfpl_clone.models.GetTopicsResponseModel;
import com.example.myfpl_clone.models.GetUserDetailResponseModel;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.NewsDetailModel;
import com.example.myfpl_clone.models.RegisterResponseModel;
import com.example.myfpl_clone.models.UserModel;
import com.example.myfpl_clone.models.UserRegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitRouter {

    @POST("/login.php")
    Call<LoginResponseModel> login (@Body UserModel body);

    @POST("/register.php")
    Call<RegisterResponseModel> register (@Body UserRegisterModel body);

    @GET("/get-news.php")
    Call<GetNewsResponseModel> getNews ();

    @GET("get-news-detail.php")
    Call<GetNewsDetailResponseModel> getNewsDetail (@Query("news_id") Integer news_id);

    @GET("/search-news-by-keyword.php")
    Call<GetNewsResponseModel> getNewsByKeyword (@Query("keyword") String keyword);

    @GET("/get-news-by-topic.php")
    Call<GetNewsResponseModel> getNewsByTopic (@Query("topic_id") Integer topicId);

    @GET("/search-news-by-keyword-and-topic.php")
    Call<GetNewsResponseModel> getNewsByKeywordAndTopic (@Query("keyword") String keyword, @Query("topic_id") Integer topic_id);

    @GET("/get-topics.php")
    Call<GetTopicsResponseModel> getTopics ();

    @GET("/get-user-detail.php")
    Call<GetUserDetailResponseModel> getUserDetail (@Query("user_id") Integer user_id);

    @POST("/forgot-password.php")
    Call<ForgotPassResponseModel> forgotPass (@Body ForgotPassModel body);

}
