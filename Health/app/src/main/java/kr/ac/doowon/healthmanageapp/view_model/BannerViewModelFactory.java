package kr.ac.doowon.healthmanageapp.view_model;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BannerViewModelFactory implements ViewModelProvider.Factory {
    private FragmentActivity fragmentActivity;

    public BannerViewModelFactory(FragmentActivity activity){
        this.fragmentActivity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BannerViewModel();
    }
}
