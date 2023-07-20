package kr.ac.doowon.healthmanageapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscribers.LambdaSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import me.relex.circleindicator.CircleIndicator3;

public class Home extends Fragment implements View.OnClickListener {

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

        AppDatabase db = AppDatabase.getDatabase(getContext());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        String formatNowDate = dateFormat.format(nowDate);



        Disposable dispose = db.targetKcalDAO().getTargetKcal(formatNowDate)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(targetKcals -> {

                        },
                        throwable -> {

                        });

        Log.i("getTargetKcal",dispose.toString());

        dispose.dispose();

        Log.i("getTargetKcal",dispose.toString());

        ProgressBar pbTodayKcal = rootView.findViewById(R.id.pb_today_kcal);



        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //if (disposable != null && !disposable.isDisposed()) {
         //   disposable.dispose();
       // }
    }
}
