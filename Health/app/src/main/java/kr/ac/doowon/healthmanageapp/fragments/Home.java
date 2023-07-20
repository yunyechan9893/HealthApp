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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscribers.LambdaSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.Fragment_Introduce_Main;
import kr.ac.doowon.healthmanageapp.Fragment_Management_main;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeBinding;
import kr.ac.doowon.healthmanageapp.view_model.BannerViewModel;
import me.relex.circleindicator.CircleIndicator3;

public class Home extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        BannerViewModel bannerViewModel = new BannerViewModel();
        bannerViewModel
                .addBannerImage(new HomeMainImage(R.drawable.img_banner_1))
                .addBannerImage(new HomeMainImage(R.drawable.img_banner_2));

        ArrayList<Fragment> bannerImages = bannerViewModel.getFragments();

        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(getActivity());
        for (Fragment fragment: bannerImages
             ) {
            fragmentAdapter.addFragment(fragment);
        }

        binding.viewPager2.setAdapter(fragmentAdapter);
        binding.circleIndicator.setViewPager(binding.viewPager2);

        int listCnt= fragmentAdapter.getItemCount();
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
        //if (disposable != null && !disposable.isDisposed()) {
         //   disposable.dispose();
       // }
    }
}
