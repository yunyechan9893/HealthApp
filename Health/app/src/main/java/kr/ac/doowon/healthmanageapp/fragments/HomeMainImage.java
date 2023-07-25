package kr.ac.doowon.healthmanageapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeMainimgeBinding;

public class HomeMainImage extends Fragment {
    private FragmentHomeMainimgeBinding mainimgeBinding;
    private File imgFile;

    public HomeMainImage(){

    }
    public HomeMainImage(File imgFile){
        this.imgFile = imgFile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainimgeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_mainimge, container, false );

        if (imgFile!=null && imgFile.exists()){
            Glide.with(this)
                    .load(imgFile)
                    .into(mainimgeBinding.iv1);
        }

        return mainimgeBinding.getRoot();
    }


}
