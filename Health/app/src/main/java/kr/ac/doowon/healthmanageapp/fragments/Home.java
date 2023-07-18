package kr.ac.doowon.healthmanageapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import me.relex.circleindicator.CircleIndicator3;

public class Home extends Fragment {

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
    ProgressBar pbTodayKcal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewPg = rootView.findViewById(R.id.viewPager2);
        indicator = rootView.findViewById(R.id.circleIndicator);
        tvNoticeBBTitle = rootView.findViewById(R.id.tvNoticeBBTitle);
        tvFreeBBTitle = rootView.findViewById(R.id.tvFreeBBTitle);
        tvExerciseBBTitle = rootView.findViewById(R.id.tvExerciseBBTitle);
        pbTodayKcal = rootView.findViewById(R.id.pb_today_kcal);

        //-- 추후 베너이미지 추가를 할 시 여기를 변경해주세요. --//
        // test용입니다.

        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(getActivity());
        fragmentAdapter.addFragment(new HomeMainImage(R.drawable.img_banner_1));
        fragmentAdapter.addFragment(new HomeMainImage(R.drawable.img_banner_2));
        viewPg.setAdapter(fragmentAdapter);
        indicator.setViewPager(viewPg);

        int listCnt= fragmentAdapter.getItemCount();
        indicator.createIndicators(listCnt,0);
        viewPg.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPg.setCurrentItem(listCnt, true);
        viewPg.setOffscreenPageLimit(listCnt);

        return rootView;
    }
}
