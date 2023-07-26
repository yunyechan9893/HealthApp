package kr.ac.doowon.healthmanageapp.view_model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.errorprone.annotations.Immutable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.Fragment_Management_main;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.fragments.HomeMainImage;


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
