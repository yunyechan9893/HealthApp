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

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentDateDialogBinding;

public class MyDatePicker extends DialogFragment implements View.OnClickListener {
    private FragmentDateDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date_dialog, container, false);

        int year  = 2023;
        int month = 07;
        int day   = 30;

        binding.datePicker.init(year, month - 1, day, (datePicker1, _year, _month, _day) -> {
            Log.i("_year", _year+"");
            Log.i("_month", _month+"");
            Log.i("_day", _day+"");
        });

        binding.btnCancel.setOnClickListener(this::onClick);
        binding.btnRegist.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (binding.btnRegist.equals(v)){
                this.dismiss();
        }
        else if (binding.btnCancel.equals(v)){
                this.dismiss();
        }
    }
}
