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
import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;
import kr.ac.doowon.healthmanageapp.database.TargetKcal;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeBinding;
import kr.ac.doowon.healthmanageapp.view_model.BannerViewModel;

public class Home extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private BannerViewModel bannerViewModel;
    private Disposable disposable;
    private AppDatabase db;
    private String formatNowDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        setNowData();
        setupViewModel();
        setupBannerIfNeeded();
        setupViewPager();
        loadTargetKcalsFromDatabase();

        return binding.getRoot();
    }

    private void setNowData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        formatNowDate = dateFormat.format(nowDate);
    }

    private void setupViewModel() {
        bannerViewModel = new ViewModelProvider(this).get(BannerViewModel.class);
    }

    private void setupBannerIfNeeded() {
        MyFragmentStateAdapter pagerAdapter = bannerViewModel.initAdpter(getActivity()).getFragmentAdapter();

        if (pagerAdapter.getItemCount() == 0) {
            List<File> imgFiles = Arrays.asList(
                    new File(requireActivity().getFilesDir(), "banner_0"),
                    new File(requireActivity().getFilesDir(), "banner_1"),
                    new File(requireActivity().getFilesDir(), "banner_2"),
                    new File(requireActivity().getFilesDir(), "banner_3")
            );

            bannerViewModel.initFiles(imgFiles);
            bannerViewModel.setFragments();
        }

        binding.viewPager2.setAdapter(pagerAdapter);
        binding.circleIndicator.setViewPager(binding.viewPager2);
    }

    private void setupViewPager() {
        int listCnt = bannerViewModel.getFragmentAdapter().getItemCount();
        ViewPager2 viewPager = binding.viewPager2;

        binding.circleIndicator.createIndicators(listCnt, 0);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setCurrentItem(0, false);
        viewPager.setOffscreenPageLimit(listCnt);
    }

    private void loadTargetKcalsFromDatabase() {
        db = AppDatabase.getDatabase(requireContext());

        disposable = db.targetKcalDAO().getTargetKcal(formatNowDate)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        targetKcals -> {
                            loadAteDietFromDatabase();
                        },
                        throwable -> {
                            handleTargetKcalsError(throwable);
                        }
                );
    }

    private void handleTargetKcals(List<TargetKcal> targetKcals) {
        for (TargetKcal targetKcal : targetKcals) {
            Log.i("targetKcals", targetKcal.getKcal() + "");
        }
    }

    private void loadAteDietFromDatabase(){
        db.dietDAO().getDiet(formatNowDate)
                .subscribeOn(Schedulers.io())
                .subscribe((diets, throwable) -> {
                    if (diets==null) return;

                    for ( Diet diet : diets ) {
                        loadAteFoodFromDatabase(diet.getDietId());
                    }

                    Log.i("throwable", throwable+"");
                });


    }

    private void loadAteFoodFromDatabase(int diet_no){
        db.ateFoodDAO().getAteFood(diet_no)
                .subscribeOn(Schedulers.io())
                .subscribe((ateFoods, throwable) -> {
                    if (ateFoods == null) return;

                    for (AteFood ateFood : ateFoods) {
                        Log.i("getKcal", ateFood.getKcal()+"");
                    }
                }
        );

    }

    private void handleTargetKcalsError(Throwable throwable) {
        Log.i("targetKcals", "실패");
        Log.i("targetKcals", throwable.toString());
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
