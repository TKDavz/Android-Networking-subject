package com.example.myfpl_clone.Adapter;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfpl_clone.Model.News;
import com.example.myfpl_clone.R;
import com.example.myfpl_clone.databinding.NewsCartItemBinding;
import com.example.myfpl_clone.fragments.NewsDetailFragment;
import com.example.myfpl_clone.models.NewsModel;
import com.example.myfpl_clone.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    private final List<NewsModel> mValues;
    private FragmentManager fragmentManager ;
    public MyNewsRecyclerViewAdapter(List<NewsModel> items, FragmentManager fragmentManager) {
        mValues = items;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(NewsCartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues.size() > 0) {
            holder.mItem = mValues.get(position);
            holder.newsTitle.setText(mValues.get(position).getTitle());
            holder.newsContent.setText(mValues.get(position).getContent());
//        holder.newsTime.setText("Ngày đăng: " + mValues.get(position).getCreated_at());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.b_t_n_fragmentContainerView, new NewsDetailFragment(mValues.get(holder.getAdapterPosition())));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        } else {
            holder.newsTitle.setText("Không có tin");
            holder.newsContent.setText("Không có tin do admin chưa thêm tin tức của topic này");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() > 0 ? mValues.size() : 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView newsTitle;
        public final TextView newsContent;
//        public final TextView newsTime;
        public final CardView cardView;
        public NewsModel mItem;

        public ViewHolder(NewsCartItemBinding binding) {
            super(binding.getRoot());
            newsTitle = binding.nCITitle;
            newsContent = binding.nCIContent;
            cardView = binding.nCICart;
//            newsTime = binding.nCITime;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}