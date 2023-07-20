package kr.ac.doowon.healthmanageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.databinding.Activity04MainFrameBinding;
import kr.ac.doowon.healthmanageapp.fragments.Home;
import kr.ac.doowon.healthmanageapp.Fragment_Introduce_Main;
import kr.ac.doowon.healthmanageapp.Fragment_Management_main;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.res.Prefs;

public class MainFrame extends AppCompatActivity {
    private static Prefs prefs;
    @Override
    protected
    void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity04MainFrameBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_04_main_frame);

        View drawerView = findViewById(R.id.drawer);

        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(this)
                .addFragment(new Home())
                .addFragment(new Fragment_Management_main())
                .addFragment(new Fragment_Introduce_Main());

        binding.viewPager.setAdapter(fragmentAdapter);
        binding.viewPager.setUserInputEnabled(false);

        binding.navBottom.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.icHome:
                    binding.viewPager.setCurrentItem(0);
                    return true;
                case R.id.icManage:
                    binding.viewPager.setCurrentItem(1);
                    return true;
                case R.id.icIntroduce:
                    binding.viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        });


        binding.ibtnNav.setOnClickListener((view -> {
            binding.drawerLayout.openDrawer(drawerView);
        }));

        prefs = Prefs.getInstance(getApplicationContext());
        binding.tvLogout.setOnClickListener((v -> {
            prefs.clearToken();
            Intent intent = new Intent(MainFrame.this, Login.class);
            startActivity(intent);
        }));
    }
}
