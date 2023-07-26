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

    private FragmentPagerAdapter fragmentAdapter;
    private List<File> imgFiles;

    public BannerViewModel(FragmentActivity activity, List<File> imgFiles){

    }

    public void init(FragmentActivity activity,List<File> imgFiles){
        fragmentAdapter = new FragmentPagerAdapter(activity);
        this.imgFiles = imgFiles;
    }

    public void setFragments(){
        for (File imgFile:
             imgFiles) {
            fragmentAdapter.addFragment(new HomeMainImage(imgFile));
        }
    }

    public FragmentPagerAdapter getFragmentAdapter(){
        return fragmentAdapter;
    }
}
