package kr.ac.doowon.healthmanageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.navigation.NavigationBarView;

import kr.ac.doowon.healthmanageapp.Fragment_Introduce_Main;
import kr.ac.doowon.healthmanageapp.fragments.management.Management;
import kr.ac.doowon.healthmanageapp.databinding.ActivityMainFrameBinding;
import kr.ac.doowon.healthmanageapp.fragments.home.Home;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import kr.ac.doowon.healthmanageapp.view_model.NavigationVIewModel;

public class MainFrame extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    private static Prefs prefs;
    ActivityMainFrameBinding binding;

    @Override
    protected
    void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_frame);

        // 네비게이션 아이템 클릭 시 보여줄 프레그먼트 구성
        setNavigationFragmentAdapter();

        binding.navBottom.setOnItemSelectedListener(this::onNavigationItemSelected);
        binding.ibtnNav.setOnClickListener(this::onClick);
        binding.tvLogout.setOnClickListener(this::onClick);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    }

    @Override
    public void onClick(View v) {
        Log.i("e", "1");
        if (binding.ibtnNav.equals(v)) {
            binding.drawerLayout.openDrawer((View) findViewById(R.id.drawer));
        }
        else if (binding.tvLogout.equals(v)) {
            prefs = Prefs.getInstance(getApplicationContext());
            prefs.clearToken();

            Intent intent = new Intent(MainFrame.this, Login.class);
            startActivity(intent);
        }
    }

    private void setNavigationFragmentAdapter(){
        NavigationVIewModel navigationVIewModel = new NavigationVIewModel();
        FragmentPagerAdapter fragmentAdapter = navigationVIewModel.getFragmentAdapter();
        if (fragmentAdapter==null){
            fragmentAdapter = navigationVIewModel.initAdpter(this)
                    .addFragment(new Home())
                    .addFragment(new Management())
                    .addFragment(new Fragment_Introduce_Main())
                    .getFragmentAdapter();
        }

        binding.viewPager.setAdapter(fragmentAdapter);
        binding.viewPager.setUserInputEnabled(false);
    }
}
