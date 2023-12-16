package com.example.myfpl_clone.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfpl_clone.databinding.ActivityForgotPassBinding;
import com.example.myfpl_clone.databinding.ActivityLoginBinding;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.ForgotPassModel;
import com.example.myfpl_clone.models.ForgotPassResponseModel;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {

    ActivityForgotPassBinding activityForgotPassBinding;

    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityForgotPassBinding= ActivityForgotPassBinding .inflate(getLayoutInflater());
        setContentView(activityForgotPassBinding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        activityForgotPassBinding.edtEmail.setText("email1@gmail.com");

        activityForgotPassBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLoginClick(view);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                // lưu vad share preference
//                finish();
            }
        });

    }

    public void onLoginClick(View v) {
        String email = activityForgotPassBinding.edtEmail.getText().toString();

        ForgotPassModel forgotPassModel = new ForgotPassModel();
        forgotPassModel.setEmail(email);

        iRetrofitRouter.forgotPass(forgotPassModel).enqueue(loginCallback);

    }
    public void onRegisterClick(View v) {
        Intent intent = new Intent(ForgotPassActivity.this, RegisterActivity.class);
        startActivity(intent);
        // lưu vad share preference
        finish();
    }

    Callback<ForgotPassResponseModel> loginCallback = new Callback<ForgotPassResponseModel>() {
        @Override
        public void onResponse(Call<ForgotPassResponseModel> call, Response<ForgotPassResponseModel> response) {
            if (response.isSuccessful()){
                ForgotPassResponseModel forgotPassResponseDTO = response.body();
//                if (forgotPassResponseDTO.getStatus()) {
                    Toast.makeText(ForgotPassActivity.this,
                                   forgotPassResponseDTO.getMessage() +"!!!", Toast.LENGTH_LONG)
                            .show();
                    // nếu thành công chuyển sang màn hình danh sách

//                    finish();
//                }
//                else {
//                    Toast.makeText(ForgotPassActivity.this,
//                                    "Failed!!!", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }
        }

        @Override
        public void onFailure(Call<ForgotPassResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };



}