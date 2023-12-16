package com.example.myfpl_clone.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfpl_clone.R;
import com.example.myfpl_clone.activities.LoginActivity;
import com.example.myfpl_clone.activities.RegisterActivity;
import com.example.myfpl_clone.databinding.FragmentProfileBinding;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.GetNewsResponseModel;
import com.example.myfpl_clone.models.GetUserDetailResponseModel;
import com.example.myfpl_clone.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int userId;
    IRetrofitRouter iretrofitRouter;
    FragmentProfileBinding profileBinding;

    EditText edtEmail, edtName, edtPhone, edtAddress;
    TextView tvCreatedAt;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        // Inflate the layout for this fragment
        iretrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);
        profileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtAddress = view.findViewById(R.id.edt_address);
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPhone = view.findViewById(R.id.edt_phone);
        tvCreatedAt = view.findViewById(R.id.edt_create_at);
        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userID", Context.MODE_PRIVATE);

        // Lấy dữ liệu
        userId = sharedPreferences.getInt("userId", -1);

        Button button =view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        GetUserDeail();
    }

    private void GetUserDeail() {
        iretrofitRouter.getUserDetail(userId).enqueue(getUserDetailCallback);
    }


    Callback<GetUserDetailResponseModel> getUserDetailCallback = new Callback<GetUserDetailResponseModel>() {
        @Override
        public void onResponse(Call<GetUserDetailResponseModel> call, Response<GetUserDetailResponseModel> response) {
            if (response.isSuccessful()) {
                GetUserDetailResponseModel userResponseDTO = response.body();
                if (userResponseDTO.getUser() != null ) {
                    Toast.makeText(getContext(),
                                    "Get user detail success!!!", Toast.LENGTH_SHORT)
                            .show();
                    // nếu thành công
                    UserModel userModel = userResponseDTO.getUser();
                    Log.d("user", userModel.toString());

                    edtEmail.setText(userModel.getEmail());
                    edtAddress.setText(userModel.getAddress());
                    tvCreatedAt.setText("Created at: " +userModel.getCreated_at());
                    edtPhone.setText(userModel.getPhone());
                    edtName.setText(userModel.getName());

                } else {
                    Toast.makeText(getContext(),
                                    "Failed!!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<GetUserDetailResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };
}