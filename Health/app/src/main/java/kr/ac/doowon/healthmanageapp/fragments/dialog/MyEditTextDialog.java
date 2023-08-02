package kr.ac.doowon.healthmanageapp.fragments.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentEdittextDialogBinding;


public class MyEditTextDialog extends DialogFragment implements View.OnClickListener {
    FragmentEdittextDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edittext_dialog, container, false);


        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (binding.btnCancel.equals(v)){
            this.dismiss();
        } else if (binding.btnCancel.equals(v)){
            this.dismiss();
        }
    }


    public MyEditTextDialog setTitle(String title){
        binding.tvTitle.setText(title);
        return this;
    }

    public MyEditTextDialog setContentHint(String hint){
        binding.edContent.setHint(hint);
        return this;
    }
}
