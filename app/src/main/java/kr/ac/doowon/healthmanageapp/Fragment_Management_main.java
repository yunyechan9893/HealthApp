package kr.ac.doowon.healthmanageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment_Management_main extends Fragment implements View.OnClickListener {
    class HandlerManager extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            targetKcal = bundle.getInt("targetKcal",0);
            tvTargetKcal.setText(String.valueOf(targetKcal));
        }
    }

    private Button btnDiet;
    private TextView tvTodayKcal, tvTargetKcal;
    private View rootView;
    private SharedPreferences sharedPreferences;
    private Fragment_Management_Diet diet;
    private String sNowDate;
    private int todayKcal, targetKcal;
    private Class_TheadPool theadPool;
    private Bundle bundle;
    private static String USERID;
    private Activity_04_Main_Frame parentActivity;

    int count = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDiet:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flMainMain, diet).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences("Diet", Context.MODE_PRIVATE);
        diet = new Fragment_Management_Diet();
        Handler handler = new HandlerManager();
        theadPool = new Class_TheadPool(handler);
        USERID = Class_Tool.getUSERID(getContext());
        todayKcal = 0;
        parentActivity = (Activity_04_Main_Frame) getActivity();
        parentActivity.cfm.setFragmentManagementMain(this);

        // 현재 날짜를 가져온다
        sNowDate = Class_Tool.getNowDate();

        updateTodayKcal();
        theadPool.TargetKcalSelectThread(USERID, sNowDate);
    }

    public void updateTodayKcal(){
        for (int i = 1; i < Integer.MAX_VALUE; i++){
            if(sharedPreferences.getInt("kcal"+i,999999) == 999999) break;
            // 오늘 날짜와 같은 값일 경우 칼로리를 전부 더한다
            // 오름차순 정리이므로 날짜에서 벗어날 경우 바로 브레이크 해준다
            if(sNowDate.equals(sharedPreferences.getString("date"+i,"E"))) {
                todayKcal += sharedPreferences.getInt("kcal" + i, 999999);
                if (!sNowDate.equals(sharedPreferences.getString("date" + (i + 1), "E"))) break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menagement_main, container, false);
        btnDiet = rootView.findViewById(R.id.btnDiet);
        tvTodayKcal = rootView.findViewById(R.id.tvTodayKcal);
        tvTargetKcal = rootView.findViewById(R.id.tvTargetKcal);
        System.out.println(targetKcal);
        // 오늘 칼로리를 텍스트뷰로 출력한다.
        tvTodayKcal.setText(String.valueOf(todayKcal));
        tvTargetKcal.setText(String.valueOf(targetKcal));
        return rootView;
    }

    public void updateTargetKcal(){
        theadPool.TargetKcalSelectThread(USERID, sNowDate);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnDiet.setOnClickListener(this);
    }
}
