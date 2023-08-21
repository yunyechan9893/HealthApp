package kr.ac.doowon.healthmanageapp.fragments.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentTimeDialogBinding;


public class MyTimePicker extends DialogFragment implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    private FragmentTimeDialogBinding binding;
    private setPositiveClick positiveClick;

    public static MyTimePicker getInstance(Long time){

        Date convertedTime = new Date(time);

        // Date 객체에서 시간과 분 추출
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        MyTimePicker timePicker = new MyTimePicker();
        Bundle args = new Bundle();
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        timePicker.setArguments(args);
        return timePicker;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        int hour = getArguments().getInt("hour");
        int minute = getArguments().getInt("minute");

        //버전이 낮아서 setHour 대신 씀
        binding.timePicker.setCurrentHour(hour);
        binding.timePicker.setCurrentMinute(minute);

        binding.timePicker.setOnTimeChangedListener(this::onTimeChanged);
        binding.btnRegist.setOnClickListener(this::onClick);
        binding.btnCancel.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (binding.btnRegist.equals(v)){
            this.dismiss();
        } else if (binding.btnCancel.equals(v)){
            this.dismiss();
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    }

    public void setPositiveClick(setPositiveClick positiveClick){
        this.positiveClick = positiveClick;
    }

    public interface setPositiveClick{
        void movePage();
    }
}
