package kr.ac.doowon.healthmanageapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.doowon.healthmanageapp.R;

public class HomeMainImage extends Fragment {

    ImageView imageView;
    int imageSource;

    public HomeMainImage(){
        imageSource = 0;
    }

    public HomeMainImage(int imageSource){
        this.imageSource = imageSource;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home_mainimge, container, false);
        if(imageSource != 0) {
            imageView = rootView.findViewById(R.id.iv1);
            imageView.setImageResource(imageSource);
        }

        return rootView;
    }
}
