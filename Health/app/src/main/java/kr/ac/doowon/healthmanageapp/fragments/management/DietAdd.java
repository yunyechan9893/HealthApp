package kr.ac.doowon.healthmanageapp.fragments.management;

import static android.view.Gravity.AXIS_CLIP;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import kr.ac.doowon.healthmanageapp.DialogFragment_Time;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementDietAddBinding;

public class DietAdd extends Fragment implements View.OnClickListener {

    private FragmentManagementDietAddBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_diet_add, container, false);

        if(binding.tvDate.getText().toString().equals(""))
        {
            // binding.tvDate.setText(sNowDate);
        }
        // 날짜가 비어있다면

        binding.edMealOfType.setOnKeyListener((view, i, keyEvent) -> {
            if (i == keyEvent.KEYCODE_ENTER)
                return true;
            return false;
        });





        binding.btnFoodSearch.setOnClickListener(v -> {
            // 팝업 메뉴를 띄운다

            PopupMenu popupMenu = new PopupMenu(requireContext(), binding.btnFoodSearch);

            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle menu item clicks here
                switch (item.getItemId()) {
                    case R.id.icHome:
                        // Do something for menu item 1
                        return true;
                    case R.id.icIntroduce:
                        // Do something for menu item 2
                        return true;
                }
                return false;
            });

            popupMenu.show();
//            PopupMenu popupMenu = new PopupMenu(requireContext(), binding.btnFoodSearch, AXIS_CLIP);
//            popupMenu.getMenu().add(0, 1,0, "양 : 100g, 열량 : 500Kcal");
//            popupMenu.getMenu().add(0, 2,0, "양 : 100g, 열량 : 500Kcal");
//            popupMenu.getMenu().add(0, 3,0, "양 : 100g, 열량 : 500Kcal");
//            popupMenu.getMenu().add(0, 4,0, "양 : 100g, 열량 : 500Kcal");
//            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
//            popupMenu.show();
//
//            popupMenu.setOnMenuItemClickListener((menuItem -> {
//                Log.i("popup", menuItem.toString());
//                return false;
//            }));
        });




        /*
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
         */

        binding.tvDate.setOnClickListener(this::onClick);
        binding.tvTime.setOnClickListener(this::onClick);
        binding.btnFoodSearch.setOnClickListener(this::onClick);
        binding.btnCancel.setOnClickListener(this::onClick);
        binding.btnRegist.setOnClickListener(this::onClick);

        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (binding.tvDate.equals(v)){
            DialogFragment dialog = new MyDatePicker();
            dialog.show(getActivity().getSupportFragmentManager(),"tag");
        }else if (binding.tvTime.equals(v)){
            DialogFragment dialog = new DialogFragment_Time();
            dialog.show(getActivity().getSupportFragmentManager(),"tag");
        }else if (binding.btnFoodSearch.equals(v)){
            String foodName = binding.edFoodName.getText().toString();
            // 데이터베이스에 food에 foodName이 포함된 값을 전달받음
        }else if (binding.btnCancel.equals(v)){
            //Diet화면으로 이동
            DietFrame dietFrame = (DietFrame) getActivity();
            dietFrame.moveDietActivity();
        }else if (binding.btnRegist.equals(v)){
            String mealOfType = binding.edMealOfType.getText().toString();
            String date = binding.tvDate.getText().toString();
            String time = binding.tvTime.getText().toString();
            String comment = binding.edComment.getText().toString();

            if(mealOfType.length()==0) {
                makeToast("식사 종류를 선택해주세요");
                return;
            } else if(date.length()==0){
                makeToast("날짜를 선택해주세요.");
                return;
            } else if(time.length()==0){
                makeToast("시간을 선택해주세요.");
                return;
            }
            /*
            else if(ateFoodCount <= 0){
                System.out.println("ateCount" + ateFoodCount);
                makeToast("먹은 음식을 등록해주세요");
                return;
            }
            */
        }
    }

    private void makeToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /*
    * @Override
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


                    getActivity().getSupportFragmentManager().popBackStack();

                    break;
                default:
                    System.out.println("오류가 발생하였습니다.");
                    break;
            }
        }
    }*/
}
