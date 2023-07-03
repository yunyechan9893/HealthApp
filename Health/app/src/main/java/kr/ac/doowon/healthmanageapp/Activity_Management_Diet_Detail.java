package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Activity_Management_Diet_Detail extends AppCompatActivity {

    private TextView tvTypeOfMeal, tvTime, tvConsumedKcal, tvConsumedCarbohydrate, tvConsumedDietaryFiber,
            tvConsumedProtein, tvConsumedFat, tvConsumedDietarySaturatedFiber, tvConsumedDietaryUnsaturatedFiber,
            tvConsumedDietaryCholesterol;
    private ListView listFoodDetail;
    String DATE;
    int DIET_LIST_POSITION;
    int NO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_diet_detail);

        Intent intent = getIntent();
        // 받은 날짜를 가져온다.
        DATE = intent.getStringExtra("date");
        // 다이어트에서 클릭된 리스트 번호를 가져온다.
        DIET_LIST_POSITION = intent.getIntExtra("position",-1);

        tvTypeOfMeal = findViewById(R.id.tvTypeOfMeal);
        tvTime = findViewById(R.id.tvTime);
        tvConsumedKcal = findViewById(R.id.tvConsumedKcal);
        tvConsumedCarbohydrate = findViewById(R.id.tvConsumedCarbohydrate);
        tvConsumedDietaryFiber = findViewById(R.id.tvConsumedDietaryFiber);
        tvConsumedProtein = findViewById(R.id.tvConsumedProtein);
        tvConsumedFat = findViewById(R.id.tvConsumedFat);
        tvConsumedDietarySaturatedFiber = findViewById(R.id.tvConsumedDietarySaturatedFiber);
        tvConsumedDietaryUnsaturatedFiber = findViewById(R.id.tvConsumedDietaryUnsaturatedFiber);
        tvConsumedDietaryCholesterol = findViewById(R.id.tvConsumedDietaryCholesterol);
        listFoodDetail = findViewById(R.id.listFoodDetail);

        System.out.println("DATE : "+DATE);
        System.out.println("DIET_LIST_POSITION : "+DIET_LIST_POSITION);

        // 일단 날짜와 포지션으로 넘버를 구하고
        SharedPreferences preferences = getSharedPreferences("Diet", Activity.MODE_PRIVATE);
        int count = 0;
        for (int i = 1; i < Integer.MAX_VALUE; i++){
            // 인덱스를 초과하면 탈출
            if(preferences.getInt("no"+i,0)==0)break;
            // 받아온 날짜와 저장프로시저 날짜가 맞지 않는다면 다음
            if(!DATE.equals(preferences.getString("date"+i,""))) continue;
            // no의 값이 같은 값일 경우 같은 시간에 먹은 음식이다.
            // 그러므로 카운터를 증가시키지 않고 다음
            if(preferences.getInt("no"+i,-1) == preferences.getInt("no"+(i+1), -2)) continue;
            // 넘겨 받은 리스트 포지션 값과 카운터가 같다면 NO에 no값을 넣어주고 탈출한다.
            if(count == DIET_LIST_POSITION) {
                NO = preferences.getInt("no"+i,0);
                break;
            }

            count ++;
        }

        System.out.println("NO : " + NO);

        // 넘버를 토대로 같은 시간에 먹은 음식들을 불러오고
        // 음식의 영양성분을 더하면서 리스트에 각각의 음식 정보를 넘긴다.

    }
}
