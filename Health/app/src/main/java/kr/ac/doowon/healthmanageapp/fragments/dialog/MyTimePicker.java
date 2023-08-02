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

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentTimeDialogBinding;


public class MyTimePicker extends DialogFragment implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    FragmentTimeDialogBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_dialog, container, false);

        binding.timePicker.setOnTimeChangedListener(this::onTimeChanged);
        binding.btnRegist.setOnClickListener(this::onClick);
        binding.btnCancel.setOnClickListener(this::onClick);

        return binding.getRoot();
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
}
