package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import kr.ac.doowon.healthmanageapp.activities.Login;


public class Activity_04_Main_Frame extends AppCompatActivity {
    class HandlerManager extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodName = bundle.getString("Method","E");

            switch (methodName) {
                case "Logout":
                    Intent intent = new Intent(context, Login.class);
                    startActivity(intent);
                    break;
                case "GetDietResultTread":
                    dietList = threadPool.GetDietList();
                    Class_SPFUpdate.SaveSharedPreferences(context,dietList);
                    break;
            }
        }
    }
    class ItemSelectListener implements NavigationBarView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.icHome:
                    getSupportFragmentManager().popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(FRAME_LAYOUT_CONTANER, homeFragment).commit();
                    return true;
                case R.id.icManage:
                    getSupportFragmentManager().popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(FRAME_LAYOUT_CONTANER, fragment_management_main).commit();
                    return true;
                case R.id.icIntroduce:
                    getSupportFragmentManager().popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(FRAME_LAYOUT_CONTANER, introduceFragment).commit();
                    return true;
            }

            return false;
        }
    }

    TextView tvLogout;
    ImageView ibtnNav;
    NavigationBarView navigationBarView;
    String USER_ID_COOKIE;
    Class_TheadPool threadPool;
    Bundle bundle;
    Fragment_Home homeFragment = new Fragment_Home(this);
    Fragment_Management_main fragment_management_main = new Fragment_Management_main();
    Fragment_Introduce_Main introduceFragment = new Fragment_Introduce_Main();
    private final int FRAME_LAYOUT_CONTANER = R.id.flMain;
    DrawerLayout drawerLayout;
    View drawerView;
    public Class_CFM cfm;
    Context context;

    private List<JavaBean_Management_Diet_AllAteFood> dietList;

    @Override
    protected
    void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_04_main_frame);

        context = this;
        //시작시 홈 화면 띄우기
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain, homeFragment).commit();
        Handler handler = new Activity_04_Main_Frame.HandlerManager();
        threadPool = new Class_TheadPool(handler);
        USER_ID_COOKIE = Class_Tool.getUSERID(this);
        getDietView();
        cfm = new Class_CFM();

        tvLogout = findViewById(R.id.tvLogout);
        navigationBarView = findViewById(R.id.navBottom);
        ibtnNav = findViewById(R.id.ibtnNav);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerView = findViewById(R.id.drawer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationBarView.setOnItemSelectedListener(new ItemSelectListener());

        tvLogout.setOnClickListener((view ->{
            SharedPreferences sp = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
            String id = sp.getString("ID", "E");
            String code = sp.getString("CODE", "E");

            threadPool.AutoLogin_DeleteThread1(id,code);
        }));

        ibtnNav.setOnClickListener((view -> {
            drawerLayout.openDrawer(drawerView);
        }));
    }

    public void getDietView(){
        threadPool.GetDietResultTread(USER_ID_COOKIE);
    }
}
