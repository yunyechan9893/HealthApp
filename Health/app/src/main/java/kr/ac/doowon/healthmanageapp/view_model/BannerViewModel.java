package kr.ac.doowon.healthmanageapp.view_model;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class BannerViewModel extends ViewModel {
    private ArrayList<Fragment> bannerImagefragments;

    public BannerViewModel(){
        bannerImagefragments = new ArrayList<>();
    }

    public BannerViewModel addBannerImage( Fragment fragment ){
        bannerImagefragments.add(fragment);

        return this;
    }

    public ArrayList getFragments(){
        return bannerImagefragments;
    }
}
