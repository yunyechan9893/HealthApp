package kr.ac.doowon.healthmanageapp.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentHomeMainimgeBinding;

public class HomeMainImage extends Fragment {
    private FragmentHomeMainimgeBinding mainimgeBinding;
    private File imgFile;

    public HomeMainImage() {}

    public HomeMainImage(File imgFile) {
        this.imgFile = imgFile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainimgeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_mainimge, container, false);

        if (imgFile != null && imgFile.exists()) {
            // Glide 비동기 로드, 이미지 로드 완료 후 처리
            Glide.with(this)
                    .load(imgFile)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.i("HomeMainImage","실패");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // 이미지 로드 성공 시 처리

                            Log.i("HomeMainImage","로드성공");
                            return false;
                        }
                    })
                    .into(mainimgeBinding.iv1);
        }

        return mainimgeBinding.getRoot();
    }

    // Fragment가 재생성되도록 설정
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}