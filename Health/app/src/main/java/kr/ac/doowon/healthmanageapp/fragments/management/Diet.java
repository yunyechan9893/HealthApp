package kr.ac.doowon.healthmanageapp.fragments.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementDietMainBinding;

public class Diet extends Fragment implements View.OnClickListener {
    FragmentManagementDietMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_diet_main, container,false);

        //현재 시간을 가져온다
        //sDate = Class_Tool.getNowDate();
        binding.ibtnTargetKcal.setOnClickListener(this::onClick);
        binding.ibtnDate.setOnClickListener(this::onClick);
        binding.ibtnDietRegister.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (binding.ibtnDate.equals(v)){
            /*
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

            dialog.show(this.getSupportFragmentManager(), "tag");

             */
            return;
        }
        else if (binding.ibtnDietRegister.equals(v)){
            return;
        }else if(binding.ibtnTargetKcal.equals(v)){
            /*
            DialogFragment_EditTextView dialog = new DialogFragment_EditTextView(handler);
            dialog.setTitle("목표 칼로리");
            dialog.setContentHint("목표 칼로리를 입력하세요 ex) 1800");
            // 다이얼로그에 넘겨줄 번들을 생성
            // 데이터를 넘겨주고 싶으면 번들에 추가한다.
            Bundle args = new Bundle();
            dialog.setArguments(args);
            dialog.show(getActivity().getSupportFragmentManager(),"tag2");

             */
        }
    }
}
