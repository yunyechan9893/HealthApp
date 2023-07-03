package kr.ac.doowon.healthmanageapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.PublicKey;

public interface Interface_TextWatcher extends TextWatcher{
    @Override
    public default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public default void afterTextChanged(Editable editable) {

    }
}
