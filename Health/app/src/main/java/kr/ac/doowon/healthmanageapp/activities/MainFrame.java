package kr.ac.doowon.healthmanageapp.activities;

import static android.content.ContentValues.TAG;

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

import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.fragments.introduce.IntroduceMainFrame;
import kr.ac.doowon.healthmanageapp.fragments.management.Management;
import kr.ac.doowon.healthmanageapp.databinding.ActivityMainFrameBinding;
import kr.ac.doowon.healthmanageapp.fragments.home.Home;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;
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

        binding.navBottom.setOnItemSelectedListener(this);
        binding.ibtnNav.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);

        Log.d(TAG, "onCreate");
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
        if (binding.ibtnNav.equals(v)) {
            binding.drawerLayout.openDrawer((View) findViewById(R.id.drawer));
        }
        else if (binding.tvLogout.equals(v)) {
            prefs = Prefs.getInstance(getApplicationContext());
            prefs.clearToken();

            Intent intent = new Intent(this, AuthenticationFrame.class);
            startActivity(intent);
        }
    }

    private void setNavigationFragmentAdapter(){
        NavigationVIewModel navigationVIewModel = new NavigationVIewModel();
        MyFragmentStateAdapter fragmentAdapter = navigationVIewModel.getFragmentAdapter();
        if (fragmentAdapter==null){
            fragmentAdapter = navigationVIewModel.initAdpter(this)
                    .addFragment(new Home())
                    .addFragment(new Management())
                    .addFragment(new IntroduceMainFrame())
                    .getFragmentAdapter();
        }

        binding.viewPager.setAdapter(fragmentAdapter);
        binding.viewPager.setUserInputEnabled(false);
    }

    public void moveFragment(String fragmentName){
        if (fragmentName.equals("Login")) binding.viewPager.setCurrentItem(0);
        else binding.viewPager.setCurrentItem(1);
    }

    public void moveMainPage(){
        Intent intent = new Intent(this, MainFrame.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
