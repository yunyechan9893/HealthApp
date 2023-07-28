package kr.ac.doowon.healthmanageapp.view_model;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.List;

import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.fragments.home.HomeMainImage;


public class BannerViewModel extends ViewModel {

    private MutableLiveData<FragmentPagerAdapter> mutableLiveDatafragmentAdapter
            = new MutableLiveData<>();
    private List<File> imgFiles;

    public BannerViewModel initAdpter(FragmentActivity activity){
        mutableLiveDatafragmentAdapter.setValue(new FragmentPagerAdapter(activity));

        return this;
    }

    public BannerViewModel initFiles(List<File> imgFiles){
        this.imgFiles = imgFiles;

        return this;
    }

    public void setFragments(){
        for (File imgFile:
             imgFiles) {
            mutableLiveDatafragmentAdapter.getValue().addFragment(new HomeMainImage(imgFile));
        }
    }

    public FragmentPagerAdapter getFragmentAdapter(){
        return mutableLiveDatafragmentAdapter.getValue();
    }
}
