package kr.ac.doowon.healthmanageapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


public class DialogFragment_EditTextView extends DialogFragment {
    private Fragment fragment;
    Message msg;
    Handler handler;
    TextView tvTitle;
    EditText edContent;
    Button btnRegister, btnCancel;
    Bundle args;
    String sTitle;
    String sHint;

    public DialogFragment_EditTextView(Handler handler){
        this.handler = handler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag2");
        args = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View rootView = inflater.inflate(R.layout.view_edittext_dialog, container,false);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        edContent = rootView.findViewById(R.id.edContent);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnRegister = rootView.findViewById(R.id.btnRegist);

        tvTitle.setText(sTitle);
        edContent.setHint(sHint);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnRegister.setOnClickListener(view -> {
            msg = handler.obtainMessage();
            args.putString("Method", "getContent");
            args.putString("content", edContent.getText().toString());
            msg.setData(args);
            handler.sendMessage(msg);

            if (fragment != null) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }
        });

        //버튼 클릭시 다이얼로그 종료
        btnCancel.setOnClickListener((view-> {
            if (fragment != null) {
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }}));
    }

    public void setTitle(String title){
        sTitle = title;
    }

    public void setContentHint(String hint){
        sHint = hint;
    }
}
