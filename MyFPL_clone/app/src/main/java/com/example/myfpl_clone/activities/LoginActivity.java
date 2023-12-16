package com.example.myfpl_clone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myfpl_clone.databinding.ActivityLoginBinding;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding= ActivityLoginBinding .inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        activityLoginBinding.edtEmail.setText("email1@gmail.com");
        activityLoginBinding.edtPass.setText("123456");

        activityLoginBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLoginClick(view);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                // lưu vad share preference
//                finish();
            }
        });

        activityLoginBinding.tvToForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForgotPassClick(view);
            }
        });

        activityLoginBinding.tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterClick(view);
            }
        });

    }

    public void onLoginClick(View v) {
        String email = activityLoginBinding.edtEmail.getText().toString();
        String password = activityLoginBinding.edtPass.getText().toString();

        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);

        iRetrofitRouter.login(userModel).enqueue(loginCallback);

    }
    public void onRegisterClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        // lưu vad share preference
        finish();
    }

    public void onForgotPassClick(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
        // lưu vad share preference
        finish();
    }

    Callback<LoginResponseModel> loginCallback = new Callback<LoginResponseModel>() {
        @Override
        public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
            if (response.isSuccessful()){
                LoginResponseModel loginResponseDTO = response.body();
                if (loginResponseDTO.getStatus()) {
                    Toast.makeText(LoginActivity.this,
                                    "Login success!!!", Toast.LENGTH_LONG)
                            .show();
                    // nếu thành công chuyển sang màn hình danh sách
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    // lưu vao share preference
                    SharedPreferences sharedPreferences = getSharedPreferences("userID", Context.MODE_PRIVATE);
                    // Mở Editor để sửa đổi dữ liệu
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Lưu dữ liệu
                    editor.putInt("userId", loginResponseDTO.getUser().getId());
                    editor.putString("userName", loginResponseDTO.getUser().getName());
//                    Log.d("user", loginResponseDTO.getUser().toString());

                    // Lưu các thay đổi
                    editor.apply();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<LoginResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };



}