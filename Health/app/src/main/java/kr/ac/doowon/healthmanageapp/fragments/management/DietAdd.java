package kr.ac.doowon.healthmanageapp.fragments.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementDietAddBinding;
import kr.ac.doowon.healthmanageapp.fragments.dialog.MyDatePicker;
import kr.ac.doowon.healthmanageapp.fragments.dialog.MyTimePicker;

public class DietAdd extends Fragment implements View.OnClickListener {

    private FragmentManagementDietAddBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_diet_add, container, false);

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
            String dateString = "23.08.21 10:10:10";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

            try {
                Date date = dateFormat.parse(dateString);
                long timeInMillis = date.getTime();

                MyDatePicker dialog = MyDatePicker.getInstance(timeInMillis);
                dialog.setPositiveClick(() -> {
                    // 날짜를 선택시 나옴
                });
                dialog.show(getActivity().getSupportFragmentManager(), "tag");

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else if (binding.tvTime.equals(v)){

            String timeString = "10:10:10";
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            try {
                Date time = timeFormat.parse(timeString);
                long timeInMillis = time.getTime();

                MyTimePicker dialog = MyTimePicker.getInstance(timeInMillis);
                dialog.setPositiveClick(() -> {
                    // 타임피커 선택시 값 받기
                });
                dialog.show(getActivity().getSupportFragmentManager(),"tag");
            } catch (ParseException e) {
                e.printStackTrace();
            }




        }else if (binding.btnFoodSearch.equals(v)){
            String foodName = binding.edFoodName.getText().toString();
            // 팝업 메뉴는 안뜨니깐 다른 방법을 찾아보자
            // 데이터베이스에 food에 foodName이 포함된 값을 전달받음
        }else if (binding.btnCancel.equals(v)){
            //Diet화면으로 이동
            DietFrame dietFrame = (DietFrame) getActivity();
            dietFrame.moveDietFragment();
        }else if (binding.btnRegist.equals(v)){
            String mealOfType = binding.edMealOfType.getText().toString();
            String date = binding.tvDate.getText().toString();
            String time = binding.tvTime.getText().toString();
            String comment = binding.edComment.getText().toString();

            if(mealOfType.length()==0) {
                makeToast("식사 종류를 선택해주세요");
            } else if(date.length()==0){
                makeToast("날짜를 선택해주세요.");
            } else if(time.length()==0){
                makeToast("시간을 선택해주세요.");
            }

            // 성공
            makeToast("등록 성공");
        }
    }

    private void makeToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
