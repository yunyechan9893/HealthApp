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
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.adapters.MainPagerAdapter;
import me.relex.circleindicator.CircleIndicator3;

public class Fragment_Home extends Fragment {

    // 내부 클래스 핸들러
    /*
    * 구현해야할 것
    * - SQL Lite에서 오늘의 칼로리, 운동, PT일정 가져오기
    * -
    * */

    View rootView;
    TextView tvNoticeBBTitle, tvFreeBBTitle, tvExerciseBBTitle;
    ViewPager2 viewPg;
    CircleIndicator3 indicator;


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
        ArrayList<Integer> bannerImage = new ArrayList<>();
        bannerImage.add(R.drawable.img_banner_1);
        bannerImage.add(R.drawable.img_banner_2);

        FragmentStateAdapter fragmentStateAdapter = new MainPagerAdapter(getActivity(), bannerImage);
        viewPg.setAdapter(fragmentStateAdapter);
        indicator.setViewPager(viewPg);

        int listCnt= bannerImage.size();
        indicator.createIndicators(listCnt,0);
        viewPg.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPg.setCurrentItem(listCnt, true);
        viewPg.setOffscreenPageLimit(listCnt);


        return rootView;
    }
}
