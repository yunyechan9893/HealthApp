package kr.ac.doowon.healthmanageapp;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Date;


public class DialogFragment_Date extends DialogFragment {
    private Fragment fragment;
    private int year, month, day;
    private Handler handler;
    Message msg;
    Bundle bundle = new Bundle();

    public DialogFragment_Date(){
    }

    public DialogFragment_Date(Handler handler){
        this.handler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.view_date_dialog, container,false);
        DatePicker datePicker = rootView.findViewById(R.id.datePicker);
        Button btnRegister = rootView.findViewById(R.id.btnRegist);
        Button btnCancel = rootView.findViewById(R.id.btnCancel);

        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");

        Bundle args = getArguments();
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");

        datePicker.init(year, month - 1, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int _year, int _month, int _day) {
                year = _year;
                month = _month;
                day = _day;
            }
        });

        btnRegister.setOnClickListener(view -> {
            msg = handler.obtainMessage();
            bundle.putString("Method","getDate");
            String sMonth = (month + 1) < 10 ? "0"+(month+1) : ""+(month + 1);
            String sDay = day < 10 ? "0"+day : ""+day;
            bundle.putString("date",year+"-" + sMonth +"-"+sDay);
            msg.setData(bundle);
            handler.sendMessage(msg);

            if (fragment != null) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }
        });


        btnCancel.setOnClickListener(view -> {
            if (fragment != null) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }
        });


        return rootView;
    }
}
