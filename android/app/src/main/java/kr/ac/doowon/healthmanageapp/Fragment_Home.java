package kr.ac.doowon.healthmanageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Fragment_Home extends Fragment implements View.OnClickListener {
    public Fragment_Home(AppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

    }

    // 내부 클래스 핸들러
    class HandlerManager extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodName = bundle.getString("Method","E");

            switch (methodName)
            {
                case  "BBSSetting":
                    String title = bundle.getString("Title","E");
                    int category = bundle.getInt("Category");

                    if(category == 1000) {
                        tvNoticeBBTitle.setText(title);
                        threadPool.HomeBBSelectThread1(1001);
                    }

                    else if(category == 1001)
                    {
                        tvFreeBBTitle.setText(title);
                        threadPool.HomeBBSelectThread1(1002);
                    }
                    else if(category == 1002) tvExerciseBBTitle.setText(title);
            }
        }
    }

    View rootView;
    TextView tvNoticeBBTitle, tvFreeBBTitle, tvExerciseBBTitle;
    SharedPreferences USER_ID;
    Class_TheadPool threadPool;
    Bundle bundle;
    ViewPager2 viewPg;
    CircleIndicator3 indicator;
    int num_page = 4;
    AppCompatActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new HandlerManager();
        threadPool = new Class_TheadPool(handler);
        threadPool.HomeBBSelectThread1(1000);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewPg = rootView.findViewById(R.id.viewPager2);
        indicator = rootView.findViewById(R.id.circleIndicator);
        tvNoticeBBTitle = rootView.findViewById(R.id.tvNoticeBBTitle);
        tvFreeBBTitle = rootView.findViewById(R.id.tvFreeBBTitle);
        tvExerciseBBTitle = rootView.findViewById(R.id.tvExerciseBBTitle);

        //-- 추후 베너이미지 추가를 할 시 여기를 변경해주세요. --//
        // test용입니다.
        List<Integer> bannerImage = new ArrayList<Integer>();
        bannerImage.add(R.drawable.img_banner_1);
        bannerImage.add(R.drawable.img_banner_2);

        //-- 뷰페이저, 서클인디케이터, 프레그먼트 액티비티, 배너 이미지 바인딩을 담당합니다 --//
        Class_ViewPagerManager viewPagerManager = new Class_ViewPagerManager(viewPg, indicator, activity, bannerImage);
        //-- 우리 앱에 알맞게 세팅합니다 --//
        viewPagerManager.Setting(viewPg, indicator);

        return rootView;
    }
}
