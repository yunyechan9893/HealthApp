package kr.ac.doowon.healthmanageapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeBinding;
import kr.ac.doowon.healthmanageapp.view_model.BannerViewModel;
import kr.ac.doowon.healthmanageapp.view_model.BannerViewModelFactory;

public class Home extends Fragment implements View.OnClickListener {
    /* * * * *
     * 1. 로딩창에서 비트맵 받기
     * 2. SQLLite에 비트맵 저장
     * 3. SQLLite에서 비트맵 확인 후 메인화면에 출력
     * ※ 비동기로 Url 이미지를 받아오니 시간이 오래걸린다
     * * * * * */
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        List<File> imgFiles = Arrays.asList(
                new File(getActivity().getFilesDir(), "banner_0"),
                new File(getActivity().getFilesDir(), "banner_1"),
                new File(getActivity().getFilesDir(), "banner_2"),
                new File(getActivity().getFilesDir(), "banner_3")
        );

        Log.i("PATH",getActivity().getFilesDir().toString());
        Log.i("File",new File(getActivity().getFilesDir(), "banner_1").toString());

        BannerViewModel bannerViewModel = new ViewModelProvider(getActivity()).get(BannerViewModel.class);
        bannerViewModel.setBannerFiles(imgFiles, getActivity());
        FragmentPagerAdapter pagerAdapter = bannerViewModel.setFragments();

        binding.viewPager2.setAdapter(pagerAdapter);
        binding.circleIndicator.setViewPager(binding.viewPager2);

        int listCnt= pagerAdapter.getItemCount();
        Log.i("listCnt",String.valueOf(listCnt));
        binding.circleIndicator.createIndicators(listCnt,0);
        binding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager2.setCurrentItem(listCnt, true);
        binding.viewPager2.setOffscreenPageLimit(listCnt);

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


        dispose.dispose();

        ProgressBar b = binding.pbTodayKcal;

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}
