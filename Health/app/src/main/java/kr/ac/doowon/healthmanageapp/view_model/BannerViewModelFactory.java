package kr.ac.doowon.healthmanageapp.view_model;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BannerViewModelFactory implements ViewModelProvider.Factory {
    private FragmentActivity fragmentActivity;
    private List<File> imgFiles;

    public BannerViewModelFactory(FragmentActivity activity, List<File> imgFiles ){
        this.fragmentActivity = activity;
        this.imgFiles = imgFiles;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BannerViewModel();
    }
}
