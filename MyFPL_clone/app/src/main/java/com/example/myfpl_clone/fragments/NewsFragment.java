package com.example.myfpl_clone.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myfpl_clone.Adapter.MyNewsRecyclerViewAdapter;
import com.example.myfpl_clone.Model.News;
import com.example.myfpl_clone.R;
import com.example.myfpl_clone.activities.LoginActivity;
import com.example.myfpl_clone.activities.MainActivity;
import com.example.myfpl_clone.helpers.IRetrofitRouter;
import com.example.myfpl_clone.helpers.RetrofitHelper;
import com.example.myfpl_clone.models.GetNewsResponseModel;
import com.example.myfpl_clone.models.LoginResponseModel;
import com.example.myfpl_clone.models.NewsModel;
import com.example.myfpl_clone.models.TopicModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class NewsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<NewsModel> list = new ArrayList<>();
    private TopicModel topic;
    private MyNewsRecyclerViewAdapter adapter;

    private IRetrofitRouter retrofitRouter;
    RecyclerView recyclerView ;
    EditText editTextKeyword ;
    ImageButton imageButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsFragment() {
    }
    public NewsFragment(TopicModel topic) {
        this.topic = topic;
    }

    // TODO: Customize parameter initialization

    public static NewsFragment newInstance(int columnCount) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        recyclerView = view.findViewById(R.id.f_n_l_list);
        editTextKeyword = view.findViewById(R.id.f_n_l_edt_search);
        imageButton = view.findViewById(R.id.f_n_l_img_btn_search);

