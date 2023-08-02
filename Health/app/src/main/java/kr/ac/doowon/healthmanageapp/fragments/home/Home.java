package kr.ac.doowon.healthmanageapp.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.database.TargetKcal;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeBinding;
import kr.ac.doowon.healthmanageapp.view_model.BannerViewModel;

public class Home extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private BannerViewModel bannerViewModel;
    private Disposable disposable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);



        // ViewModel 초기화
        bannerViewModel = new ViewModelProvider(this).get(BannerViewModel.class);

        if (bannerViewModel.initAdpter(getActivity()).getFragmentAdapter().getItemCount()==0){

            // 차후에 동적으로 받아온다
            List<File> imgFiles = Arrays.asList(
                    new File(requireActivity().getFilesDir(), "banner_0"),
                    new File(requireActivity().getFilesDir(), "banner_1"),
                    new File(requireActivity().getFilesDir(), "banner_2"),
                    new File(requireActivity().getFilesDir(), "banner_3")
            );

            bannerViewModel.initFiles(imgFiles);
            bannerViewModel.setFragments();
        }

        MyFragmentStateAdapter pagerAdapter = bannerViewModel.getFragmentAdapter();

        binding.viewPager2.setAdapter(pagerAdapter);
        binding.circleIndicator.setViewPager(binding.viewPager2);

        int listCnt= pagerAdapter.getItemCount();
        binding.circleIndicator.createIndicators(listCnt,0);
        binding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager2.setCurrentItem(listCnt, true);
        binding.viewPager2.setOffscreenPageLimit(listCnt);

        AppDatabase db = AppDatabase.getDatabase(requireContext());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        String formatNowDate = dateFormat.format(nowDate);

        // RxJava를 사용한 비동기 처리
        disposable = db.targetKcalDAO().getTargetKcal(formatNowDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(targetKcals -> {
                    // 데이터 처리
                    for (TargetKcal targetKcal:
                    targetKcals) {
                        Log.i("targetKcals",targetKcal.getKcal()+"");
                    }

                }, throwable -> {
                    // 에러 처리
                    Log.i("targetKcals","실패");
                    Log.i("targetKcals",throwable.toString());
                });

        // 나머지 코드는 그대로 유지
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Disposable 객체 해제
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onClick(View v) {
        // 클릭 이벤트 처리
    }
}
