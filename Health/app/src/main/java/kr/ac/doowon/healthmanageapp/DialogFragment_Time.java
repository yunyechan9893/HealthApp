package kr.ac.doowon.healthmanageapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


public class DialogFragment_Time extends DialogFragment {
    int hourOfDay, minute;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View rootView = inflater.inflate(R.layout.view_time_dialog, container,false);
        TimePicker timePicker = rootView.findViewById(R.id.timePicker);
        Button btnCancel = rootView.findViewById(R.id.btnCancel);
        Button btnRegister = rootView.findViewById(R.id.btnRegist);

        timePicker.setOnTimeChangedListener(((timePickerView, _hourOfDay, _minute) -> {
            hourOfDay = _hourOfDay;
            minute = _minute;
        }));

        btnRegister.setOnClickListener(view -> {
           this.dismiss();
        });

        //버튼 클릭시 다이얼로그 종료
        btnCancel.setOnClickListener((view-> {
            this.dismiss();
        }));

        return rootView;
    }
}
