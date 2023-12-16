package com.example.myfpl_clone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myfpl_clone.R;
import com.example.myfpl_clone.databinding.ActivityLoginBinding;
import com.example.myfpl_clone.databinding.ActivityRegisterBinding;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.RegisterResponseModel;
import com.example.myfpl_clone.models.UserModel;
import com.example.myfpl_clone.models.UserRegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    IRetrofitRouter iRetrofitRouter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        activityRegisterBinding.edtEmail.setText("email1@gmail.com");
        activityRegisterBinding.edtPass.setText("123456");
        activityRegisterBinding.edtRePass.setText("12345");
        activityRegisterBinding.edtName.setText("Teo");
        activityRegisterBinding.edtPhone.setText("0132356789");
        activityRegisterBinding.edtAddress.setText("Tân TÂN tâN");

    }

    public void onRegisterClick(View v) {
        String email = activityRegisterBinding.edtEmail.getText().toString();
        String password = activityRegisterBinding.edtPass.getText().toString();
        String rePassword = activityRegisterBinding.edtRePass.getText().toString();
        String name = activityRegisterBinding.edtName.getText().toString();
        String phone = activityRegisterBinding.edtPhone.getText().toString();
        String address = activityRegisterBinding.edtAddress.getText().toString();


        UserRegisterModel userModel = new UserRegisterModel();
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setConfirm_password(rePassword);
        userModel.setName(name);
        userModel.setPhone(phone);
        userModel.setAddress(address);
        userModel.setRole("user");


        iRetrofitRouter.register(userModel).enqueue(registerCallback);

    }
    public void onLoginClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        // lưu vad share preference
        finish();
    }

    Callback<RegisterResponseModel> registerCallback = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
            if (response.isSuccessful()){
                RegisterResponseModel registerResponseDTO = response.body();
                Log.d("deeeee", registerResponseDTO.getStatus() + "");
                if (registerResponseDTO.getStatus()) {
                    Toast.makeText(RegisterActivity.this,
                                    "Register success!!!", Toast.LENGTH_LONG)
                            .show();
                    // nếu thành công chuyển sang màn hình danh sách
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // lưu vad share preference
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };
}