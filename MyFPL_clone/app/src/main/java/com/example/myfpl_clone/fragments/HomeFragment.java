package com.example.myfpl_clone.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfpl_clone.Adapter.NewsPagerAdapter;
import com.example.myfpl_clone.R;
import com.example.myfpl_clone.activities.LoginActivity;
import com.example.myfpl_clone.activities.MainActivity;
import com.example.myfpl_clone.databinding.TopTabNavigationBinding;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.interfaces.OnBackPressedListener;
import com.example.myfpl_clone.models.GetTopicsResponseModel;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.TopicModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  TopTabNavigationBinding topTabNavigationBinding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    IRetrofitRouter iRetrofitRouter;
    List<TopicModel> topicModelList;
    NewsPagerAdapter adapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        // Kiểm tra xem Activity có implement giao diện OnBackPressedListener hay không
//        if (context instanceof OnBackPressedListener) {
//            // Gán activity nhận được qua giao diện cho một biến
//            OnBackPressedListener listener = (OnBackPressedListener) context;
//
//            // Override phương thức onBackPressed của activity khi fragment được attach
//            requireActivity().onBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//                @Override
//                public void handleOnBackPressed() {
//                    // Gọi yêu cầu quay lại thông qua giao diện
//                    listener.onBackPressed();
//                }
//            });
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        topTabNavigationBinding = TopTabNavigationBinding.inflate(getLayoutInflater());
        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.top_tab_navigation, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.t_t_n_view_pager2);

        topicModelList = new ArrayList<>();
         adapter = new NewsPagerAdapter(getActivity(), topicModelList);
//        topTabNavigationBinding.tTNViewPager2.setAdapter(adapter) ;
        viewPager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.t_t_n_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) ->{
                    switch (position) {
                        case 0:
                            tab.setText("Học tập".toUpperCase());
                            break;
                        case 1:
                            tab.setText("Hoạt động".toUpperCase());
                            break;
                        case 2:
                            tab.setText("Học phí".toUpperCase());
                            break;
                        case 3:
                            tab.setText("Học phívda".toUpperCase());
                            break;
                    }
                }
        ).attach();
    }
    @Override
    public void onResume() {
        super.onResume();
        GetTopics();
    }

    private void GetTopics() {
        iRetrofitRouter.getTopics().enqueue(getTopicsCallback);
    }

    Callback<GetTopicsResponseModel> getTopicsCallback = new Callback<GetTopicsResponseModel>() {
        @Override
        public void onResponse(Call<GetTopicsResponseModel> call, Response<GetTopicsResponseModel> response) {
            if (response.isSuccessful()){
                GetTopicsResponseModel getTopicsResponseDTO = response.body();

                if (!getTopicsResponseDTO.getTopics().isEmpty()) {
                    Toast.makeText(getContext(),
                                    "Get topics success!!!", Toast.LENGTH_SHORT)
                            .show();

                    List<TopicModel> listTemp =getTopicsResponseDTO.getTopics();
                    topicModelList.clear();
                    topicModelList.addAll( listTemp);
                    adapter.notifyDataSetChanged();


                    Log.d("topics list", getTopicsResponseDTO.getTopics().size() + "" );
                    new TabLayoutMediator(tabLayout, viewPager,
                            (tab, position) ->{

                                if (position >= 0 && position < topicModelList.size()) {
                                    Log.d("topics list", topicModelList.get(position).getName() );
                                    tab.setText(topicModelList.get(position).getName().toUpperCase());
                                }
                            }

                    ).attach();
                    // lưu vad share preference
                    Log.d("topics list", "end");

                }
                else {
                    Toast.makeText(getContext(),
                                    "Failed!!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<GetTopicsResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };

}