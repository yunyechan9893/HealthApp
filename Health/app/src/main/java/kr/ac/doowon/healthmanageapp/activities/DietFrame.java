package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.databinding.ActivityDietBinding;
import kr.ac.doowon.healthmanageapp.fragments.management.Diet;
import kr.ac.doowon.healthmanageapp.view_model.NavigationVIewModel;


public class DietFrame extends AppCompatActivity {

    ActivityDietBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet);

        NavigationVIewModel navigationVIewModel = new NavigationVIewModel();
        FragmentPagerAdapter adapter = navigationVIewModel.getFragmentAdapter();
        if (adapter==null){
            navigationVIewModel.initAdpter(this)
                    .addFragment(new Diet())
                    .addFragment();
        }

        binding.dietViewpager.setAdapter();


    }
}
