package com.example.myfpl_clone.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfpl_clone.fragments.NewsFragment;
import com.example.myfpl_clone.models.TopicModel;

import java.util.List;

public class NewsPagerAdapter extends FragmentStateAdapter  {

    private List<TopicModel> topicModelList;
    public NewsPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public NewsPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<TopicModel> topicModelList) {
        super(fragmentActivity);
        this.topicModelList = topicModelList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return your fragments based on the position
        if (topicModelList != null && position >= 0 && position < topicModelList.size()) {
            TopicModel topic = topicModelList.get(position);
            // Sử dụng dữ liệu từ TopicModel để tạo Fragment tương ứng
            return new NewsFragment(topic); // Thay NewsFragment.newInstance bằng cách tạo Fragment của bạn từ TopicModel
        } else {
            // Trả về một Fragment mặc định hoặc xử lý tùy thuộc vào trường hợp cụ thể của bạn
            return new NewsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return topicModelList == null ? 0 : topicModelList.size();
    }


}
