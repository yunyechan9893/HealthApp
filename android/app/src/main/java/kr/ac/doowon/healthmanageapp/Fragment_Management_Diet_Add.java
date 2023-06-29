package kr.ac.doowon.healthmanageapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fragment_Management_Diet_Add extends Fragment implements View.OnClickListener {

    public Fragment_Management_Diet_Add(Handler handler){
        ManagementDietHandler = handler;
    }

    @Override
    public void onClick(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeAllViews();
        ateFoodCount--;
    }

    class EventHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodType = bundle.getString("Method","E");

            switch (methodType){
                case "getDate" :
                    tvDate.setText(bundle.getString("date","E"));
                    break;

                case "getTime" :
                    //시간과 분을 가져온다.
                    int hour = bundle.getInt("hourOfDay",-1);
                    int minute = bundle.getInt("minute",-1);
                    String AmPm = hour > 12 ? "PM":"AM";
                    //12시를 초과하면 12시간을 빼라
                    //시, 분이 한자리라면 앞에 0을 붙혀줘라 ex)01:03
                    if(hour > 12) hour = hour - 12;
                    String sHour = hour < 10 ? "0"+hour: ""+hour;
                    String sminute = minute < 10 ? "0"+minute: ""+minute;

                    tvTime.setText(sHour+":"+sminute+AmPm);
                    break;

                case "setFoodList" :
                    foodList = theadPool.getFoodList();
                    // 팝업 메뉴 생성
                    // 컨텍스트 지정 및 위치 지정
                    PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), btnFoodSearch);

                    // 데이터베이스에 저장된 음식을 불러와 메뉴에 추가한다.
                    for(int i = 0; i < foodList.size(); i++){
                        String uniFoodInfo = foodList.get(i).getFoodName() + " ( 양 : " + foodList.get(i).getFoodAmount() + "g, "+
                                "열량 : "+foodList.get(i).getKcal() + "kcal )";
                        popupMenu.getMenu().add(0,i + 1,0, uniFoodInfo);
                    }

                    // 팝업 메뉴를 띄운다
                    popupMenu.show();

                    //팝업메뉴 아이템 클릭 리스터
                    popupMenu.setOnMenuItemClickListener((menuItem -> {

                        // 뷰그룹 : 리니어 레이아웃, 뷰 : 텍스트뷰 2개
                        // 먹었던 음식을 동적으로 추가해준다.
                        LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        TextView tvFoodInfo = new TextView(getActivity().getApplicationContext());
                        tvFoodInfo.setText(menuItem.getTitle());
                        tvFoodInfo.setSingleLine(true);
                        TextView tvDelete = new TextView(getActivity().getApplicationContext());
                        tvDelete.setText(" X");
                        tvDelete.setSingleLine();
                        tvDelete.setOnClickListener(Fragment_Management_Diet_Add.this);
                        linearLayout.addView(tvFoodInfo);
                        linearLayout.addView(tvDelete);
                        llAteFoodContainer.addView(linearLayout);
                        ateFoodCount++;

                        return true;
                    }));
                    break;

                // 등록 버튼 누를 시 동작
                case "registDietThread" :
                    // 데이터베이스 diet에 값 저장 후 no값 반환
                    theadPool.registDietFoodThread(bundle.getInt("no"),foodList);
                    break;

                // 위에 registDietThread 동작 후 동작
                case "registDietFood" :
                    // 데이터베이스 diet_Ate_Food 값 저장 (여러개의 값 반복문으로 저장)
                    theadPool.GetDietResultTread(USERID);
                    break;

                // 위에 GetDietResultTread 동작 후 동작
                case "GetDietResultTread" :
                    // 데이터베이스 뷰테이블 값을 리스트 반환
                    // Diet 쉐어드프리퍼런스 값 업데이트
                    Class_SPFUpdate.SaveSharedPreferences(context,theadPool.GetDietList());

                    //토스트 메세지
                    Class_Tool.MakeToast.Unit(context,"식단이 등록되었습니다.");

                    //다이어트 프레그먼트 업데이트
                    fragmentManagementDiet.updateDietListView();
                    fragmentManagementDiet.updateKcales();
                    getActivity().getSupportFragmentManager().popBackStack();

                    break;
                default:
                    System.out.println("오류가 발생하였습니다.");
                    break;
            }
        }
    }

    View rootView;
    TextView tvDate, tvTime;
    EditText edMealOfType, edFoodName, edComment;
    Button btnRegistPicture, btnFoodSearch, btnRegist, btnCancel;
    Bundle bundle;
    LinearLayout llAteFoodContainer;
    Class_TheadPool theadPool;
    List<JavaBean_Management_Diet_AllAteFood> foodList;
    Context context;
    String USERID, sNowDate;
    Handler handler, ManagementDietHandler;
    Activity_04_Main_Frame parentActivity;
    Fragment_Management_Diet fragmentManagementDiet;
    int ateFoodCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new EventHandler();
        theadPool = new Class_TheadPool(handler);
        context = getActivity().getApplicationContext();
        USERID = Class_Tool.getUSERID(context);
        parentActivity = (Activity_04_Main_Frame) getActivity();
        parentActivity.cfm.setFragmentManagementDietAdd(this);
        sNowDate = Class_Tool.getNowDate();
        fragmentManagementDiet = parentActivity.cfm.getFragmentManagementDiet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_management_diet_add, container,false);
        tvDate = rootView.findViewById(R.id.tvDate);
        tvTime = rootView.findViewById(R.id.tvTime);
        edMealOfType = rootView.findViewById(R.id.edMealOfType);
        edFoodName = rootView.findViewById(R.id.edFoodName);
        edComment = rootView.findViewById(R.id.edComment);
        btnRegistPicture = rootView.findViewById(R.id.btnRegistPicture);
        btnFoodSearch = rootView.findViewById(R.id.btnFoodSearch);
        btnRegist = rootView.findViewById(R.id.btnRegist);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        llAteFoodContainer = rootView.findViewById(R.id.llAteFoodContainer);

        if(tvDate.getText().toString().equals(""))
        {
            tvDate.setText(sNowDate);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 날짜가 비어있다면

        edMealOfType.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });

        tvDate.setOnClickListener((view -> {
            String[] date = tvDate.getText().toString().split("-");
            int year = Integer.valueOf(date[0]);
            int month = Integer.valueOf(date[1]);
            int day = Integer.valueOf(date[2]);

            DialogFragment dialog = new DialogFragment_Date(handler);
            Bundle args = new Bundle();
            args.putString("key", "value");
            args.putInt("year",year);
            args.putInt("month",month);
            args.putInt("day",day);

            dialog.setArguments(args); // 데이터 전달

            dialog.show(getActivity().getSupportFragmentManager(),"tag");
        }));

        tvTime.setOnClickListener(view -> {
            DialogFragment dialog = new DialogFragment_Time(handler);

            Bundle args = new Bundle();

            dialog.setArguments(args); // 데이터 전달

            dialog.show(getActivity().getSupportFragmentManager(),"tag");
        });


        btnFoodSearch.setOnClickListener((view -> {
            String foodName = edFoodName.getText().toString();
            // 데이터베이스에 food에 foodName이 포함된 값을 전달받음
            theadPool.setFoodList(foodName);
        }));

        btnCancel.setOnClickListener((view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        }));

        btnRegist.setOnClickListener((view -> {
            String mealOfType = edMealOfType.getText().toString();
            String date = tvDate.getText().toString();
            String time = tvTime.getText().toString();
            String comment = edComment.getText().toString();

            if(mealOfType.equals("")) {
                Class_Tool.MakeToast.Unit(context, "식사종류를 입력해주세요.");
                return;
            }
            else if(date.equals("")){
                Class_Tool.MakeToast.Unit(context, "날짜를 선택해주세요.");
                return;
            }
            else if(time.equals("")){
                Class_Tool.MakeToast.Unit(context, "시간을 선택해주세요.");
                return;
            }
            else if(time.equals("")){
                Class_Tool.MakeToast.Unit(context, "시간을 선택해주세요.");
                return;
            }else if(ateFoodCount <= 0){
                System.out.println("ateCount" + ateFoodCount);
                Class_Tool.MakeToast.Unit(context, "먹은 음식을 등록해주세요.");
                return;
            }

            theadPool.registDietThread(USERID, mealOfType, time, comment, date, 0);
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parentActivity.getDietView();
        parentActivity.cfm.setFragmentManagementDietAdd(null);
    }
}
