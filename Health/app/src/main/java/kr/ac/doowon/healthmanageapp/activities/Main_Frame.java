package kr.ac.doowon.healthmanageapp.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationBarView;
import kr.ac.doowon.healthmanageapp.Fragment_Home;
import kr.ac.doowon.healthmanageapp.Fragment_Introduce_Main;
import kr.ac.doowon.healthmanageapp.Fragment_Management_main;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.database.DBHelper;

public class Main_Frame extends AppCompatActivity {
    /*
    * 추가해야되는것
    * - 로그아웃 구현
    * - 액세스 코인, 리프레시 코인 삭제
    * */
    @Override
    protected
    void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_04_main_frame);

        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(Main_Frame.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        TextView tvLogout = findViewById(R.id.tvLogout);
        NavigationBarView navigationBarView = findViewById(R.id.navBottom);
        ImageView ibtnNav = findViewById(R.id.ibtnNav);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        View drawerView = findViewById(R.id.drawer);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(this);
        fragmentAdapter.addFragment(new Fragment_Home());
        fragmentAdapter.addFragment(new Fragment_Management_main());
        fragmentAdapter.addFragment(new Fragment_Introduce_Main());
        viewPager.setAdapter(fragmentAdapter);

        navigationBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.icHome:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.icManage:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.icIntroduce:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navigationBarView.getMenu().getItem(position).setCheckable(true);
            }
        });

        ibtnNav.setOnClickListener((view -> {
            drawerLayout.openDrawer(drawerView);
        }));
    }
}
