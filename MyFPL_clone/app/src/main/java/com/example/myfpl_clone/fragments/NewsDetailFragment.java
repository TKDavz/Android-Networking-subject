package com.example.myfpl_clone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfpl_clone.Model.News;
import com.example.myfpl_clone.R;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.GetNewsDetailResponseModel;
import com.example.myfpl_clone.models.GetNewsResponseModel;
import com.example.myfpl_clone.models.NewsDetailModel;
import com.example.myfpl_clone.models.NewsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    IRetrofitRouter iRetrofitRouter;
    NewsDetailModel newsDetailModel;
    NewsModel newsModel;
    TextView tv1, tv2, tv3, tv4;
    ImageView imageView;
    public NewsDetailFragment() {
        // Required empty public constructor
    }


    public NewsDetailFragment(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    //    public static NewsDetailFragment newInstance(News param1) {
//        NewsDetailFragment fragment = new NewsDetailFragment();
//        Bundle args = new Bundle();
//        args.put(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         tv1 = view.findViewById(R.id.textView);
         tv2 = view.findViewById(R.id.textView2);
         tv3 = view.findViewById(R.id.textView3);
         tv4 = view.findViewById(R.id.textView4);
         imageView = view.findViewById(R.id.f_n_d_imageView);

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);


    }

    @Override
    public void onResume() {
        super.onResume();

        GetNewsDetail();

    }

    private void GetNewsDetail() {
        iRetrofitRouter.getNewsDetail(newsModel.getId()).enqueue(getNewsCallback);
    }


    Callback<GetNewsDetailResponseModel> getNewsCallback = new Callback<GetNewsDetailResponseModel>() {
        @Override
        public void onResponse(Call<GetNewsDetailResponseModel> call, Response<GetNewsDetailResponseModel> response) {
            if (response.isSuccessful()){
                GetNewsDetailResponseModel newsDetailResponseDTO = response.body();
                if (newsDetailResponseDTO != null) {

                    Toast.makeText(getContext(),
                                    "Get news detail success!!!", Toast.LENGTH_SHORT)
                            .show();
                    // nếu thành công

                    newsDetailModel = newsDetailResponseDTO.getNews();
                    tv1.setText(newsDetailModel.getTitle());
                    tv2.setText(newsDetailModel.getContent());
                    tv3.setText("Người đăng: " + newsDetailModel.getAuthor_name());
//                    Log.d("luc", newsDetailModel.toString());
                    tv4.setText("Lúc: "+ newsDetailModel.getCreated_at().toString());

                    Glide.with(getContext()).load(newsDetailModel.getImage()).into(imageView);
                }
                else {
                    Toast.makeText(getContext(),
                                    "Failed!!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<GetNewsDetailResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }

    };

}