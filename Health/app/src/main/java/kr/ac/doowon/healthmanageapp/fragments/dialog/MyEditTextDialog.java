package kr.ac.doowon.healthmanageapp.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
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

    private FragmentEdittextDialogBinding binding;
    private setPositiveClick positiveClick;
    private setNegativeClick negativeClick;

    public static MyEditTextDialog getInstance(String title, String contentHint){
        MyEditTextDialog dialog = new MyEditTextDialog();
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putSerializable("contentHint",contentHint);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edittext_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String contentHint = getArguments().getString("contentHint");

        binding.tvTitle.setText(title);
        binding.edContent.setHint(contentHint);

        binding.btnRegist.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (binding.btnRegist.equals(v)) {
            if (positiveClick != null) {
                positiveClick.movePage();
            }
            this.dismiss();
        } else if (binding.btnCancel.equals(v)) {
            if (negativeClick != null) {
                negativeClick.movePage();
            }
            this.dismiss();
        }
    }

    public void setPositiveClick(setPositiveClick positiveClick) {
        this.positiveClick = positiveClick;
    }

    public interface setPositiveClick {
        void movePage();// abstract method
    }

    public void setNegativeClick(setNegativeClick negativeClick){
        this.negativeClick = negativeClick;
    }

    public interface setNegativeClick {
        void movePage();// abstract method
    }

}
