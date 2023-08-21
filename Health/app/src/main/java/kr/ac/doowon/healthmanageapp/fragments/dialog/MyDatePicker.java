package kr.ac.doowon.healthmanageapp.fragments.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentDateDialogBinding;

public class MyDatePicker extends DialogFragment implements View.OnClickListener {
    private FragmentDateDialogBinding binding;
    private setPositiveClick positiveClick;

    public static MyDatePicker getInstance(Long date){
        // long 시간 값을 다시 Date로 변환
        Date convertedDate = new Date(date);

        // Date 객체에서 년, 월, 일 추출
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 0부터 시작하므로 +1 해줌
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        MyDatePicker datePicker = new MyDatePicker();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        datePicker.setArguments(args);
        return datePicker;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Bundle에서 Date 객체 가져오기
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");

        binding.datePicker.init(year, month - 1, day - 2, (datePicker1, _year, _month, _day) -> {
        });

        binding.btnCancel.setOnClickListener(this);
        binding.btnRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (binding.btnRegist.equals(v)){
            positiveClick.movePage();
            this.dismiss();
        }
        else if (binding.btnCancel.equals(v)){
                this.dismiss();
        }
    }

    public void setPositiveClick(setPositiveClick positiveClick){
        this.positiveClick = positiveClick;
    }

    public interface setPositiveClick{
        void movePage();
    }
}