//        list.add(new News(1,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                    aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(2,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(3,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(4,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(5,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(6,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(7,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));
//        list.add(new News(8,"THÔNG BÁO (V/v Triển khai Kế hoạch thực tập và Hỗ trợ thực tập Học kỳ Spring 2024)",
//                aaaa,
//                new Date(2023, 5,5), 1,1));



        retrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topic != null) {
                    GetNewsByKeywordAndTopic();
                } else {
                    GetNewsByKeyword();
                }
            }
        });

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = recyclerView.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyNewsRecyclerViewAdapter(list, getParentFragmentManager());
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (topic != null) {
            GetNewsByTopic();
        } else {
            GetNews();
        }
    }

    private void GetNews() {
        retrofitRouter.getNews().enqueue(getNewsCallback);
    }

    private void GetNewsByTopic() {
        retrofitRouter.getNewsByTopic(topic.getId()).enqueue(getNewsCallback);
    }

    private void GetNewsByKeywordAndTopic() {
        String keyword = editTextKeyword.getText().toString();
        retrofitRouter.getNewsByKeywordAndTopic(keyword, topic.getId()).enqueue(getNewsCallback);
    }

    private void GetNewsByKeyword() {
        String keyword = editTextKeyword.getText().toString();
        retrofitRouter.getNewsByKeyword(keyword).enqueue(getNewsCallback);
    }


    Callback<GetNewsResponseModel> getNewsCallback = new Callback<GetNewsResponseModel>() {
        @Override
        public void onResponse(Call<GetNewsResponseModel> call, Response<GetNewsResponseModel> response) {
            if (response.isSuccessful()){
                GetNewsResponseModel newsResponseDTO = response.body();
                if (!newsResponseDTO.getNews().isEmpty()) {
                    Toast.makeText(getContext(),
                                    "Get news success!!!", Toast.LENGTH_SHORT)
                            .show();
                    // nếu thành công
                    list.clear();
                    list.addAll(newsResponseDTO.getNews());
                    adapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getContext(),
                                    "Failed!!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<GetNewsResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };


    String aaaa = "Để triển khai hiệu quả hoạt động thực tập tốt nghiệp học kỳ Spring 2024 Phòng quan hệ doanh nghiệp thông báo đến các bạn sinh viên Kế hoạch thực tập và Hỗ trợ thực tập như sau: \n" +
            "\n" +
            "\n" +
            "\n" +
            "1. Đối tượng và điều kiện thực tập Doanh nghiệp: \n" +
            "\n" +
            "-  Sinh viên xem danh sách dự kiến thực tập: TẠI ĐÂY\n" +
            "\n" +
            "-  Sinh viên thuộc một trong ba đối tượng thực tập, cần lưu ý những điểm sau:\n" +
            "\n" +
            "\n" +
            "\n" +
            "1.1. Thực tập đúng tiến độ (bắt buộc)\n" +
            "\n" +
            "- Tất cả sinh viên học kỳ 7 (tính đến thời điểm học kỳ Spring 2024) có trạng thái học đi (HDI), Đủ điều kiện thực tập (Đã pass môn tiên quyết và đã hoàn thành tất cả các môn học 5 kỳ đầu);\n" +
            "\n" +
            "- Đối với sinh viên kỳ 7 thực tập đúng tiến độ không đăng ký thực tập, Phòng quan hệ doanh nghiệp sẽ ghi nhận sinh viên rớt môn thực tập tại học kỳ Spring 2024. Sinh viên cần đăng ký học lại thành công trên hệ thống AP và đăng ký Doanh nghiệp thực tập vào kỳ sau.\n" +
            "\n" +
            "\n" +
            "\n" +
            "1.2. Thực tập trước tiến độ (khuyến khích) \n" +
            "\n" +
            "- Sinh viên học kỳ 6 (tính đến thời điểm học kỳ Spring 2024) đủ điều kiện thực tập (Đã pass môn tiên quyết và đã hoàn thành tất cả các môn học 5 kỳ đầu) có nhu cầu đi thực tập trước tiến độ đào tạo. \n" +
            "\n" +
            "Lưu ý:\n" +
            "\n" +
            "- Sinh viên cần đảm bảo việc học tập ở trường và việc thực tập tại Doanh nghiệp (Nhà trường không xét việc chuyển lớp, chuyển ca học vì lý do đi thực tập);\n" +
            "\n" +
            "- Sinh viên đã đăng ký đi thực tập trước tiến độ và được công nhận kết quả thực tập sẽ không phải thực tập trong học kỳ 7;\n" +
            "\n" +
            "- Sinh viên kỳ 6 thực tập trước tiến độ nếu kết quả thực tập không đạt, học kỳ 7 cần đăng ký thực tập như sinh viên thực tập đúng tiến độ.\n" +
            "\n" +
            "\n" +
            "\n" +
            " 1.3. Sinh viên thực tập lại \n" +
            "\n" +
            "- Sinh viên đã thực tập trong kỳ 7 nhưng chưa đạt sẽ thuộc đối tượng thực tập lại. Những bạn thuộc đối tượng thực tập lại cần hoàn thành đủ 2 bước dưới đây:\n" +
            "\n" +
            "Bước 1: Đăng ký học lại và thanh toán phí học lại môn Thực tập tốt nghiệp trên hệ thống AP;\n" +
            "\n" +
            "Bước 2: Đăng ký Doanh nghiệp thực tập theo link P.QHDN.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lưu ý:\n" +
            "\n" +
            " Sinh viên đăng ký học lại và thanh toán phí trên hệ thống AP nhưng không đăng ký link thực tập sẽ không được công nhận kết quả thực tập;\n" +
            " Sinh viên thực tập lại đăng ký link Doanh nghiệp thực tập nhưng không đăng ký học lại và thanh toán phí trên hệ thống AP sẽ không được công nhận kết quả thực tập.\n" +
            "\n" +
            "\n" +
            "2. Định hướng thực tập tốt nghiệp:\n" +
            "\n" +
            "Trước 8 tuần khi học kỳ mới bắt đầu, P. Quan hệ doanh nghiệp sẽ tổ chức buổi định hướng thực tập tốt nghiệp nhằm: triển khai các nội dung về quy chế thực tập, nội dung thực tập và phổ biến các biểu mẫu liên quan và giải đáp các thắc mắc liên quan tới việc thực tập thực tế tại Doanh nghiệp. Sinh viên cần lưu ý theo dõi thông báo và tham dự đúng giờ.\n" +
            "\n" +
            "\n" +
            "\n" +
            "3. Hình thức đăng ký, thời gian đăng ký:\n" +
            "\n" +
            "\n" +
            "\n" +
            "3.1. Hình thức đăng ký   (16/10/2023 đến 31/12/2023)\n" +
            "\n" +
            "- Sinh viên sau khi đã được Doanh nghiệp tiếp nhận thực tập, sinh viên phải đăng ký tên Doanh nghiệp cho P. Quan hệ doanh nghiệp theo đường link: SPRING24- ĐĂNG KÝ THỰC TẬP\n" +
            "\n" +
            "- Trong quá trình thực tập, sinh viên được phép thay đổi Doanh nghiệp thực tập (nếu thấy không phù hợp). Việc thay đổi Doanh nghiệp chỉ được thực hiện trong vòng 01 tuần (tính từ thời điểm bắt đầu thực tập). Sau 01 tuần, mọi thay đổi Doanh nghiệp đều không được chấp nhận;Sinh viên có nguyện vọng thay đổi thông tin Doanh nghiệp thực tập đã đăng ký trước đó, sinh viên cập nhật thông tin Doanh nghiệp mới theo đường link: SPRING24 - ĐIỀU CHỈNH THÔNG TIN DOANH NGHIỆP\n" +
            "\n" +
            "\n" +
            "\n" +
            "3.2. Các mốc thời gian sinh viên cần lưu ý\n" +
            "\n" +
            "Theo Quy chế thực tập và Kế hoạch đào tạo học kỳ SPRING 2024, thời gian thực tập của sinh viên là 2,5 tháng (hai tháng rưỡi) hoặc 10 tuần.\n" +
            "\n" +
            "Sinh viên cần lưu ý các mốc thời gian quan trong sau:";
}





