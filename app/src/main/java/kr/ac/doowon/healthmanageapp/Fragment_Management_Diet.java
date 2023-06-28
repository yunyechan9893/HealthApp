package kr.ac.doowon.healthmanageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_Management_Diet extends Fragment {

    class HandlerManager extends Handler{
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodType = bundle.getString("Method", "E");

            switch (methodType){
                // 데이트 피커를 변경할 때 변경된 날짜를 받아온다
                case "getDate":
                    sDate = bundle.getString("date","E");
                    updateDietListView();
                    listTodayAteFood.setAdapter(baseAdapter);
                    theadPool.TargetKcalSelectThread(USERID,sDate);
                    break;
                // 위에 getDate를 실행한 다음 실행한다.
                // 목표칼로리를 받아온 뒤 텍스트를 바꿔준다.
                case "getTargetKcal":
                    targetKcal = bundle.getInt("targetKcal");
                    System.out.println(targetKcal);
                    updateDietListView();
                    updateKcales();
                    break;
                case "getContent":
                    targetKcal = Integer.valueOf(bundle.getString("content", "0"));
                    theadPool.TargetKcalInsertThread(USERID, sDate,targetKcal);
                    break;
                case "setTargetKcal":
                    int RESULT = bundle.getInt("RESULT",-1);

                    if(RESULT == 0){
                        Class_Tool.MakeToast.Unit(context, "목표 칼로리가 변경되었습니다.");
                        tvTodayTargetKcal.setText(""+targetKcal);
                        tvTodayExtratKcal.setText(String.valueOf(targetKcal - ateKcal));
                    }else if(RESULT == 1){
                        Class_Tool.MakeToast.Unit(context, "등록되었습니다.");
                        tvTodayTargetKcal.setText(""+targetKcal);
                        tvTodayExtratKcal.setText(String.valueOf(targetKcal - ateKcal));
                    }else {
                        Class_Tool.MakeToast.Unit(context, "에러입니다.");
                    }

                    break;
                }
            }
        }

    View rootView;
    ArrayList<JavaBean_Management_Diet_AllAteFood> dietList;
    BaseAdapter baseAdapter;
    SharedPreferences sharedPreferences;
    ImageView imgBackgroundFood, ibtnDietRegister;
    ImageButton ibtnDate, ibtnTargetKcal;
    TextView tvTodayTargetKcal, tvTodayExtratKcal, tvConsumedKcal;
    Context context;
    ListView listTodayAteFood;
    Fragment_Management_Diet_Add diet_add;
    private String USERID;
    Activity_04_Main_Frame parentActivity;
    Bundle bundle;
    Handler handler;
    Class_TheadPool theadPool;
    Fragment_Management_main fragmentManagementMain;
    String sDate;
    int targetKcal, ateKcal, extraKcal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();

        sharedPreferences = context.getSharedPreferences("Diet", context.MODE_PRIVATE);
        USERID = Class_Tool.getUSERID(context);
        parentActivity = (Activity_04_Main_Frame) getActivity();
        parentActivity.cfm.setFragmentManagementDiet(this);
        fragmentManagementMain = parentActivity.cfm.getFragmentManagementMain();
        handler = new HandlerManager();
        diet_add = new Fragment_Management_Diet_Add(handler);
        theadPool = new Class_TheadPool(handler);
        USERID = Class_Tool.getUSERID(context);

        //현재 시간을 가져온다
        sDate = Class_Tool.getNowDate();

        updateDietListView();
        theadPool.TargetKcalSelectThread(USERID, sDate);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_management_diet_main,container, false);
        imgBackgroundFood = rootView.findViewById(R.id.imgBackgroundFood);
        tvTodayTargetKcal = rootView.findViewById(R.id.tvTodayTargetKcal);
        tvTodayExtratKcal = rootView.findViewById(R.id.tvTodayExtratKcal);
        tvConsumedKcal = rootView.findViewById(R.id.tvConsumedKcal);
        listTodayAteFood = rootView.findViewById(R.id.listAteFood);
        ibtnDietRegister = rootView.findViewById(R.id.ibtnDietRegister);
        ibtnDate = rootView.findViewById(R.id.ibtnDate);
        ibtnTargetKcal = rootView.findViewById(R.id.ibtnTargetKcal);

        listTodayAteFood.setAdapter(baseAdapter);
        tvConsumedKcal.setText(String.valueOf(ateKcal));
        tvTodayTargetKcal.setText(String.valueOf(targetKcal));
        tvTodayExtratKcal.setText(String.valueOf(extraKcal));

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();
        //리스튜뷰를 클릭 시
        listTodayAteFood.setOnItemClickListener(((adapterView, view, position, l) -> {
            // Diet_Detail로 이동
            // 날짜하고 몇번째 리스트가 눌렸는지를 보내준다.
            Intent intentDietDetail = new Intent(getActivity(), Activity_Management_Diet_Detail.class);
            intentDietDetail.putExtra("date",sDate);
            intentDietDetail.putExtra("position",position);
            startActivity(intentDietDetail);
        }));

                ibtnDate.setOnClickListener((view -> {
                    String[] aDate = sDate.split("-");

                    int year = Integer.valueOf(aDate[0]);
                    int month = Integer.valueOf(aDate[1]);
                    int day = Integer.valueOf(aDate[2]);

                    DialogFragment dialog = new DialogFragment_Date(handler);
                    Bundle args = new Bundle();
                    args.putString("key", "value");
                    args.putInt("year", year);
                    args.putInt("month", month);
                    args.putInt("day", day);

                    dialog.setArguments(args); // 데이터 전달

                    dialog.show(getActivity().getSupportFragmentManager(), "tag");
                }));

        ibtnDietRegister.setOnClickListener((view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flDietMain, diet_add).addToBackStack(null).commit();
        }));

        ibtnTargetKcal.setOnClickListener((view -> {
            DialogFragment_EditTextView dialog = new DialogFragment_EditTextView(handler);
            dialog.setTitle("목표 칼로리");
            dialog.setContentHint("목표 칼로리를 입력하세요 ex) 1800");
            // 다이얼로그에 넘겨줄 번들을 생성
            // 데이터를 넘겨주고 싶으면 번들에 추가한다.
            Bundle args = new Bundle();
            dialog.setArguments(args);
            dialog.show(getActivity().getSupportFragmentManager(),"tag2");
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Diet 생명주기 종료");
        fragmentManagementMain.updateTodayKcal();
        fragmentManagementMain.updateTargetKcal();
        parentActivity.cfm.setFragmentManagementDiet(null);
    }

    // 재사용은 안되지만 너무 길어 객체화 해주었다.
    public void updateDietListView(){
        // 쉐어드프리퍼런스에 담긴 값들을 어레이리스트에 저장한다.
        // 넘버 값이 없으면 브레이크
        // 전 넘버가 같거나, 날짜가 설정한 날짜와 안맞으면 다음
        // 어레이 리스트에 각각의 값을 저장한다.
        if(dietList != null){
            dietList = null;
        }



        dietList = new ArrayList<JavaBean_Management_Diet_AllAteFood>();
        baseAdapter = new Adapter_Base_Management_Diet(context, dietList);

        int equalTimeAteFoodKcal = 0;
        int equalTimeAteFoodProtein = 0;
        boolean flag = false;

        for(int i = 1; i < Integer.MAX_VALUE; i++){
            int no = sharedPreferences.getInt("no"+i, 0);
            int afterNo = sharedPreferences.getInt("no"+(i+1), 0);

            // no == 0 이면 탈출
            if(no == 0) break;

            // date가 sDate와 일치하지 않으면 다음
            if(!sharedPreferences.getString("date"+i,"").equals(sDate)) continue;
            // nextNo와 no가 같으면 칼로리와 단백질을 더해주고 다음
            // no가 같다는 말은 같은 시간에 먹은 음식이라는 뜻
            if(afterNo == no) {
                equalTimeAteFoodKcal += sharedPreferences.getInt("kcal"+i, 0) ;
                equalTimeAteFoodProtein += sharedPreferences.getInt("protein"+i, 0);
                flag = true;
                continue;
            }
            if(flag){
                equalTimeAteFoodKcal += sharedPreferences.getInt("kcal"+i, 0) ;
                equalTimeAteFoodProtein += sharedPreferences.getInt("protein"+i, 0);

                dietList.add(new JavaBean_Management_Diet_AllAteFood(
                        sharedPreferences.getString("typeOfMeal"+i,"E"),
                        equalTimeAteFoodKcal,
                        equalTimeAteFoodProtein,
                        sharedPreferences.getString("comment"+i,"E")
                ));
            }else {
                dietList.add(new JavaBean_Management_Diet_AllAteFood(
                        sharedPreferences.getString("typeOfMeal"+i,"E"),
                        sharedPreferences.getInt("kcal"+i,0),
                        sharedPreferences.getInt("protein"+i,0),
                        sharedPreferences.getString("comment"+i,"E")
                ));
            }
        }


        // 리스트에 있는 값의 칼로리 합을 구해준다
        ateKcal = 0;
        for(int i = 0; i < dietList.size(); i++){
            int Kcal = dietList.get(i).getKcal();
            ateKcal += Kcal;
        }
    }

    public void updateKcales(){
        extraKcal = targetKcal - ateKcal;
        tvConsumedKcal.setText(String.valueOf(ateKcal));
        tvTodayTargetKcal.setText(String.valueOf(targetKcal));
        tvTodayExtratKcal.setText(String.valueOf(extraKcal));
    }

}
