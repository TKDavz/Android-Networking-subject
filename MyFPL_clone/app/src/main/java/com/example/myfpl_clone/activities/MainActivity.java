package com.example.myfpl_clone.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myfpl_clone.databinding.BottomTabNavigationBinding;
import com.example.myfpl_clone.fragments.BlankFragment;
import com.example.myfpl_clone.fragments.HomeFragment;
import com.example.myfpl_clone.fragments.NewsFragment;
import com.example.myfpl_clone.R;
import com.example.myfpl_clone.fragments.ProfileFragment;
import com.example.myfpl_clone.interfaces.OnBackPressedListener;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {

    private BottomTabNavigationBinding bottomTabNavigationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bottom_tab_navigation);
        bottomTabNavigationBinding = BottomTabNavigationBinding.inflate(getLayoutInflater());

        setContentView(bottomTabNavigationBinding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("userID", Context.MODE_PRIVATE);

        // Lấy dữ liệu
        String userName = sharedPreferences.getString("userName", "Name");

//        bottomTabNavigationBinding.bTNToolbar.setLogo(R.drawable.ic_launcher_foreground);
        setSupportActionBar(bottomTabNavigationBinding.bTNToolbar);
        // Thiết lập tiêu đề cho ActionBar
        getSupportActionBar().setTitle(userName);



        replaceFragment(new HomeFragment());
        bottomTabNavigationBinding.bTNBottomNavigationView.setOnItemSelectedListener(item ->{
            int id = item.getItemId();
            if(id == R.id.navigation_home){
                replaceFragment(new HomeFragment());
                return true;
            } else if(id == R.id.navigation_cart) {
                replaceFragment(new BlankFragment());

                return true;

            } else if(id == R.id.navigation_gifts ) {
                replaceFragment(new NewsFragment());

                return true;

            } else if(id == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment());

                return true;

            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.b_t_n_fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
//
//    // Lấy SharedPreferences
//    SharedPreferences sharedPreferences = getSharedPreferences("tên_file_preferences", Context.MODE_PRIVATE);
//
//    // Mở Editor để sửa đổi dữ liệu
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//// Lưu dữ liệu
//editor.putString("key_string", "giá_trị_string");
//        editor.putInt("key_int", 10);
//        editor.putBoolean("key_boolean", true);
//
//// Lưu các thay đổi
//        editor.apply();
//
//
//
//
//// Lấy SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("tên_file_preferences", Context.MODE_PRIVATE);
//
//// Lấy dữ liệu
//        String stringValue = sharedPreferences.getString("key_string", "giá_trị_mặc_định_nếu_không_tìm_thấy");
//        int intValue = sharedPreferences.getInt("key_int", 0);
//        boolean booleanValue = sharedPreferences.getBoolean("key_boolean", false);
