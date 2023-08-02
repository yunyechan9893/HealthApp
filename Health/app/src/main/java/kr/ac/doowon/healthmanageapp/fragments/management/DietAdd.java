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
            DialogFragment dialog = new MyDatePicker();
            dialog.show(getActivity().getSupportFragmentManager(),"tag");
        }else if (binding.tvTime.equals(v)){
            DialogFragment dialog = new MyTimePicker();
            dialog.show(getActivity().getSupportFragmentManager(),"tag");
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
